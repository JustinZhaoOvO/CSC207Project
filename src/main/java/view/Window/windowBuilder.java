package view.Window;
//CreateTime: 2024-11-27 3:34 p.m.

import interface_adapter.board.BoardViewModel;
import interface_adapter.board.move.MoveController;
import interface_adapter.board.move.MovePresenter;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.repaintboard.RepaintBoardPresenter;
import interface_adapter.board.select.SelectController;
import interface_adapter.board.select.SelectPresenter;
import interface_adapter.controller.TimerManager;
import interface_adapter.window.WindowViewModel;
import use_case.board.move.MoveInteractor;
import use_case.board.repaintboard.RepaintBoardInteractor;
import use_case.board.select.SelectInteractor;
import view.BoardView.BoardLayout;
import view.BoardView.BoardView;
import view.BoardView.ColorConstants;
import view.timer.TimerView;

public class windowBuilder {

    private final WindowView windowView;
    private final WindowLayout windowLayout;
    private final WindowViewModel windowViewModel;
    private TimerManager timerManager; // Add timerManager field

    public windowBuilder() {
        this.windowView = new WindowView();
        this.windowViewModel = new WindowViewModel(windowView.getViewName());
        this.windowView.setBackground(ColorConstants.LIGHTBLUE);
        this.windowLayout = new WindowLayout();
        this.windowView.setLayout(windowLayout);
    }

    public windowBuilder addBoard(){
        // Initialize board and layout
        BoardView boardView = new BoardView(windowViewModel);
        boardView.setLayout(new BoardLayout());

        // Initialize boardView in windowView and layout
        this.windowView.setBoardView(boardView);
        this.windowView.add(boardView);
        this.windowLayout.setBoardView(boardView);
        this.windowViewModel.addPropertyChangeListener(boardView);

        // Initialize the controllers
        BoardViewModel boardViewModel = new BoardViewModel();
        RepaintBoardPresenter repaintBoardPresenter = new RepaintBoardPresenter(boardViewModel);
        RepaintBoardInteractor repaintBoardInteractor = new RepaintBoardInteractor(repaintBoardPresenter);
        RepaintBoardController repaintBoardController = new RepaintBoardController(repaintBoardInteractor);

        SelectPresenter selectPresenter = new SelectPresenter(boardViewModel);
        SelectInteractor selectInteractor = new SelectInteractor(selectPresenter);
        SelectController selectController = new SelectController(selectInteractor);

        MovePresenter movePresenter = new MovePresenter(boardViewModel, repaintBoardController, selectController, windowViewModel);
        MoveInteractor moveInteractor = new MoveInteractor(movePresenter);
        MoveController moveController = new MoveController(moveInteractor);

        // Set Controllers
        boardView.setRepaintBoardController(repaintBoardController);
        boardView.setSelectController(selectController);
        boardView.setMoveController(moveController);

        // Add BoardView to the listener list of BoardViewModel
        boardViewModel.addPropertyChangeListener(boardView);

        return this;
    }

    public windowBuilder addTimer(){
        // Initialize timer components
        long totalTimePerPlayer = 5 * 60 * 1000; // 5 minutes per player
        TimerView timerView = new TimerView(totalTimePerPlayer);

        // Instantiate TimerManager with WindowViewModel
        timerManager = new TimerManager(totalTimePerPlayer, timerView, windowViewModel);

        // Add TimerView to windowView
        this.windowView.add(timerView);

        // Set TimerView in layout
        this.windowLayout.setTimerView(timerView);

        return this;
    }

    public WindowView build() {
        return windowView;
    }
}