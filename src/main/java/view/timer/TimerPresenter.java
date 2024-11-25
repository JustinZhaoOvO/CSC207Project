package view.timer;

import interface_adapter.timer.TimerOutputBoundary;

public class TimerPresenter implements TimerOutputBoundary {
    private final TimerViewModel viewModel;

    public TimerPresenter(TimerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void updateTime(long player1Time, long player2Time, boolean isPlayer1Turn) {
        viewModel.setPlayer1Time(player1Time);
        viewModel.setPlayer2Time(player2Time);
        viewModel.setPlayer1Turn(isPlayer1Turn);
    }

    @Override
    public void timeUp(int playerNumber) {
        viewModel.setTimeUp(playerNumber);
    }
}