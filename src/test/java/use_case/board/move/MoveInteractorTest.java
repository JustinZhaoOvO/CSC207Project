package use_case.board.move;
//CreateTime: 2024-11-25 3:12 p.m.
import api_adapters.ChariotAPI.ChariotBoard;
import chariot.util.Board;
import entity.Coordinate;
import interface_adapter.board.move.MoveController;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class MoveInteractorTest {
    @Test
    void switchBlackSelectedTest() {
        ChariotBoard chariotBoard = new ChariotBoard();

        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6 a6a7");

        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isRepaint() && outputData.isSelect());
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard, Coordinate.fromString("c6"), new ArrayList<>());
    }

    @Test
    void switchWhiteSelectedTest() {
        ChariotBoard chariotBoard = new ChariotBoard();

        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6");

        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isRepaint() && outputData.isSelect());
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard, Coordinate.fromString("a6"), new ArrayList<>());
    }

    @Test
    void validMoveTest() {

        ChariotBoard chariotBoard = new ChariotBoard();

        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6 a6a7");

        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.board().equals(chariotBoard)
                        && outputData.getCoordinate().equals(Coordinate.fromString("b8"))
                        && outputData.isRepaint());
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard,
                Coordinate.fromString("b8"), chariotBoard.getValidMovesOfPosition("c6"));
    }

    @Test
    void invalidMoveTest() {
        ChariotBoard chariotBoard = new ChariotBoard();

        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6 a6a7");

        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isRepaint());
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard,
                Coordinate.fromString("b7"), chariotBoard.getValidMovesOfPosition("c6"));
    }

    @Test
    void gameOverTest() {
        ChariotBoard chariotBoard = new ChariotBoard();
        chariotBoard.move("h2h4 g7g5 h4g5 h7h6 g5h6 a7a6 h6h7 a6a5 h7g8q a5a4 g8h8 a4a3 h8h5 f8h6 h5h6 a3b2");
        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isGameOver()
                        && outputData.getGameState() == Board.GameState.checkmate);
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard,
                Coordinate.fromString("h8"), chariotBoard.getValidMovesOfPosition("h6"));
    }

    @Test
    void promotionTest(){
        ChariotBoard chariotBoard = new ChariotBoard();

        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6 a6a7 c6b8");

        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isPromotion());
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard,
                Coordinate.fromString("b8"), chariotBoard.getValidMovesOfPosition("a7"));
    }
}
