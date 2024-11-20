package view.BoardView.PiecesView;
//CreateTime: 2024-11-11 10:38 a.m.


import view.BoardView.BoardView;
import view.BoardView.ColorConstants;
import view.BoardView.MouseListenerAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PiecesListener extends MouseListenerAdapter {

    private final BoardView boardView;

    public PiecesListener(BoardView boardView) {
        this.boardView = boardView;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof PiecesView piecesView){
            boardView.clickOn(piecesView);
        }
    }

    public void mouseEntered(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof PiecesView piecesView){
            boardView.mouseEnterPiece(piecesView);
        }
    }

    public void mouseExited(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof PiecesView){
            boardView.mouseEnterPiece(null);
        }
    }
}
