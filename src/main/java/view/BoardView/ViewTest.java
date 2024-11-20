package view.BoardView;
//CreateTime: 2024-11-14 4:04 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import entity.BoardConstants;
import interface_adapter.board.BoardViewModel;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.repaintboard.RepaintBoardPresenter;
import use_case.board.repaintboard.RepaintBoardInteractor;
import view.BoardView.PiecesView.PiecesView;
import view.WindowView.WindowLayout;
import view.WindowView.WindowView;

import javax.swing.*;
import java.awt.event.MouseAdapter;

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

        //initialize the repaint function
        BoardViewModel boardViewModel = new BoardViewModel();
        RepaintBoardPresenter repaintBoardPresenter = new RepaintBoardPresenter(boardViewModel);
        RepaintBoardInteractor repaintBoardInteractor = new RepaintBoardInteractor(repaintBoardPresenter);
        RepaintBoardController repaintBoardController = new RepaintBoardController(repaintBoardInteractor);

        //add BoardView to the listener list of BoardViewModel
        boardViewModel.addPropertyChangeListener(boardView);

        //repaint the board
        repaintBoardController.execute(chariotBoard);

        this.setVisible(true);
    }
}
