package view.BoardView;
//CreateTime: 2024-11-11 1:22 p.m.

import java.awt.*;
import java.awt.event.MouseEvent;

public class BoardListener extends MouseListenerAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
//        Component component = e.getComponent();
//        if (component instanceof BoardView){
//            BoardView boardView = (BoardView) component;
//        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        Component component = e.getComponent();
        if (component instanceof BoardView boardView){
            boardView.mouseEnterPiece(null);
        }
    }
}
