package view.Window;
//CreateTime: 2024-11-16 12:11 p.m.


import app.ViewStates;
import entity.ChariotBoard;
import view.BoardView.BoardView;
import view.timer.TimerView;

import javax.swing.*;
import java.awt.*;

public class WindowView extends JPanel{

    private final String viewName = ViewStates.BOARD_VIEW;

    private BoardView boardView;
    private TimerView timerView;

    public WindowView() {

    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public void setTimerView(TimerView timerView) {
        this.timerView = timerView;
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
