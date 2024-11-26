package interface_adapter.board.repaintboard;
//CreateTime: 2024-11-14 4:20 p.m.

import entity.ChariotBoard;
import use_case.board.repaintboard.RepaintBoardInputBoundary;
import use_case.board.repaintboard.RepaintBoardInputData;


public class RepaintBoardController {

    private final RepaintBoardInputBoundary interactor;

    public RepaintBoardController(RepaintBoardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(ChariotBoard board) {
        this.interactor.execute(new RepaintBoardInputData(board));
    }

}
