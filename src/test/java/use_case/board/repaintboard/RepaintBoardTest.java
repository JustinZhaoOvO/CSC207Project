package use_case.board.repaintboard;
//CreateTime: 2024-11-18 3:12 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import entity.BoardConstants;
import interface_adapter.board.BoardViewModel;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.repaintboard.RepaintBoardPresenter;
import org.junit.Test;
import view.BoardView.BoardLayout;
import view.BoardView.BoardView;
import view.BoardView.ColorConstants;
import view.BoardView.PiecesView.PiecesView;
import view.WindowView.WindowLayout;
import view.WindowView.WindowView;

import javax.swing.*;

public class RepaintBoardTest {

    @Test
    public void RepaintBoardTest() {


        BoardView boardView = new BoardView();

        JPanel window = new WindowView();
        window.setLayout(new WindowLayout(boardView));
        window.setBackground(ColorConstants.LIGHTBLUE);

        window.add(boardView);


        //set the boardView layout
        boardView.setLayout(new BoardLayout());

        //create the board object
        ChariotBoard chariotBoard = new ChariotBoard();

        BoardViewModel boardViewModel = new BoardViewModel();
        RepaintBoardPresenter repaintBoardPresenter = new RepaintBoardPresenter(boardViewModel);
        RepaintBoardInteractor repaintBoardInteractor = new RepaintBoardInteractor(repaintBoardPresenter);
        RepaintBoardController repaintBoardController = new RepaintBoardController(repaintBoardInteractor);

        //add BoardView to the listener list of BoardViewModel
        boardViewModel.addPropertyChangeListener(boardView);

        //repaint the board
        repaintBoardController.execute(chariotBoard);

    }
}
