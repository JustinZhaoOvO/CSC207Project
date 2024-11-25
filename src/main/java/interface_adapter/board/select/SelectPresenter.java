package interface_adapter.board.select;
//CreateTime: 2024-11-20 3:28 p.m.

import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import interface_adapter.board.BoardViewModel;
import use_case.board.select.SelectOutputBoundary;
import use_case.board.select.SelectOutputData;

public class SelectPresenter implements SelectOutputBoundary {

    private BoardViewModel viewModel;

    public SelectPresenter(BoardViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SelectOutputData outputData) {
        BoardState boardState = new BoardState();
        boardState.setSelected(outputData.coordinate());
        boardState.setValidMoves(outputData.validMoves());
        viewModel.setState(boardState);
        viewModel.firePropertyChanged(BoardStateConstants.SELECT);
    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
