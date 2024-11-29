package use_case.board.move;
//CreateTime: 2024-11-25 3:12 p.m.
import entity.ChariotBoard;
import entity.Coordinate;
import interface_adapter.board.move.MoveController;
import interface_adapter.controller.TimerManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
                        && outputData.isRepaint() && outputData.isMoved() &&
                        "c6b8".equals(outputData.getMove()));
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
    void CheckMateBlackTest() {
        ChariotBoard chariotBoard = new ChariotBoard();
        chariotBoard.move("h2h4 g7g5 h4g5 h7h6 g5h6 a7a6 h6h7 a6a5 h7g8q a5a4 g8h8 a4a3 h8h5 f8h6 h5h6 a3b2");
        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isGameOver()
                        && outputData.getMsg().equals("Checkmate! White win"));
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
    void gameOverDrawByfiftyMoveRule(){
        ChariotBoard chariotBoard = new ChariotBoard();
        chariotBoard.move("e4 c6 d4 d5 e5 c5 dxc5 e6 Nf3 Bxc5 Bd3 Ne7 O-O Ng6 Qe2 O-O c3 Nd7 b4 " +
                "Be7 Na3 a5 Nc2 Qc7 Bxg6 fxg6 Ncd4 Nb6 Bg5 Bxg5 Nxg5 Qe7 f4 h6 Nh3 axb4 Rf3 bxc3 " +
                "Rxc3 Nc4 Rg3 Qf7 Rb1 b6 Nf3 Ba6 Qe1 Bb7 Nh4 Rxa2 Rxg6 d4 f5 exf5 Nf4 Be4 e6 Qe8 " +
                "Ra1 Rxa1 Qxa1 Ne5 Qxd4 Nxg6 Nhxg6 Rf6 Ne5 Rxe6 Qc4 b5 Qxe6+ Qxe6 Nxe6 b4 Nc5 Bd5 " +
                "Kf2 Kf8 g3 Ke7 Ke3 Kd6 Kd4 b3 Na4 g5 Nc3 Bg8 Nd3 Bh7 Nd1 Ke6 Nc5+ Kf6 Nxb3 f4 Nd2 " +
                "Kf5 Nf2 h5 Nc4 fxg3 hxg3 Bg8 Ne3+ Kf6 Ne4+ Kg6 Ke5 Bb3 Nf2 Ba4 Nd5 h4 g4 h3 Nxh3 " +
                "Bd1 Nf2 Bxg4 Nxg4 Kh5 Ndf6+ Kh4 Ke4 Kg3 Ke3 Kg2 Ne4 Kh3 Kf3 Kh4 Ng3 Kh3 Nh5 Kh4 " +
                "Nhf6 Kh3 Nf2+ Kh4 N6g4 Kh5 Kg3 Kg6 Nd3 Kf5 Kf3 Ke6 Ke4 Kd6 Nb4 Kc5 Nd5 Kd6 Kd4 Ke6 " +
                "Nde3 Kd6 Nf5+ Kc6 Kc4 Kb6 Nd4 Kc7 Kc5 Kd7 Nb5 Ke7 Nd6 Kf8 Nf5 Kf7 Kd6 Kg6 Ng3 Kf7 " +
                "Kd7 Kf8 Nh5 Kf7 Nhf6 Kg7 Ke7 Kg6 Ke6 Kg7 Nh5+ Kf8 Ng3 Ke8 Nf5 Kd8 Kd6 Ke8 Ne7 Kd8 " +
                "Nc6+ Kc8 Na5 Kd8 Nb7+ Ke8 Ke6 Kf8 Nd6 Kg7 Kf5 Kf8 Kf6 Kg8 Nf5 Kf8 Ng7 Kg8 Ne6 Kh8 " +
                "Kf7 Kh7 Nf8+ Kh8");
        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isGameOver()
                        && outputData.getMsg().equals("Draw by fifty move rule"));
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard,
                Coordinate.fromString("e5"), chariotBoard.getValidMovesOfPosition("g4"));
    }

    @Test
    void drawBythreeFoldRepetition() {
        ChariotBoard chariotBoard = new ChariotBoard();
        chariotBoard.move("a2a4 a7a5 a1a3 a8a6 a3a1 a6a8 a1a3 a8a6 a3a1");
        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isGameOver()
                        && outputData.getMsg().equals("Draw by three fold repetition"));
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard,
                Coordinate.fromString("a8"), chariotBoard.getValidMovesOfPosition("a6"));
    }

    @Test
    void DrawByStaleMateTest() {
        ChariotBoard chariotBoard = new ChariotBoard();
        chariotBoard.move("e4 c6 d4 d5 e5 c5 dxc5 e6 Nf3 Bxc5 Bd3 Ne7 O-O Ng6 Qe2 O-O c3 Nd7 b4 " +
                "Be7 Na3 a5 Nc2 Qc7 Bxg6 fxg6 Ncd4 Nb6 Bg5 Bxg5 Nxg5 Qe7 f4 h6 Nh3 axb4 Rf3 bxc3 " +
                "Rxc3 Nc4 Rg3 Qf7 Rb1 b6 Nf3 Ba6 Qe1 Bb7 Nh4 Rxa2 Rxg6 d4 f5 exf5 Nf4 Be4 e6 Qe8 " +
                "Ra1 Rxa1 Qxa1 Ne5 Qxd4 Nxg6 Nhxg6 Rf6 Ne5 Rxe6 Qc4 b5 Qxe6+ Qxe6 Nxe6 b4 Nc5 Bd5 " +
                "Kf2 Kf8 g3 Ke7 Ke3 Kd6 Kd4 b3 Na4 g5 Nc3 Bg8 Nd3 Bh7 Nd1 Ke6 Nc5+ Kf6 Nxb3 f4 Nd2 " +
                "Kf5 Nf2 h5 Nc4 fxg3 hxg3 Bg8 Ne3+ Kf6 Ne4+ Kg6 Ke5 Bb3 Nf2 Ba4 Nd5 h4 g4 h3 Nxh3 " +
                "Bd1 Nf2 Bxg4 Nxg4 Kh5 Ndf6+ Kh4 Ke4 Kg3 Ke3 Kg2 Ne4 Kh3 Kf3 Kh4 Ng3 Kh3 Nh5 Kh4 " +
                "Nhf6 Kh3 Nf2+ Kh4 N6g4 Kh5 Kg3 Kg6 Nd3 Kf5 Kf3 Ke6 Ke4 Kd6 Nb4 Kc5 Nd5 Kd6 Kd4 Ke6 " +
                "Nde3 Kd6 Nf5+ Kc6 Kc4 Kb6 Nd4 Kc7 Kc5 Kd7 Nb5 Ke7 Nd6 Kf8 Nf5 Kf7 Kd6 Kg6 Ng3 Kf7 " +
                "Kd7 Kf8 Nh5 Kf7 Nhf6 Kg7 Ke7 Kg6 Ke6 Kg7 Nh5+ Kf8 Ng3 Ke8 Nf5 Kd8 Kd6 Ke8 Ne7 Kd8 " +
                "Nc6+ Kc8 Na5 Kd8 Nb7+ Ke8 Ke6 Kf8 Nd6 Kg7 Kf5 Kf8 Kf6 Kg8 Nf5 Kf8 Ng7 Kg8 Ne6 Kh8 " +
                "Kf7 Kh7");
        chariotBoard.move("e6g5 h7h8");
        MoveOutputBoundary moveOutputBoundary = new MoveOutputBoundary() {
            @Override
            public void prepareSuccessView(MoveOutputData outputData) {
                assertTrue(outputData.isGameOver()
                        && outputData.getMsg().equals("Draw by stalemate"));
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };

        MoveInteractor moveInteractor = new MoveInteractor(moveOutputBoundary);

        MoveController moveController = new MoveController(moveInteractor);

        moveController.execute(chariotBoard,
                Coordinate.fromString("e5"), chariotBoard.getValidMovesOfPosition("g4"));
    }

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

        chariotBoard.move("g2g4 h7h5 h2h3 h5g4 g1f3 g4h3 f1g2 h3g2 f3g5");

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
                Coordinate.fromString("h1"), chariotBoard.getValidMovesOfPosition("g2"));
    }
}
