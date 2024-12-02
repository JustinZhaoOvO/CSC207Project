package interface_adapter.board.move;
//CreateTime: 2024-11-20 3:28 p.m.

import entity.ChariotBoard;
import entity.Coordinate;
import use_case.board.move.MoveInputBoundary;
import use_case.board.move.MoveInputData;

import java.util.List;

/**
 * Perform a move
 */
public class MoveController {
    private final MoveInputBoundary interactor;

    public MoveController(MoveInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * perform a move on given board
     * @param board : a chariot board
     * @param coordinate : the coordinate was clicked
     * @param validMoves : an array contains all current valid moves.
     */
    public void execute(ChariotBoard board, Coordinate coordinate, List<String> validMoves) {
        this.interactor.execute(new MoveInputData(board, coordinate, validMoves));
    }
}
