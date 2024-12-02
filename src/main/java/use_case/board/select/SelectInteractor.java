package use_case.board.select;
//CreateTime: 2024-11-20 3:14 p.m.


import entity.ChariotBoard;
import chariot.util.Board;
import entity.Coordinate;

public class SelectInteractor implements SelectInputBoundary{

    private final SelectOutputBoundary presenter;

    public SelectInteractor(SelectOutputBoundary presenter) {

        this.presenter = presenter;

    }

    /**
     * determine if a valid selection, if so, update the selected pieces and valid moves
     * @param data : the coordinate was clicked and the board
     */
    @Override
    public void execute(SelectInputData data) {
        ChariotBoard board = data.board();
        Coordinate coordinate = data.coordinate();
        Board.Piece piece = board.getPieceAt(coordinate);
        if (piece == null) return;
        if ((board.isBlackToMove() && piece.color() == Board.Side.BLACK)
                || (!board.isBlackToMove() && piece.color() == Board.Side.WHITE)) {
            presenter.prepareSuccessView(new SelectOutputData(coordinate,
                    board.getValidMovesOfPosition(coordinate.toString())));
        }
    }
}
