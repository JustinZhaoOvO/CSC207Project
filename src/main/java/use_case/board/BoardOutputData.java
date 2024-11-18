package use_case.board;
//CreateTime: 2024-11-14 4:59 p.m.

import entity.Cell;


public class BoardOutputData {
    private Cell[][] board;


    public BoardOutputData(Cell[][] board) {

        this.board = board;
    }

    public Cell[][] getBoard() {
        return board;
    }
}
