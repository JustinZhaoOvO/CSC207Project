package interface_adapter.board.repaintboard;
//CreateTime: 2024-11-14 4:20 p.m.

import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import interface_adapter.board.BoardViewModel;
import use_case.board.repaintboard.RepaintBoardOutputBoundary;
import use_case.board.repaintboard.RepaintBoardOutputData;

public class RepaintBoardPresenter implements RepaintBoardOutputBoundary {

    private BoardViewModel viewModel;

    public RepaintBoardPresenter(BoardViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(RepaintBoardOutputData outputData) {
        BoardState boardState = new BoardState();
        boardState.setCells(outputData.getBoard());
        boardState.setRepaintSuccess(true);
        viewModel.setState(boardState);
        viewModel.firePropertyChanged(BoardStateConstants.REPAINT);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        BoardState boardState = new BoardState();
        boardState.setRepaintSuccess(false);
        viewModel.setState(boardState);
        viewModel.firePropertyChanged(BoardStateConstants.REPAINT);
    }
}
