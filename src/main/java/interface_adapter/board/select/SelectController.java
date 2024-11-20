package interface_adapter.board.select;
//CreateTime: 2024-11-20 3:28 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import use_case.board.select.SelectInputBoundary;
import use_case.board.select.SelectInputData;
import view.BoardView.PiecesView.PiecesView;

public class SelectController {
    private final SelectInputBoundary interactor;

    public SelectController(SelectInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(ChariotBoard board, PiecesView piecesView) {
        this.interactor.execute(new SelectInputData(board, piecesView));
    }
}
