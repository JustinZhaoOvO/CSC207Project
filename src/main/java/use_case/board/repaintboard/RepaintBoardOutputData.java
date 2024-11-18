package use_case.board.repaintboard;
//CreateTime: 2024-11-14 4:23 p.m.


import entity.Cell;
import use_case.board.BoardOutputData;

public class RepaintBoardOutputData extends BoardOutputData {

    public RepaintBoardOutputData(Cell[][] board) {
        super(board);
    }
}
