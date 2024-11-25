package use_case.timer;

public interface TimerInputBoundary {
    void startTimer();
    void stopTimer();
    void switchTurn();
    void pauseTimer();
    void resumeTimer();
}