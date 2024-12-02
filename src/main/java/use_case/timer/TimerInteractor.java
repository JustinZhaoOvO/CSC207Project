package use_case.timer;

import entity.Timer;
import interface_adapter.timer.TimerInputBoundary;
import interface_adapter.timer.TimerOutputBoundary;

public class TimerInteractor implements TimerInputBoundary, Runnable {
    private final Timer player1Timer;
    private final Timer player2Timer;
    private final TimerOutputBoundary outputBoundary;
    private volatile boolean running;
    private volatile boolean paused;
    private boolean isPlayer1Turn;
    private Thread timerThread;

    // Constructor
    public TimerInteractor(long totalTimePerPlayer, TimerOutputBoundary outputBoundary) {
        this.player1Timer = new Timer(totalTimePerPlayer);
        this.player2Timer = new Timer(totalTimePerPlayer);
        this.outputBoundary = outputBoundary;
        this.running = false;
        this.paused = false;
        this.isPlayer1Turn = true; // Start with player 1's turn
    }

    @Override
    public synchronized void startTimer() {
        if (!running) {
            paused = false; // Reset paused flag when starting the timer
            running = true;
            timerThread = new Thread(this);
            timerThread.start();
        }
    }

    @Override
    public synchronized void stopTimer() {
        if (running) {
            running = false;
            if (timerThread != null) {
                timerThread.interrupt();
                try {
                    timerThread.join(); // Wait for the thread to finish
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                timerThread = null;
            }
        }
    }

    @Override
    public void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
        updateTime();
    }

    @Override
    public void pauseTimer() {
        paused = true;
    }

    @Override
    public void resumeTimer() {
        paused = false;
        synchronized (this) {
            this.notify(); // Wake up waiting thread
        }
    }

    @Override
    public void resetTimers() {
        player1Timer.reset();
        player2Timer.reset();
        isPlayer1Turn = true;
        updateTime();
    }

    private void updateTime() {
        outputBoundary.updateTime(
                player1Timer.getRemainingTime(),
                player2Timer.getRemainingTime(),
                isPlayer1Turn
        );
    }

    @Override
    public void run() {
        long previousTime = System.currentTimeMillis();
        try {
            while (running) {
                if (paused) {
                    synchronized (this) {
                        try {
                            this.wait(); // Wait until notified
                        } catch (InterruptedException e) {
                            if (!running) {
                                // Exit loop if not running
                                break;
                            }
                            // Handle interruption during pause
                            Thread.currentThread().interrupt();
                        }
                    }
                    previousTime = System.currentTimeMillis(); // Reset time after pause
                    continue;
                }
                long currentTime = System.currentTimeMillis();
                long deltaTime = currentTime - previousTime;
                previousTime = currentTime;

                if (isPlayer1Turn) {
                    player1Timer.decrement(deltaTime);
                } else {
                    player2Timer.decrement(deltaTime);
                }

                updateTime();

                if (player1Timer.isTimeUp() || player2Timer.isTimeUp()) {
                    running = false;
                    // Notify which player's time is up
                    outputBoundary.timeUp(isPlayer1Turn ? 1 : 2);
                    break; // Exit the loop
                }

                try {
                    Thread.sleep(100); // Update every 100 milliseconds
                } catch (InterruptedException e) {
                    if (!running) {
                        // Exit loop if not running
                        break;
                    }
                    // Handle interruption during sleep
                    Thread.currentThread().interrupt();
                }
            }
        } finally {
            running = false; // Ensure running is set to false when thread ends
        }
    }
}