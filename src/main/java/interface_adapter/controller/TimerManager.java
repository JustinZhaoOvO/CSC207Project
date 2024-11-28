package interface_adapter.controller;

import interface_adapter.timer.TimerController;
import interface_adapter.window.WindowViewModel;
import use_case.timer.TimerInteractor;
import view.timer.TimerPresenter;
import view.timer.TimerView;
import view.timer.TimerViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TimerManager {
    private final TimerController timerController;
    private final TimerView timerView;
    private boolean isPaused = false;
    private final long totalTimePerPlayer;
    private final WindowViewModel windowViewModel;

    public TimerManager(long totalTimePerPlayer, TimerView timerView, WindowViewModel windowViewModel) {
        this.totalTimePerPlayer = totalTimePerPlayer;
        this.timerView = timerView;
        this.windowViewModel = windowViewModel;
        this.timerController = initializeTimer();
        setupActionListeners();
        setupWindowViewModelListener();
    }

    private TimerController initializeTimer() {
        TimerViewModel timerViewModel = new TimerViewModel();
        TimerPresenter timerPresenter = new TimerPresenter(timerViewModel);
        TimerInteractor timerInteractor = new TimerInteractor(totalTimePerPlayer, timerPresenter);
        TimerController timerController = new TimerController(timerInteractor);

        timerViewModel.addPropertyChangeListener(timerView);

        // 监听 timeUp 事件以更新 WindowState
        timerViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("timeUp".equals(evt.getPropertyName())) {
                    int timeUpPlayer = (int) evt.getNewValue();
                    System.out.println("TimerManager detected timeUp for player " + timeUpPlayer);
                    // 更新 WindowState
                    windowViewModel.setGameOver(true);
                    windowViewModel.setBlackRanOutOfTime(timeUpPlayer == 2); // 假设 Player 2 是 Black
                }
            }
        });

        timerController.startGame();
        return timerController;
    }

    private void setupActionListeners() {
        // 暂停按钮
        timerView.addPauseActionListener(e -> {
            System.out.println("Pause button clicked");
            timerController.pauseGame();
            isPaused = true;
            timerView.setPaused(isPaused);
            // 更新 WindowState
            windowViewModel.setPaused(true);
        });

        // 开始按钮
        timerView.addStartActionListener(e -> {
            System.out.println("Start button clicked");
            timerController.resumeGame();
            isPaused = false;
            timerView.setPaused(isPaused);
            // 更新 WindowState
            windowViewModel.setPaused(false);
        });

        // 重启按钮
        timerView.addRestartActionListener(e -> {
            System.out.println("Restart button clicked");
            resetTimerAndGame();
            // 通知重启
            windowViewModel.setRestart(true);
        });
    }

    private void setupWindowViewModelListener() {
        windowViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("switchTurn".equals(evt.getPropertyName())) {
                    boolean switchTurn = (boolean) evt.getNewValue();
                    if (switchTurn) {
                        System.out.println("TimerManager detected switchTurn");
                        timerController.switchTurn();
                        // 处理完毕后，将 switchTurn 重置为 false
                        windowViewModel.setSwitchTurn(false);
                    }
                }

                if ("gameOver".equals(evt.getPropertyName())) {
                    boolean gameOver = (boolean) evt.getNewValue();
                    if (gameOver) {
                        System.out.println("TimerManager detected gameOver");
                        timerController.stopGame();
                        timerView.disablePauseAndStartButtons();
                    }
                }
            }
        });
    }

    public void resetTimerAndGame() {
        timerController.stopGame();
        timerController.resetTimers();
        isPaused = false;
        timerView.setPaused(isPaused);
        timerController.startGame();
        timerController.resumeGame();
        windowViewModel.setRestart(false); // 重置重启标志
    }

    // 接口，用于通知重启事件
    public interface RestartListener {
        void onRestart();
    }

    // 设置重启监听器
    public void setRestartListener(RestartListener listener) {
        // 当前设置中未使用
    }
}