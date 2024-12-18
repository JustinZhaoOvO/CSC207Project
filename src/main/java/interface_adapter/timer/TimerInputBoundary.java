package interface_adapter.timer;

public interface TimerInputBoundary {
    void startTimer();
    void stopTimer();
    void switchTurn();
    void pauseTimer();
    void resumeTimer();
    void resetTimers();
}