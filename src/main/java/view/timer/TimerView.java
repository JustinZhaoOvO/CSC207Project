package view.timer;

import entity.ImageConstants;
import view.BoardView.ColorConstants;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;

public class TimerView extends JPanel implements PropertyChangeListener {
    private long player1Time; // Player 1 remaining time
    private long player2Time; // Player 2 remaining time
    private boolean isPlayer1Turn; // Indicates if it's Player 1's turn
    private int timeUpPlayer; // Indicates which player time is up (1 or 2)
    private JButton pauseButton;
    private JButton startButton;
    private boolean isPaused;

    public TimerView() {
        this.setPreferredSize(new Dimension(200, 600)); // Set panel size to 200x600
        this.player1Time = 0;
        this.player2Time = 0;
        this.isPlayer1Turn = true;
        this.timeUpPlayer = 0;
        this.isPaused = false;

        setLayout(null); // Using absolute positioning

        // Load and scale images
        Image pauseImage = ImageConstants.PAUSEBUTTON.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image startImage = ImageConstants.STARTBUTTON.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        // Initialize pause button
        pauseButton = new JButton(new ImageIcon(pauseImage));
        pauseButton.setBounds(10, 550, 50, 50);
        styleButton(pauseButton);
        add(pauseButton);

        // Initialize start button
        startButton = new JButton(new ImageIcon(startImage));
        startButton.setBounds(70, 550, 50, 50);
        styleButton(startButton);
        add(startButton);

        // Initially, start button is not visible
        startButton.setVisible(false);

        // Debug: Verify images are loaded
        System.out.println("Pause Button Image: " + (ImageConstants.PAUSEBUTTON != null));
        System.out.println("Start Button Image: " + (ImageConstants.STARTBUTTON != null));
    }

    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setFocusable(false);
        button.setRolloverEnabled(false);
        // Ensure the pressed and selected icons are same as default icon
        button.setPressedIcon(button.getIcon());
        button.setSelectedIcon(button.getIcon());
    }

    // Provide methods to add listeners
    public void addPauseActionListener(ActionListener listener) {
        pauseButton.addActionListener(listener);
    }

    public void addStartActionListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    // Methods to show/hide buttons
    public void showPauseButton() {
        pauseButton.setVisible(true);
        startButton.setVisible(false);
    }

    public void showStartButton() {
        pauseButton.setVisible(false);
        startButton.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "player1Time" -> player1Time = (long) evt.getNewValue();
            case "player2Time" -> player2Time = (long) evt.getNewValue();
            case "isPlayer1Turn" -> isPlayer1Turn = (boolean) evt.getNewValue();
            case "timeUp" -> timeUpPlayer = (int) evt.getNewValue();
        }
        repaint(); // Repaint the panel when properties change
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get panel dimensions
        int width = getWidth();
        int height = getHeight();

        // Set background color based on current turn
        if (isPlayer1Turn) {
            g.setColor(ColorConstants.BLUEWHITE); // Player 1 color (blue)
        } else {
            g.setColor(ColorConstants.RED); // Player 2 color (red)
        }
        g.fillRect(0, 0, width, height);

        // Calculate time ratio
        float ratio;
        Color currentColor;
        long totalTime = 1 * 60 * 1000; // 1 minute total time

        if (isPlayer1Turn) {
            ratio = (float) player1Time / totalTime;
            ratio = Math.max(0, Math.min(1, ratio)); // Clamp between 0 and 1
            currentColor = ColorConstants.RED; // Player 1's remaining time color
        } else {
            ratio = (float) player2Time / totalTime;
            ratio = Math.max(0, Math.min(1, ratio));
            currentColor = ColorConstants.BLUEWHITE; // Player 2's remaining time color
        }

        // Calculate remaining time height
        int remainingHeight = (int) (height * ratio);

        // Draw remaining time area
        g.setColor(currentColor);
        g.fillRect(0, height - remainingHeight, width, remainingHeight); // Draw from bottom up

        // Draw "Time Remaining" text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String timeRemainingText = "Time Remaining";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(timeRemainingText);
        g.drawString(timeRemainingText, (width - textWidth) / 2, height - 40); // Slight margin above

        // Display remaining time at the bottom
        String timeText = formatTime(isPlayer1Turn ? player1Time : player2Time);
        textWidth = fm.stringWidth(timeText);
        g.drawString(timeText, (width - textWidth) / 2, height - 20);

        // Display current turn information at the top
        String turnText = isPlayer1Turn ? "Player 1's Turn" : "Player 2's Turn";
        g.drawString(turnText, 10, 40);

        // Display time-up message if applicable
        if (timeUpPlayer != 0) {
            String message = "Player " + (timeUpPlayer == 1 ? "1" : "2") + " time is up!";
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.RED);
            textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (width - textWidth) / 2, height / 2);
        }
    }

    // Convert milliseconds to mm:ss format
    private String formatTime(long timeMillis) {
        long totalSeconds = timeMillis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return minutes + ":" + String.format("%02d", seconds);
    }

    // Update paused state
    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            // Switch to start button
            showStartButton();
        } else {
            // Switch to pause button
            showPauseButton();
        }
    }
}