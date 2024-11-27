package view.WindowView;
//CreateTime: 2024-11-16 12:11 p.m.

import view.BoardView.BoardView;
import view.LayoutAdapter;

import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ComponentEvent;

public class WindowLayout extends LayoutAdapter {

    private final BoardView boardView;

    public WindowLayout(BoardView boardView) {
        this.boardView = boardView;
    }

    @Override
    public void layoutContainer(Container parent) {

        //board view layout
        int len = Math.min(parent.getWidth()* 3 / 4, parent.getHeight());
        super.layoutContainer(parent);
        boardView.setBounds(0, 0, len, len);


    }
}
