package interface_adapter.timer;

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

    public void pauseGame() {
        interactor.pauseTimer();
    }

    public void resumeGame() {
        interactor.resumeTimer();
    }

    public void resetTimers() {
        interactor.resetTimers();
    }
}