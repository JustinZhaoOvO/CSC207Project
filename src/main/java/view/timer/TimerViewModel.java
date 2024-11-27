package view.timer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class TimerViewModel {
    private final PropertyChangeSupport support;
    private long player1Time;
    private long player2Time;
    private boolean isPlayer1Turn;
    private int timeUpPlayer;

    public TimerViewModel() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void updateTime(long player1Time, long player2Time, boolean isPlayer1Turn) {
        support.firePropertyChange("player1Time", this.player1Time, player1Time);
        this.player1Time = player1Time;

        support.firePropertyChange("player2Time", this.player2Time, player2Time);
        this.player2Time = player2Time;

        support.firePropertyChange("isPlayer1Turn", this.isPlayer1Turn, isPlayer1Turn);
        this.isPlayer1Turn = isPlayer1Turn;
    }

    public void timeUp(int playerNumber) {
        support.firePropertyChange("timeUp", this.timeUpPlayer, playerNumber);
        this.timeUpPlayer = playerNumber;
    }
}