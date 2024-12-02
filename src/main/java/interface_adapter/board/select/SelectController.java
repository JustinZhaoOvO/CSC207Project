package interface_adapter.board.select;
//CreateTime: 2024-11-20 3:28 p.m.

import entity.ChariotBoard;
import entity.Coordinate;
import use_case.board.select.SelectInputBoundary;
import use_case.board.select.SelectInputData;

/**
 * Select a piece, highlight it and all its valid moves
 */
public class SelectController {
    private final SelectInputBoundary interactor;

    public SelectController(SelectInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * select a piece
     * @param board : a chariot board
     * @param coordinate : the coordinate was clicked
     */
    public void execute(ChariotBoard board, Coordinate coordinate) {
        this.interactor.execute(new SelectInputData(board, coordinate));
    }
}
