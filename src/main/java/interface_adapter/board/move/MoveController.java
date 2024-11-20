package interface_adapter.board.move;
//CreateTime: 2024-11-20 3:28 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import use_case.board.move.MoveInputBoundary;
import use_case.board.move.MoveInputData;
import view.BoardView.PiecesView.PiecesView;

public class MoveController {
    private final MoveInputBoundary interactor;

    public MoveController(MoveInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(ChariotBoard board, PiecesView piecesView) {
        this.interactor.execute(new MoveInputData(board, piecesView));
    }
}
