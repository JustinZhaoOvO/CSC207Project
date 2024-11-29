package entity;

public class Timer {
    private final long totalTime;
    private long remainingTime;

    public Timer(long totalTime) {
        this.totalTime = totalTime;
        this.remainingTime = totalTime;
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

    public long getRemainingTime() {
        return remainingTime;
    }

    public boolean isTimeUp() {
        return remainingTime <= 0;
    }
}