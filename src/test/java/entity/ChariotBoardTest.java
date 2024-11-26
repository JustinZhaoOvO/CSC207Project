package entity;
//CreateTime: 2024-11-26 3:28 p.m.
import interface_adapter.board.move.MoveController;
import org.junit.jupiter.api.Test;
import use_case.board.move.MoveInteractor;
import use_case.board.move.MoveOutputBoundary;
import use_case.board.move.MoveOutputData;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChariotBoardTest {

    @Test
    void whitePromotionTest(){
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

    @Test
    void blackPromotionTest(){
        ChariotBoard chariotBoard = new ChariotBoard();

        chariotBoard.move("b2b4 a7a5 a2a3 a5b4 b1c3 b4a3 a1b1 a3a2 c3b5");

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
                Coordinate.fromString("b1"), chariotBoard.getValidMovesOfPosition("a2"));
    }

    @Test
    void CheckmateWhiteTest() {
        ChariotBoard chariotBoard = new ChariotBoard();
        chariotBoard.move("g2g4 h7h5 h2h3 h5g4 g1f3 g4f3 f1g2 f3g2 h3h4");
        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isGameOver()
                        && outputData.getMsg().equals("Checkmate! Black win"));
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        List<String> strings = new ArrayList<>();
        strings.add("g2h1q");
        moveController.execute(chariotBoard,
                Coordinate.fromString("h1"), strings);
    }


    @Test
    void toStringTest() {
        ChariotBoard chariotBoard = new ChariotBoard();
        assertEquals(chariotBoard.toString(),
                "♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜\n" +
                "♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟\n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "               \n" +
                "♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙\n" +
                "♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖");
    }
}
