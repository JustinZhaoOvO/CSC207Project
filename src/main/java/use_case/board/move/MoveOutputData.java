package use_case.board.move;
//CreateTime: 2024-11-20 3:15 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import view.BoardView.PiecesView.PiecesView;

public record MoveOutputData(boolean repaint, boolean select, ChariotBoard board, PiecesView piecesView) {

}
