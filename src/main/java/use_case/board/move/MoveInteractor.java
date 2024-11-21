package use_case.board.move;
//CreateTime: 2024-11-20 3:14 p.m.


import api_adapters.ChariotAPI.ChariotBoard;
import chariot.util.Board;
import view.BoardView.PiecesView.PiecesView;

import java.util.List;

public class MoveInteractor implements MoveInputBoundary{

    private MoveOutputBoundary presenter;

    public MoveInteractor(MoveOutputBoundary presenter) {

        this.presenter = presenter;
    }

    @Override
    public void execute(MoveInputData data) {
        ChariotBoard board = data.board();
        PiecesView piecesView = data.piecesView();
        Board.Piece piece = board.getPieceAt(piecesView.getCoordinate());
        List<String> validMoves = data.validMoves();
        String move;
        MoveOutputData moveOutputData;
        if (piece != null && ((board.isBlackToMove() && piece.color() == Board.Side.BLACK)
                || (!board.isBlackToMove() && piece.color() == Board.Side.WHITE))) {
            moveOutputData = new MoveOutputData(true, true, false, board, piecesView);
        }else if (!(move = findValidMoves(validMoves, piecesView)).isEmpty()){
            if (board.isPromotionMove(move)){
                moveOutputData = new MoveOutputData(false, false, true, board, piecesView);
            }else{
                board.move(move);
                moveOutputData = new MoveOutputData(true, false, false, board, piecesView);
            }
        }else {
            moveOutputData = new MoveOutputData(true, false, false, board, piecesView);
        }presenter.prepareSuccessView(moveOutputData);
    }

    private String findValidMoves(List<String> validMoves, PiecesView piecesView) {
        String stringCoordinate = piecesView.getCoordinate().toString();
        for (String validMove : validMoves) {
            if (validMove.substring(2, 4).equals(stringCoordinate)) {
                return validMove;
            }
        }return "";
    }
}
