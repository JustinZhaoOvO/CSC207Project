package use_case.board;
//CreateTime: 2024-11-14 4:59 p.m.

import view.BoardView.PiecesView.PiecesView;


public class BoardOutputData {
    private PiecesView[][] board;


    public BoardOutputData(PiecesView[][] board) {

        this.board = board;
    }

    public PiecesView[][] getBoard() {
        return board;
    }
}
