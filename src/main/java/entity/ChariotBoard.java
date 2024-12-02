package entity;
//CreateTime: 2024-11-10 7:10 p.m.

import chariot.util.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Using Facade Design Pattern
 * Encapsulate the Chariot API, only expose the methods we need to use,
 * and also add some new method we need
 */
public class ChariotBoard {
    private Board board;

    public ChariotBoard() {
        this.board = Board.fromStandardPosition();
    }


    private List<String> getValidMoves(){
        return this.board.validMoves().stream()
                .map(Board.Move::uci)
                .sorted()
                .toList();
    }

    /**
     * get all valid moves of a given position
     * @param position : a position on the board, for example a1, f3
     * @return : A list containing of valid moves of the position
     */
    public List<String> getValidMovesOfPosition(String position){
        if (position.length() != 2) return null;
        List<String> ans = new ArrayList<>();
        List<String> validMoves = getValidMoves();
        for (String move : validMoves) {
            if (move.startsWith(position)) {
                ans.add(move);
            }
        }return ans;
    }

    /**
     * get the Piece object at a given coordinate
     * @param coordinate : a coordinate object
     * @return : the piece at given coordinate on the board
     */
    public Board.Piece getPieceAt(Coordinate coordinate){
        return this.board.get(coordinate.toString());
    }

    /**
     *
     * @param move : A move in UCI
     * @return : A Pawn Promotion or not
     */
    public boolean isPromotionMove(String move){
        if (move.length() != 4) return false;
        String position = move.substring(0, 2);
        Board.Piece piece = this.board.get(position);
        return piece.type().equals(Board.PieceType.PAWN)
                && ((piece.color().equals(Board.Side.WHITE)
                && position.charAt(1) == '7') ||
                (piece.color().equals(Board.Side.BLACK)
                        && position.charAt(1) == '2'));
    }


    /**
     * Perform a move
     * @param movesToPlay : the move string, For example : a3a4
     */
    public void move(String movesToPlay){
        this.board = this.board.play(movesToPlay);
    }

    /**
     * determine if  the game is ended
     * @return : game is ended or not
     */
    public boolean ended() {
        return this.board.ended();
    }

    /**
     * get the game state
     * @return: GameState : checkmate, stalemate, fifty_move_rule, threefold_repetition, ongoing
     */
    public Board.GameState gameState() {
        return this.board.gameState();
    }

    /**
     * Determine is black turn or not
     * @return : is black turn or not
     */
    public boolean isBlackToMove() {
        return this.board.blackToMove();
    }

    public String toString(){
        return this.board.toString();
    }
}
