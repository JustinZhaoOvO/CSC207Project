package use_case.board.move;
//CreateTime: 2024-11-20 3:14 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import entity.Coordinate;
import view.BoardView.PiecesView.PiecesView;

import java.util.List;

public record MoveInputData(ChariotBoard board, Coordinate coordinate, List<String> validMoves) {

}
