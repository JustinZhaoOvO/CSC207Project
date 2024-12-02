package view.timer;

import entity.ImageConstants;
import interface_adapter.window.WindowState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;

public class TimerView extends JPanel implements PropertyChangeListener {
    long player1Time; // Remaining time for player 1
    long player2Time; // Remaining time for player 2
    boolean isPlayer1Turn; // Is it player 1's turn
    int timeUpPlayer; // Player whose time is up (1 or 2)
    JButton pauseButton;
    JButton startButton;
    JButton restartButton;
    JButton continueButton; // Added continueButton
    boolean isPaused;
    final long totalTime; // Total time per player (milliseconds)
    boolean gameOver;

    // Constructor, accepts total time per player as parameter
    public TimerView(long totalTimePerPlayer) {
        this.setPreferredSize(new Dimension(200, 600));
        this.player1Time = totalTimePerPlayer;
        this.player2Time = totalTimePerPlayer;
        this.isPlayer1Turn = true; // Start with player 1's turn
        this.timeUpPlayer = 0;
        this.isPaused = false;
        this.totalTime = totalTimePerPlayer;
        this.gameOver = false;

        setLayout(null); // Use absolute positioning

        // Load and scale images
        int buttonWidth = 50;
        int buttonHeight = 50;
        Image pauseImage = ImageConstants.PAUSEBUTTON.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        Image startImage = ImageConstants.STARTBUTTON.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        Image restartImage = ImageConstants.RESTARTBUTTON.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
        Image continueImage = ImageConstants.CONTINUEBUTTON.getScaledInstance(150, 50, Image.SCALE_SMOOTH); // Continue button image scaled to 150x50

        // Initialize buttons
        pauseButton = new JButton(new ImageIcon(pauseImage));
        styleButton(pauseButton);
        add(pauseButton);

        startButton = new JButton(new ImageIcon(startImage));
        styleButton(startButton);
        add(startButton);

        restartButton = new JButton(new ImageIcon(restartImage));
        styleButton(restartButton);
        add(restartButton);

        continueButton = new JButton(new ImageIcon(continueImage));
        styleButton(continueButton);
        add(continueButton);

        // Initially, continue button is visible; other buttons adjusted accordingly
        continueButton.setVisible(true);
        startButton.setVisible(false);
        pauseButton.setVisible(false);

        // Set button bounds using absolute values
        // Adjusted positions to ensure buttons are within the panel
        // Panel width is 200 pixels

        // Pause button at (25, 550)
        pauseButton.setBounds(25, 550, 50, 50);
        // Start button at (125, 550)
        startButton.setBounds(125, 550, 50, 50);
        // Continue button centered at (25, 550), size 150x50
        continueButton.setBounds(25, 550, 150, 50);
        // Restart button at (75, 490)
        restartButton.setBounds(75, 490, 50, 50);
    }

    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setFocusable(false);
        button.setRolloverEnabled(false);
        // Ensure pressed and selected icons are the same as default icon
        button.setPressedIcon(button.getIcon());
        button.setSelectedIcon(button.getIcon());
    }

    // Methods to add action listeners
    public void addPauseActionListener(ActionListener listener) {
        pauseButton.addActionListener(listener);
    }

    public void addStartActionListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addRestartActionListener(ActionListener listener) {
        restartButton.addActionListener(listener);
    }

    public void addContinueActionListener(ActionListener listener) {
        continueButton.addActionListener(listener);
    }

    // Methods to show/hide buttons
    public void showPauseButton() {
        pauseButton.setVisible(true);
        startButton.setVisible(false);
        continueButton.setVisible(false);
        pauseButton.setEnabled(true);
        startButton.setEnabled(false);
        continueButton.setEnabled(false);
    }

    public void showStartButton() {
        pauseButton.setVisible(false);
        startButton.setVisible(true);
        continueButton.setVisible(false);
        pauseButton.setEnabled(false);
        startButton.setEnabled(true);
        continueButton.setEnabled(false);
    }

    public void showContinueButton() {
        pauseButton.setVisible(false);
        startButton.setVisible(false);
        continueButton.setVisible(true);
        pauseButton.setEnabled(false);
        startButton.setEnabled(false);
        continueButton.setEnabled(true);
    }

    // Disable pause, start, and continue buttons
    public void disablePauseAndStartButtons() {
        pauseButton.setEnabled(false);
        startButton.setEnabled(false);
        continueButton.setEnabled(false);
    }

    // Enable pause, start, and continue buttons
    public void enablePauseAndStartButtons() {
        pauseButton.setEnabled(true);
        startButton.setEnabled(true);
        continueButton.setEnabled(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "player1Time" -> player1Time = (long) evt.getNewValue();
            case "player2Time" -> player2Time = (long) evt.getNewValue();
            case "isPlayer1Turn" -> isPlayer1Turn = (boolean) evt.getNewValue();
            case "timeUp" -> {
                timeUpPlayer = (int) evt.getNewValue();
                gameOver = true;
                disablePauseAndStartButtons();
            }
            case "gameOver" -> {
                gameOver = ((WindowState) evt.getNewValue()).isGameOver();
                if (gameOver) {
                    disablePauseAndStartButtons();
                }
            }
            case "paused" -> {
                isPaused = ((WindowState) evt.getNewValue()).isPaused();
                setPaused(isPaused);
            }
            case "restart" -> {
                boolean isRestart = ((WindowState) evt.getNewValue()).isRestart();
                if (isRestart) {
                    // Reset the state of the timer view
                    resetView();
                    // Show Continue button after restart
                    showContinueButton();
                }
            }
        }

        // Show Continue button if timer is at maximum time and game is not over
        if (player1Time == totalTime && player2Time == totalTime && !gameOver) {
            showContinueButton();
        }

        repaint(); // Repaint panel when properties change
    }

    private void resetView() {
        this.player1Time = totalTime;
        this.player2Time = totalTime;
        this.isPlayer1Turn = true;
        this.timeUpPlayer = 0;
        this.isPaused = false;
        this.gameOver = false;
        // Show Continue button after reset
        showContinueButton();
        enablePauseAndStartButtons();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Convert Graphics to Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        // Get panel dimensions
        int width = getWidth();
        int height = getHeight();

        // Split panel vertically into two sections
        int blockHeight = height / 2;

        // Calculate time ratios
        float ratio1 = (float) player1Time / totalTime;
        ratio1 = Math.max(0, Math.min(1, ratio1)); // Clamp between 0 and 1

        float ratio2 = (float) player2Time / totalTime;
        ratio2 = Math.max(0, Math.min(1, ratio2)); // Clamp between 0 and 1

        // Draw black player's block (upper half)
        int player2RemainingHeight = (int) (blockHeight * ratio2);
        int player2Y = 0;

        // Draw background
        g2.setColor(Color.GRAY);
        g2.fillRect(0, player2Y, width, blockHeight);

        // Draw black player's remaining time
        g2.setColor(Color.BLACK);
        g2.fillRect(0, player2Y + (blockHeight - player2RemainingHeight), width, player2RemainingHeight);

        // Draw yellow border if it's black player's turn
        if (!isPlayer1Turn) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(0, player2Y, width - 1, blockHeight - 1);
        }

        // Draw black player's info
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        String player2Text = "Black";
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(player2Text);
        g2.drawString(player2Text, (width - textWidth) / 2, player2Y + 20);

        // Display black player's remaining time
        String timeText2 = formatTime(player2Time);
        textWidth = fm.stringWidth(timeText2);
        g2.drawString(timeText2, (width - textWidth) / 2, player2Y + blockHeight - 10);

        // Draw white player's block (lower half)
        int player1RemainingHeight = (int) (blockHeight * ratio1);
        int player1Y = blockHeight;

        // Draw background
        g2.setColor(Color.GRAY);
        g2.fillRect(0, player1Y, width, blockHeight);

        // Draw white player's remaining time
        g2.setColor(Color.WHITE);
        g2.fillRect(0, player1Y + (blockHeight - player1RemainingHeight), width, player1RemainingHeight);

        // Draw yellow border if it's white player's turn
        if (isPlayer1Turn) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(0, player1Y, width - 1, blockHeight - 1);
        }

        // Draw white player's info
        g2.setColor(Color.BLUE);
        String player1Text = "White";
        textWidth = fm.stringWidth(player1Text);
        g2.drawString(player1Text, (width - textWidth) / 2, player1Y + 20);

        // Display white player's remaining time
        String timeText1 = formatTime(player1Time);
        textWidth = fm.stringWidth(timeText1);
        g2.drawString(timeText1, (width - textWidth) / 2, player1Y + blockHeight - 10);

        // If a player's time is up or game is over, display message
        if (gameOver) {
            String message = timeUpPlayer != 0
                    ? "Player " + (timeUpPlayer == 1 ? "White" : "Black") + " time is up!"
                    : "Game Over!";
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.setColor(Color.RED);
            textWidth = g2.getFontMetrics().stringWidth(message);
            g2.drawString(message, (width - textWidth) / 2, height / 2);
        }
    }

    // Convert milliseconds to mm:ss format
    private String formatTime(long timeMillis) {
        long totalSeconds = timeMillis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return minutes + ":" + String.format("%02d", seconds);
    }

    // Update pause state
    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            showStartButton();
        } else {
            showPauseButton();
        }
    }
}