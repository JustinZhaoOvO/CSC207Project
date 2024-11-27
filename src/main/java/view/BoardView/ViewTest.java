package view.BoardView;
//CreateTime: 2024-11-14 4:04 p.m.

import entity.ChariotBoard;
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
import view.Window.WindowLayout;
import view.Window.WindowView;

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
        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6 a6a7 c6b8"); //Move Pawn to the second row
//        chariotBoard.move("e4 c6 d4 d5 e5 c5 dxc5 e6 Nf3 Bxc5 Bd3 Ne7 O-O Ng6 Qe2 O-O c3 Nd7 b4 Be7 Na3 a5 Nc2 Qc7 Bxg6 fxg6 Ncd4 Nb6 Bg5 Bxg5 Nxg5 Qe7 f4 h6 Nh3 axb4 Rf3 bxc3 Rxc3 Nc4 Rg3 Qf7 Rb1 b6 Nf3 Ba6 Qe1 Bb7 Nh4 Rxa2 Rxg6 d4 f5 exf5 Nf4 Be4 e6 Qe8 Ra1 Rxa1 Qxa1 Ne5 Qxd4 Nxg6 Nhxg6 Rf6 Ne5 Rxe6 Qc4 b5 Qxe6+ Qxe6 Nxe6 b4 Nc5 Bd5 Kf2 Kf8 g3 Ke7 Ke3 Kd6 Kd4 b3 Na4 g5 Nc3 Bg8 Nd3 Bh7 Nd1 Ke6 Nc5+ Kf6 Nxb3 f4 Nd2 Kf5 Nf2 h5 Nc4 fxg3 hxg3 Bg8 Ne3+ Kf6 Ne4+ Kg6 Ke5 Bb3 Nf2 Ba4 Nd5 h4 g4 h3 Nxh3 Bd1 Nf2 Bxg4 Nxg4 Kh5 Ndf6+ Kh4 Ke4 Kg3 Ke3 Kg2 Ne4 Kh3 Kf3 Kh4 Ng3 Kh3 Nh5 Kh4 Nhf6 Kh3 Nf2+ Kh4 N6g4 Kh5 Kg3 Kg6 Nd3 Kf5 Kf3 Ke6 Ke4 Kd6 Nb4 Kc5 Nd5 Kd6 Kd4 Ke6 Nde3 Kd6 Nf5+ Kc6 Kc4 Kb6 Nd4 Kc7 Kc5 Kd7 Nb5 Ke7 Nd6 Kf8 Nf5 Kf7 Kd6 Kg6 Ng3 Kf7 Kd7 Kf8 Nh5 Kf7 Nhf6 Kg7 Ke7 Kg6 Ke6 Kg7 Nh5+ Kf8 Ng3 Ke8 Nf5 Kd8 Kd6 Ke8 Ne7 Kd8 Nc6+ Kc8 Na5 Kd8 Nb7+ Ke8 Ke6 Kf8 Nd6 Kg7 Kf5 Kf8 Kf6 Kg8 Nf5 Kf8 Ng7 Kg8 Ne6 Kh8 Kf7 Kh7 Nf8+ Kh8 Ne5");
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