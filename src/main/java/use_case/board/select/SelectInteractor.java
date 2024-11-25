package use_case.board.select;
//CreateTime: 2024-11-20 3:14 p.m.


import api_adapters.ChariotAPI.ChariotBoard;
import chariot.util.Board;
import view.BoardView.PiecesView.PiecesView;

public class SelectInteractor implements SelectInputBoundary{

    private SelectOutputBoundary presenter;

    public SelectInteractor(SelectOutputBoundary presenter) {

        this.presenter = presenter;

    }

    @Override
    public void execute(SelectInputData data) {
        ChariotBoard board = data.board();
        PiecesView piecesView = data.piecesView();
        Board.Piece piece = board.getPieceAt(piecesView.getCoordinate());
        if (piece == null) return;
        if ((board.isBlackToMove() && piece.color() == Board.Side.BLACK)
                || (!board.isBlackToMove() && piece.color() == Board.Side.WHITE)) {
            presenter.prepareSuccessView(new SelectOutputData(piecesView,
                    board.getValidMovesOfPosition(piecesView.getCoordinate().toString())));
        }
    }
}
