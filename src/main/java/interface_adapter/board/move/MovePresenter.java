package interface_adapter.board.move;
//CreateTime: 2024-11-20 3:28 p.m.

import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import interface_adapter.board.BoardViewModel;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.select.SelectController;
import use_case.board.move.MoveOutputBoundary;
import use_case.board.move.MoveOutputData;

public class MovePresenter implements MoveOutputBoundary {

    private final BoardViewModel boardViewModel;
    private final RepaintBoardController repaintBoardController;
    private final SelectController selectController;

    public MovePresenter(BoardViewModel boardViewModel,
                         RepaintBoardController repaintBoardController,
                          SelectController selectController) {
        this.boardViewModel = boardViewModel;

        this.repaintBoardController = repaintBoardController;
        this.selectController = selectController;
    }

    @Override
    public void prepareSuccessView(MoveOutputData outputData) {
        if (outputData.promotion()){
            BoardState boardState = new BoardState();
            boardState.setBlackTurn(outputData.board().isBlackToMove());
            boardViewModel.setState(boardState);
            boardViewModel.firePropertyChanged(BoardStateConstants.PROMOTION);
        }if (outputData.repaint()){
            repaintBoardController.execute(outputData.board());
        }if (outputData.select()){
            selectController.execute(outputData.board(), outputData.piecesView());
        }
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
