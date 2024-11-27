package view.Window;
//CreateTime: 2024-11-27 3:34 p.m.

import view.BoardView.BoardView;

public class windowBuilder {

    private final WindowView windowView;
    private final WindowLayout windowLayout;

    public windowBuilder() {
        this.windowView = new WindowView();
        this.windowLayout = new WindowLayout();
        this.windowView.setLayout(windowLayout);
    }

    public void addBoard(){
        BoardView boardView = new BoardView();
        this.windowView.add(boardView);
        this.windowLayout.setBoardView(boardView);

    }



    public WindowView build() {
        return windowView;
    }
}
