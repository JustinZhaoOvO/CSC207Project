package use_case.board.select;
//CreateTime: 2024-11-20 3:14 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import view.BoardView.PiecesView.PiecesView;

public record SelectInputData(ChariotBoard board, PiecesView piecesView) {

}
