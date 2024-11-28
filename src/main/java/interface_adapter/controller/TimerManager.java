package interface_adapter.controller;

import interface_adapter.timer.TimerController;
import interface_adapter.window.WindowViewModel;
import use_case.timer.TimerInteractor;
import view.timer.TimerPresenter;
import view.timer.TimerView;
import view.timer.TimerViewModel;

public class TimerManager {
    private final TimerController timerController;
    private final TimerView timerView;
    private boolean isPaused = false;
    private final long totalTimePerPlayer;
    private RestartListener restartListener;
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

        // Listen to timeUp events to update WindowState
        timerViewModel.addPropertyChangeListener(evt -> {
            if ("timeUp".equals(evt.getPropertyName())) {
                int timeUpPlayer = (int) evt.getNewValue();
                System.out.println("TimerManager detected timeUp for player " + timeUpPlayer);
                // Update WindowState
                windowViewModel.setGameOver(true);
                windowViewModel.setBlackRanOutOfTime(timeUpPlayer == 1);
            }
        });

        timerController.startGame();
        return timerController;
    }

    private void setupActionListeners() {
        // Pause button
        timerView.addPauseActionListener(e -> {
            System.out.println("Pause button clicked");
            timerController.pauseGame();
            isPaused = true;
            timerView.setPaused(isPaused);
            // Update WindowState
            windowViewModel.setPaused(true);
        });

        // Start button
        timerView.addStartActionListener(e -> {
            System.out.println("Start button clicked");
            timerController.resumeGame();
            isPaused = false;
            timerView.setPaused(isPaused);
            // Update WindowState
            windowViewModel.setPaused(false);
        });

        // Restart button
        timerView.addRestartActionListener(e -> {
            System.out.println("Restart button clicked");
            resetTimerAndGame();
            // Notify restart
            windowViewModel.setRestart(true);
        });
    }

    private void setupWindowViewModelListener() {
        windowViewModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("switchTurn")) {
                boolean switchTurn = (boolean) evt.getNewValue();
                if (switchTurn) {
                    System.out.println("TimerManager detected switchTurn");
                    timerController.switchTurn();
                    // After handling, reset switchTurn to false
                    windowViewModel.setSwitchTurn(false);
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
        // Notify other components (e.g., game board) to reset
        if (restartListener != null) {
            restartListener.onRestart();
        }
    }

    // Interface to notify restart events
    public interface RestartListener {
        void onRestart();
    }

    // Set restart listener
    public void setRestartListener(RestartListener listener) {
        this.restartListener = listener;
    }
}