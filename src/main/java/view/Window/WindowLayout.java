package view.Window;
//CreateTime: 2024-11-16 12:11 p.m.

import view.BoardView.BoardView;
import view.LayoutAdapter;

import java.awt.*;

public class WindowLayout extends LayoutAdapter {

    private BoardView boardView;

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    @Override
    public void layoutContainer(Container parent) {

        //board view layout
        int len = Math.min(parent.getWidth()* 3 / 5, parent.getHeight());
        super.layoutContainer(parent);
        boardView.setBounds(0, 0, len, len);


    }
}
