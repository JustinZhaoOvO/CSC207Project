package interface_adapter.timer;

import use_case.timer.TimerInputBoundary;

public class TimerController {
    private final TimerInputBoundary interactor;

    public TimerController(TimerInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void startGame() {
        interactor.startTimer();
    }

    public void stopGame() {
        interactor.stopTimer();
    }

    public void switchTurn() {
        interactor.switchTurn();
    }

    public void pauseGame() {    // 新增方法
        interactor.pauseTimer();
    }

    public void resumeGame() {   // 新增方法
        interactor.resumeTimer();
    }
}