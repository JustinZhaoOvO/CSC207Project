package use_case.board.select;
//CreateTime: 2024-11-20 3:15 p.m.

import view.BoardView.PiecesView.PiecesView;

import java.util.List;

public record SelectOutputData(PiecesView piecesView, List<String> validMoves) {

}
