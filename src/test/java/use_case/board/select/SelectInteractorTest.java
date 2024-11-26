package use_case.board.select;
//CreateTime: 2024-11-25 3:17 p.m.

import entity.ChariotBoard;
import entity.Coordinate;
import interface_adapter.board.select.SelectController;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectInteractorTest {
    @Test
    void selectTest() {
        SelectOutputBoundary  selectOutputBoundary = new SelectOutputBoundary() {
            @Override
            public void prepareSuccessView(SelectOutputData outputData) {
                assertEquals(outputData.coordinate(), Coordinate.fromString("a7"));
                List<String> arr = new ArrayList<>();
                arr.add("a7b8");
                assertEquals(outputData.validMoves(), arr);
            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };
        SelectInputBoundary selectInteractor = new SelectInteractor(selectOutputBoundary);
        SelectController selectController = new SelectController(selectInteractor);
        ChariotBoard chariotBoard = new ChariotBoard();
        chariotBoard.move("a2a4 b7b5 a4b5 a7a6 b5a6 b8c6 a6a7 c6b8");
        selectController.execute(chariotBoard, Coordinate.fromString("a7"));
    }
}
