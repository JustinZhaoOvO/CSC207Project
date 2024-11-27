package interface_adapter.controller;

import interface_adapter.timer.TimerController;
import use_case.timer.TimerInteractor;
import view.timer.TimerPresenter;
import view.timer.TimerView;
import view.timer.TimerViewModel;

import javax.swing.*;

public class TimerManager {
    private final TimerController timerController;
    private final TimerView timerView;
    private Timer switchTurnTimer;
    private boolean isPaused = false;
    private final long totalTimePerPlayer;
    private RestartListener restartListener;

    // 构造函数
    public TimerManager(long totalTimePerPlayer, TimerView timerView) {
        this.totalTimePerPlayer = totalTimePerPlayer;
        this.timerView = timerView;
        this.timerController = initializeTimer();
        initializeSwitchTurnTimer();
        setupActionListeners();
    }

    // 初始化 Timer 组件
    private TimerController initializeTimer() {
        TimerViewModel timerViewModel = new TimerViewModel();
        TimerPresenter timerPresenter = new TimerPresenter(timerViewModel);
        TimerInteractor timerInteractor = new TimerInteractor(totalTimePerPlayer, timerPresenter);
        TimerController timerController = new TimerController(timerInteractor);
        timerViewModel.addPropertyChangeListener(timerView);
        timerController.startGame();
        return timerController;
    }

    // 初始化回合切换定时器
    private void initializeSwitchTurnTimer() {
        switchTurnTimer = new Timer(5000, e -> {
            if (!isPaused) {
                System.out.println("Switching turn");
                timerController.switchTurn();
            }
        });
        switchTurnTimer.start();
    }

    // 设置暂停、开始和重启按钮的监听器
    private void setupActionListeners() {
        // 暂停按钮
        timerView.addPauseActionListener(e -> {
            System.out.println("Pause button clicked");
            timerController.pauseGame();
            isPaused = true;
            switchTurnTimer.stop();
            timerView.setPaused(isPaused);
        });

        // 开始按钮
        timerView.addStartActionListener(e -> {
            System.out.println("Start button clicked");
            timerController.resumeGame();
            isPaused = false;
            switchTurnTimer.start();
            timerView.setPaused(isPaused);
        });

        // 重启按钮
        timerView.addRestartActionListener(e -> {
            System.out.println("Restart button clicked");
            resetTimerAndGame();
            // 通知重启监听器（如果有）
            if (restartListener != null) {
                restartListener.onRestart();
            }
        });
    }

    // 重置计时器和游戏
    public void resetTimerAndGame() {
        timerController.stopGame();
        timerController.resetTimers();
        isPaused = false;
        timerView.setPaused(isPaused);
        timerController.startGame();
        timerController.resumeGame();
        if (switchTurnTimer != null) {
            switchTurnTimer.restart();
        }
    }

    // 接口，用于通知重启事件
    public interface RestartListener {
        void onRestart();
    }

    // 设置重启监听器
    public void setRestartListener(RestartListener listener) {
        this.restartListener = listener;
    }
}