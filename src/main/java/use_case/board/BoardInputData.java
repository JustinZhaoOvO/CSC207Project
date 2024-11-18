package use_case.board;
//CreateTime: 2024-11-14 4:59 p.m.

import api_adapters.ChariotAPI.ChariotBoard;

public class BoardInputData {

    private ChariotBoard board;

    public BoardInputData(ChariotBoard board) {
        this.board = board;
    }

    public ChariotBoard getBoard() {
        return board;
    }
}
