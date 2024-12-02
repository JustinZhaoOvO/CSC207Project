package interface_adapter.controller;

import interface_adapter.timer.TimerController;
import interface_adapter.window.WindowState;
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

        // Add TimerView as a listener to WindowViewModel
        windowViewModel.addPropertyChangeListener(timerView);

        // Show the Continue button at the start
        timerView.showContinueButton();
    }


    private TimerController initializeTimer() {
        TimerViewModel timerViewModel = new TimerViewModel();
        TimerPresenter timerPresenter = new TimerPresenter(timerViewModel);
        TimerInteractor timerInteractor = new TimerInteractor(totalTimePerPlayer, timerPresenter);
        TimerController timerController = new TimerController(timerInteractor);

        timerViewModel.addPropertyChangeListener(timerView);

        timerViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("timeUp".equals(evt.getPropertyName())) {
                    int timeUpPlayer = (int) evt.getNewValue();
                    System.out.println("TimerManager detected timeUp for player " + timeUpPlayer);
                    windowViewModel.setGameOver(true);
                    windowViewModel.setBlackRanOutOfTime(timeUpPlayer == 2);
                }
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

        // Continue button
        timerView.addContinueActionListener(e -> {
            System.out.println("Continue button clicked");
            timerController.startGame();
            isPaused = false;
            timerView.setPaused(isPaused);
            timerView.showPauseButton();
            // Update WindowState
            windowViewModel.setPaused(false);
        });
    }

    private void setupWindowViewModelListener() {
        windowViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("switchTurn".equals(evt.getPropertyName())) {
                    boolean switchTurn = ((WindowState) evt.getNewValue()).isSwitchTurn();
                    if (switchTurn) {
                        System.out.println("TimerManager detected switchTurn");
                        timerController.switchTurn();
                        // Reset switchTurn to false
                        windowViewModel.setSwitchTurn(false);
                    }
                }

                if ("gameOver".equals(evt.getPropertyName())) {
                    boolean gameOver = ((WindowState) evt.getNewValue()).isGameOver();
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
        windowViewModel.setRestart(false); // Reset restart flag
    }

    // Interface for notifying restart events
    public interface RestartListener {
        void onRestart();
    }

    // Set restart listener
    public void setRestartListener(RestartListener listener) {
        // Not used in current setup
    }
}