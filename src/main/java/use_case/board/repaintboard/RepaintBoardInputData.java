package use_case.board.repaintboard;
//CreateTime: 2024-11-14 4:57 p.m.


import api_adapters.ChariotAPI.ChariotBoard;
import use_case.board.BoardInputData;

public class RepaintBoardInputData extends BoardInputData {

    public RepaintBoardInputData(ChariotBoard board) {
        super(board);
    }
}
