package use_case.board.repaintboard;
//CreateTime: 2024-11-18 3:12 p.m.

import api_adapters.ChariotAPI.ChariotBoard;
import entity.Coordinate;
import entity.ImageConstants;
import interface_adapter.board.repaintboard.RepaintBoardController;
import org.junit.jupiter.api.Test;
import view.BoardView.ColorConstants;
import view.BoardView.PiecesView.PiecesView;

import static org.junit.jupiter.api.Assertions.*;

class RepaintBoardInteractorTest {

    @Test
    void repaintTest() {

        RepaintBoardOutputBoundary repaintBoardTestPresenter = new RepaintBoardOutputBoundary() {

            @Override
            public void prepareSuccessView(RepaintBoardOutputData outputData) {
                assertEquals(outputData.board()[1][0],
                        new PiecesView(ColorConstants.YELLOWWHITE, ImageConstants.WHITEPAWN, new Coordinate(1, 0)));
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };
        RepaintBoardInputBoundary repaintBoardInteractor = new RepaintBoardInteractor(repaintBoardTestPresenter);
        RepaintBoardController repaintBoardController = new RepaintBoardController(repaintBoardInteractor);

        ChariotBoard chariotBoard = new ChariotBoard();
        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6 a6a7 c6b8");
        repaintBoardController.execute(chariotBoard);
    }


}
