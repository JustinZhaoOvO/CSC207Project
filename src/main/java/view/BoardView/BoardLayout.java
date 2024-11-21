package view.BoardView;
//CreateTime: 2024-11-15 5:15 p.m.

import view.BoardView.PiecesView.PiecesView;
import view.LayoutAdapter;

import java.awt.*;

public class BoardLayout extends LayoutAdapter {

    @Override
    public void layoutContainer(Container parent) {
        if (parent instanceof BoardView){
            int len = Math.min(parent.getHeight(), parent.getWidth()) / 8;
            BoardView boardView = (BoardView) parent;
            PiecesView[][] board = boardView.getBoard();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    PiecesView piecesView = board[i][j];
                    piecesView.setBounds(j * len, i *len , len, len);
                }
            }int len2 = len * 2;
            int len4 = len * 4;
            boardView.getBlackPromotion().setBounds(len2, len2, len4, len4);
            boardView.getWhitePromotion().setBounds(len2, len2, len4, len4);
        }
    }
}
