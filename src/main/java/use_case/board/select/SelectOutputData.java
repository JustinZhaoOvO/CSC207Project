package use_case.board.select;
//CreateTime: 2024-11-20 3:15 p.m.

import entity.Coordinate;
import view.BoardView.PiecesView.PiecesView;

import java.util.List;

public record SelectOutputData(Coordinate coordinate, List<String> validMoves) {

}
