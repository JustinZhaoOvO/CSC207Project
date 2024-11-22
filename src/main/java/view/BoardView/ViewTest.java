package view.BoardView;

import api_adapters.ChariotAPI.ChariotBoard;
import entity.BoardConstants;
import view.BoardView.ColorConstants;
import entity.ImageConstants;
import interface_adapter.board.BoardViewModel;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.repaintboard.RepaintBoardPresenter;
import interface_adapter.timer.TimerController;
import use_case.board.repaintboard.RepaintBoardInteractor;
import use_case.timer.TimerInteractor;
import view.BoardView.PiecesView.PiecesView;
import view.WindowView.WindowView;
import view.timer.TimerPresenter;
import view.timer.TimerView;
import view.timer.TimerViewModel;

import javax.swing.*;
import java.awt.*;

public class ViewTest extends JFrame {
    private boolean isPaused = false; // Track pause state
    private Timer switchTurnTimer;    // Make switchTurnTimer an instance variable

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewTest::new);
    }

    public ViewTest() {

        // Set up JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Chess Game with Timer");
        this.setSize(1200, 650);
        this.setLocationRelativeTo(null);

        // Initialize components
        BoardView boardView = new BoardView();

        // Set up main window
        JPanel window = new WindowView();
        window.setLayout(new BorderLayout());
        this.setContentPane(window);
        window.setBackground(ColorConstants.LIGHTBLUE);

        // Add board to center
        window.add(boardView, BorderLayout.CENTER);

        // Add pieces to the board (assuming BoardConstants and PiecesView are properly defined)
        for (int i = 0; i < BoardConstants.SIZEOFABOARD; i++) {
            for (int j = 0; j < BoardConstants.SIZEOFABOARD; j++) {
                boardView.add(
                        new PiecesView(
                                (i + j) % 2 == 0 ? BoardConstants.EVENCELLCOLOR : BoardConstants.ODDCELLCOLOR,
                                null
                        ),
                        i,
                        j
                );
            }
        }

        // Set board layout
        boardView.setLayout(new BoardLayout());

        // Initialize repaint functionality
        BoardViewModel boardViewModel = new BoardViewModel();
        RepaintBoardPresenter repaintBoardPresenter = new RepaintBoardPresenter(boardViewModel);
        RepaintBoardInteractor repaintBoardInteractor = new RepaintBoardInteractor(repaintBoardPresenter);
        RepaintBoardController repaintBoardController = new RepaintBoardController(repaintBoardInteractor);

        // Add BoardView as listener
        boardViewModel.addPropertyChangeListener(boardView);

        // Repaint the board
        ChariotBoard chariotBoard = new ChariotBoard();
        repaintBoardController.execute(chariotBoard);

        // Set up timer components
        long totalTimePerPlayer = 1 * 60 * 1000; // 1 minute per player for testing

        TimerViewModel timerViewModel = new TimerViewModel();
        TimerPresenter timerPresenter = new TimerPresenter(timerViewModel);
        TimerInteractor timerInteractor = new TimerInteractor(totalTimePerPlayer, timerPresenter);
        TimerController timerController = new TimerController(timerInteractor);
        TimerView timerView = new TimerView();
        timerViewModel.addPropertyChangeListener(timerView);

        // Add TimerView to the right side
        window.add(timerView, BorderLayout.EAST);

        // Start the game timer
        timerController.startGame();

        // Set up the pause button listener
        timerView.addPauseActionListener(e -> {
            System.out.println("Pause/Start button clicked");
            if (!isPaused) {
                timerController.pauseGame();
                isPaused = true;
                // Pause the switchTurnTimer
                if (switchTurnTimer != null) {
                    switchTurnTimer.stop();
                }
                // Switch to start button icon
                timerView.togglePauseButtonIcon(ImageConstants.STARTBUTTON);
            } else {
                timerController.resumeGame();
                isPaused = false;
                // Resume the switchTurnTimer
                if (switchTurnTimer != null) {
                    switchTurnTimer.start();
                }
                // Switch back to pause button icon
                timerView.togglePauseButtonIcon(ImageConstants.PAUSEBUTTON);
            }
            // Update paused state in TimerView
            timerView.setPaused(isPaused);
        });

        // Initialize the switchTurnTimer
        switchTurnTimer = new Timer(5000, e -> {
            System.out.println("Switching turn");
            timerController.switchTurn();
        });
        switchTurnTimer.start();

        this.setVisible(true);
    }
}