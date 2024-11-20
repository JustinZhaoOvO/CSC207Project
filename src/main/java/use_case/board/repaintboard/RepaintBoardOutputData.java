package use_case.board.repaintboard;
//CreateTime: 2024-11-14 4:23 p.m.


import use_case.board.BoardOutputData;
import view.BoardView.PiecesView.PiecesView;

public class RepaintBoardOutputData extends BoardOutputData {

    public RepaintBoardOutputData(PiecesView[][] board) {
        super(board);
    }
}
