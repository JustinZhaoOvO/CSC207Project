package view.Window;
//CreateTime: 2024-11-16 12:11 p.m.


import entity.ChariotBoard;
import view.BoardView.BoardView;

import javax.swing.*;
import java.awt.*;

public class WindowView extends JPanel{

    private final String viewName = "Mainwindow";

    private BoardView boardView;

    public WindowView() {

    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public void startTheGameWith(ChariotBoard chariotBoard){
        boardView.restartTheGameWith(chariotBoard);
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
