package use_case.board.move;
//CreateTime: 2024-11-20 3:14 p.m.


import entity.ChariotBoard;
import chariot.util.Board;
import entity.Coordinate;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MoveInteractor implements MoveInputBoundary{

    private final MoveOutputBoundary presenter;

    public MoveInteractor(MoveOutputBoundary presenter) {

        this.presenter = presenter;
    }

    /**
     * perform a move
     * @param data : the board, the coordinate was clicked and all valid moves
     */
    @Override
    public void execute(MoveInputData data) {
        ChariotBoard board = data.board();
        Coordinate coordinate = data.coordinate();
        Board.Piece piece = board.getPieceAt(coordinate);
        List<String> validMoves = data.validMoves();
        String move;
        MoveOutputData moveOutputData = new MoveOutputData(board, coordinate);
        if (piece != null && ((board.isBlackToMove() && piece.color() == Board.Side.BLACK)
                || (!board.isBlackToMove() && piece.color() == Board.Side.WHITE))) { // switch selected piece
            moveOutputData.setRepaint(true);
            moveOutputData.setSelect(true);

        }else if (!(move = findValidMoves(validMoves, coordinate)).isEmpty()){ // if a valid move
            if (board.isPromotionMove(move)){   //if a promotion
                moveOutputData.setPromotion(true);
            }else{  //move
                board.move(move);
                moveOutputData.setRepaint(true);
                moveOutputData.setMoved(true);
                moveOutputData.setMove(move);

                if (board.ended()){ // game over
                    moveOutputData.setGameOver(true);
                    moveOutputData.setMsg(getString(board.gameState(), board.isBlackToMove()));
                }
            }
        }else {   //invalid move
            moveOutputData.setRepaint(true);
        }presenter.prepareSuccessView(moveOutputData);
    }

    /**
     * get the message to be shown when game is over with specific state and turn
     * @param state : GameState : checkmate, stalemate, fifty_move_rule, threefold_repetition
     * @param blackToMove : black turn or not
     * @return message to be shown
     */
    @NotNull
    private static String getString(Board.GameState state, boolean blackToMove) {
        String msg = "ERROR : Game End by Unknown Error";
        if (state == Board.GameState.draw_by_fifty_move_rule) {
            msg = "Draw by fifty move rule";
        }else if (state == Board.GameState.draw_by_threefold_repetition) {
            msg = "Draw by three fold repetition";
        }else if (state == Board.GameState.stalemate) {
            msg = "Draw by stalemate";
        }else if (state == Board.GameState.checkmate) {
            if (blackToMove) {
                msg = "Checkmate! White win";
            }else {
                msg = "Checkmate! Black win";
            }
        }
        return msg;
    }

    /**
     * return the move string if the given coordinate is a valid move in valid moves array
     * @param validMoves : an array contains all valid moves
     * @param coordinate : the coordinate was clicked
     * @return : a move string, for example: a1a2 is a piece move from a1 to a2
     */
    private String findValidMoves(List<String> validMoves, Coordinate coordinate) {
        String stringCoordinate = coordinate.toString();
        for (String validMove : validMoves) {
            if (validMove.substring(2, 4).equals(stringCoordinate)) {
                return validMove;
            }
        }return "";
    }
}
