package view.timer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TimerViewModel {
    private long player1Time;
    private long player2Time;
    private boolean isPlayer1Turn;
    private int timeUpPlayer;
    private final PropertyChangeSupport support;

    public TimerViewModel() {
        support = new PropertyChangeSupport(this);
        timeUpPlayer = 0;
    }

    public void setPlayer1Time(long player1Time) {
        long oldTime = this.player1Time;
        this.player1Time = player1Time;
        support.firePropertyChange("player1Time", oldTime, player1Time);
    }

    public void setPlayer2Time(long player2Time) {
        long oldTime = this.player2Time;
        this.player2Time = player2Time;
        support.firePropertyChange("player2Time", oldTime, player2Time);
    }

    public void setPlayer1Turn(boolean isPlayer1Turn) {
        boolean oldTurn = this.isPlayer1Turn;
        this.isPlayer1Turn = isPlayer1Turn;
        support.firePropertyChange("isPlayer1Turn", oldTurn, isPlayer1Turn);
    }

    public void setTimeUp(int playerNumber) {
        int oldTimeUp = this.timeUpPlayer;
        this.timeUpPlayer = playerNumber;
        support.firePropertyChange("timeUp", oldTimeUp, playerNumber);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
}