package view.Window;
//CreateTime: 2024-11-27 3:34 p.m.

import interface_adapter.board.BoardViewModel;
import interface_adapter.board.move.MoveController;
import interface_adapter.board.move.MovePresenter;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.repaintboard.RepaintBoardPresenter;
import interface_adapter.board.select.SelectController;
import interface_adapter.board.select.SelectPresenter;
import interface_adapter.window.WindowViewModel;
import use_case.board.move.MoveInteractor;
import use_case.board.repaintboard.RepaintBoardInteractor;
import use_case.board.select.SelectInteractor;
import view.BoardView.BoardLayout;
import view.BoardView.BoardView;
import view.BoardView.ColorConstants;

public class windowBuilder {

    private final WindowView windowView;
    private final WindowLayout windowLayout;
    private final WindowViewModel windowViewModel;

    public windowBuilder() {
        this.windowView = new WindowView();
        this.windowViewModel = new WindowViewModel(windowView.getViewName());
        this.windowView.setBackground(ColorConstants.LIGHTBLUE);
        this.windowLayout = new WindowLayout();
        this.windowView.setLayout(windowLayout);
    }

    public windowBuilder addBoard(){
        //initialize board and layout
        BoardView boardView = new BoardView();
        boardView.setLayout(new BoardLayout());

        //initalize boardView in windowView and layout
        this.windowView.setBoardView(boardView);
        this.windowView.add(boardView);
        this.windowLayout.setBoardView(boardView);
        this.windowViewModel.addPropertyChangeListener(boardView);


        //initialize the controllers
        BoardViewModel boardViewModel = new BoardViewModel();
        RepaintBoardPresenter repaintBoardPresenter = new RepaintBoardPresenter(boardViewModel);
        RepaintBoardInteractor repaintBoardInteractor = new RepaintBoardInteractor(repaintBoardPresenter);
        RepaintBoardController repaintBoardController = new RepaintBoardController(repaintBoardInteractor);

        SelectPresenter selectPresenter = new SelectPresenter(boardViewModel);
        SelectInteractor selectInteractor = new SelectInteractor(selectPresenter);
        SelectController selectController = new SelectController(selectInteractor);

        MovePresenter movePresenter = new MovePresenter(boardViewModel,repaintBoardController, selectController, windowViewModel);
        MoveInteractor moveInteractor = new MoveInteractor(movePresenter);
        MoveController moveController = new MoveController(moveInteractor);

        //set Controllers
        boardView.setRepaintBoardController(repaintBoardController);
        boardView.setSelectController(selectController);
        boardView.setMoveController(moveController);

        //add BoardView to the listener list of BoardViewModel
        boardViewModel.addPropertyChangeListener(boardView);

        return this;
    }





    public WindowView build() {
        return windowView;
    }
}
