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

    // 构造函数
    public TimerInteractor(long totalTimePerPlayer, TimerOutputBoundary outputBoundary) {
        this.player1Timer = new Timer(totalTimePerPlayer);
        this.player2Timer = new Timer(totalTimePerPlayer);
        this.outputBoundary = outputBoundary;
        this.running = false;
        this.paused = false;
        this.isPlayer1Turn = true; // 初始为白方回合
    }

    @Override
    public void startTimer() {
        if (!running) {
            running = true;
            timerThread = new Thread(this);
            timerThread.start();
        }
    }

    @Override
    public void stopTimer() {
        running = false;
        if (timerThread != null) {
            timerThread.interrupt();
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
            this.notify(); // 唤醒等待的线程
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
        while (running) {
            if (paused) {
                synchronized (this) {
                    try {
                        this.wait(); // 等待直到被唤醒
                    } catch (InterruptedException e) {
                        if (!running) {
                            // 如果不再运行，退出循环
                            break;
                        }
                        e.printStackTrace();
                    }
                }
                previousTime = System.currentTimeMillis(); // 暂停后重置时间
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
                // 通知哪个玩家的时间到了
                outputBoundary.timeUp(isPlayer1Turn ? 1 : 2);
            }

            try {
                Thread.sleep(100); // 每100毫秒更新一次
            } catch (InterruptedException e) {
                if (!running) {
                    // 如果不再运行，退出循环
                    break;
                }
                e.printStackTrace();
            }
        }
    }
}