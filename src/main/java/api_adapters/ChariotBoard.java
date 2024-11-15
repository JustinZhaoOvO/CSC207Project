package api_adapters;
//CreateTime: 2024-11-10 7:10 p.m.

import chariot.util.Board;

import java.util.List;

public class ChariotBoard implements ChariotAdaptorInterface {
    private Board board;

    public ChariotBoard() {
        this.board = Board.fromStandardPosition();
    }

    public List<String> getValidMoves(){
        return this.board.validMoves().stream()
                .map(Board.Move::uci)
                .sorted()
                .toList();
    }

    public void move(String movesToPlay){
        this.board.play(movesToPlay);
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    public String toString(){
        return this.board.toString();
    }
}
