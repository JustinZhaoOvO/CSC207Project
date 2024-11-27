package entity;

public class Timer {
    private long remainingTime; // in milliseconds
    private final long totalTime; // in milliseconds

    public Timer(long totalTime) {
        this.totalTime = totalTime;
        this.remainingTime = totalTime;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void decrement(long deltaTime) {
        remainingTime -= deltaTime;
        if (remainingTime < 0) {
            remainingTime = 0;
        }
    }

    public void reset() {
        remainingTime = totalTime;
    }

    public boolean isTimeUp() {
        return remainingTime <= 0;
    }

    public long getTotalTime() {
        return totalTime;
    }
}