package interface_adapter.timer;

public interface TimerOutputBoundary {
    void updateTime(long player1Time, long player2Time, boolean isPlayer1Turn);
    void timeUp(int playerNumber);
}