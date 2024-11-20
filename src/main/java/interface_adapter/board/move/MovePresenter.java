package interface_adapter.board.move;
//CreateTime: 2024-11-20 3:28 p.m.

import interface_adapter.board.BoardViewModel;
import use_case.board.move.MoveOutputBoundary;
import use_case.board.move.MoveOutputData;

public class MovePresenter implements MoveOutputBoundary {

    private BoardViewModel viewModel;

    public MovePresenter(BoardViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(MoveOutputData outputData) {

    }

    @Override
    public void prepareFailView(String errorMessage) {

    }
}
