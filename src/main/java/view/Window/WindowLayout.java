package view.Window;
//CreateTime: 2024-11-16 12:11 p.m.

import view.BoardView.BoardView;
import view.LayoutAdapter;
import view.timer.TimerView;

import java.awt.*;

public class WindowLayout extends LayoutAdapter {

    private BoardView boardView;
    private TimerView timerView;

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    @Override
    public void layoutContainer(Container parent) {

        int width = parent.getWidth();
        int height = parent.getHeight();
        //board view layout
        int len = Math.min(parent.getWidth()* 3 / 5, parent.getHeight());
        int timerWidth = Math.min(parent.getWidth() / 5, parent.getHeight());
        super.layoutContainer(parent);
        boardView.setBounds(0, 0, len, len);
        timerView.setBounds(800,0,timerWidth,600);

        super.layoutContainer(parent);
    }

    public void setTimerView(TimerView timerView) {
        this.timerView = timerView;
    }
}
