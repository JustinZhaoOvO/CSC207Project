package view.BoardView;
//CreateTime: 2024-11-14 4:04 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import interface_adapter.board.BoardViewModel;
import interface_adapter.board.move.MoveController;
import interface_adapter.board.move.MovePresenter;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.repaintboard.RepaintBoardPresenter;
import interface_adapter.board.select.SelectController;
import interface_adapter.board.select.SelectPresenter;
import use_case.board.move.MoveInteractor;
import use_case.board.repaintboard.RepaintBoardInteractor;
import use_case.board.select.SelectInteractor;
import view.WindowView.WindowLayout;
import view.WindowView.WindowView;

import javax.swing.*;

public class ViewTest extends JFrame{
    public static void main(String[] args) {
        ViewTest viewTest = new ViewTest();
    }

    public ViewTest(){

        //setup JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("ViewTest");
        this.setLayout(null);
        this.setSize(814, 637);
        this.setLocation(250, 150);
        this.setLayout(null);

        //intialize boardView
        BoardView boardView = new BoardView();

        //initialize window Component
        JPanel window = new WindowView();
        window.setLayout(new WindowLayout(boardView));
        this.setContentPane(window);
        window.setBackground(ColorConstants.LIGHTBLUE);

        //add all child components into the window
        window.add(boardView);

        //set the boardView layout
        boardView.setLayout(new BoardLayout());

        //create the board object
        ChariotBoard chariotBoard = new ChariotBoard();
//        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6 a6a7 c6b8"); //Move Pawn to the second row

        //initialize the controllers
        BoardViewModel boardViewModel = new BoardViewModel();
        RepaintBoardPresenter repaintBoardPresenter = new RepaintBoardPresenter(boardViewModel);
        RepaintBoardInteractor repaintBoardInteractor = new RepaintBoardInteractor(repaintBoardPresenter);
        RepaintBoardController repaintBoardController = new RepaintBoardController(repaintBoardInteractor);

        SelectPresenter selectPresenter = new SelectPresenter(boardViewModel);
        SelectInteractor selectInteractor = new SelectInteractor(selectPresenter);
        SelectController selectController = new SelectController(selectInteractor);

        MovePresenter movePresenter = new MovePresenter(boardViewModel,repaintBoardController, selectController);
        MoveInteractor moveInteractor = new MoveInteractor(movePresenter);
        MoveController moveController = new MoveController(moveInteractor);

        //set Controllers
        boardView.setRepaintBoardController(repaintBoardController);
        boardView.setSelectController(selectController);
        boardView.setMoveController(moveController);

        //add BoardView to the listener list of BoardViewModel
        boardViewModel.addPropertyChangeListener(boardView);

        //start the game with a new board
        boardView.restartTheGameWith(chariotBoard);

        this.setVisible(true);
    }
}
