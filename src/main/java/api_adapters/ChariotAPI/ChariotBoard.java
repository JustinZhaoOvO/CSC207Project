package api_adapters.ChariotAPI;
//CreateTime: 2024-11-10 7:10 p.m.

import chariot.util.Board;

import java.util.ArrayList;
import java.util.List;

public class ChariotBoard implements ChariotAdaptorInterface {
    private Board board;

    public ChariotBoard() {
        this.board = Board.fromStandardPosition();
    }

    public List<String> getValidMoves(){
        return this.board.validMoves().stream()
                .map(Board.Move::uci)
                .sorted()
                .toList();
    }

    public List<String> getValidMovesOfPosition(String position){

        List<String> ans = new ArrayList<>();
        List<String> validMoves = getValidMoves();
        for (String move : validMoves) {
            if (move.startsWith(position)) {
                ans.add(move);
            }
        }return ans;
    }

    /**
     *
     * @param move : A move in UCI
     * @return : A Pawn Promotion or not
     */
    @Override
    public boolean isPromotionMove(String move){
        String position = move.substring(0, 2);
        Board.Piece piece = this.board.get(position);
        return piece.type().equals(Board.PieceType.PAWN)
                && ((piece.color().equals(Board.Side.WHITE)
                && position.charAt(1) == '7') ||
                (piece.color().equals(Board.Side.BLACK)
                        && position.charAt(1) == '2'));
    }



    public void move(String movesToPlay){
        this.board = this.board.play(movesToPlay);
    }

    @Override
    public boolean ended() {
        return this.board.ended();
    }

    @Override
    public Board.GameState gameState() {
        return this.board.gameState();
    }

    @Override
    public boolean isBlackToMove() {
        return this.board.blackToMove();
    }


    public String toString(){
        return this.board.toString();
    }
}
