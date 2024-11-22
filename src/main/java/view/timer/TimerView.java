package view.timer;

import entity.ImageConstants;
import view.BoardView.ColorConstants;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;

public class TimerView extends JPanel implements PropertyChangeListener {
    private long player1Time;
    private long player2Time;
    private boolean isPlayer1Turn;
    private int timeUpPlayer;
    private JButton pauseButton;
    private boolean isPaused;

    public TimerView() {
        this.setPreferredSize(new Dimension(200, 600));
        this.player1Time = 0;
        this.player2Time = 0;
        this.isPlayer1Turn = true;
        this.timeUpPlayer = 0;
        this.isPaused = false;

        setLayout(null);

        // Initialize pause/start button
        pauseButton = new JButton();
        pauseButton.setIcon(new ImageIcon(ImageConstants.PAUSEBUTTON));
        pauseButton.setBounds(10, 550, 50, 50);
        pauseButton.setBorderPainted(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setFocusPainted(false);
        pauseButton.setOpaque(false);
        add(pauseButton);
    }

    public void addPauseActionListener(ActionListener listener) {
        pauseButton.addActionListener(listener);
    }

    public void togglePauseButtonIcon(Image newIcon) {
        pauseButton.setIcon(new ImageIcon(newIcon));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "player1Time" -> player1Time = (long) evt.getNewValue();
            case "player2Time" -> player2Time = (long) evt.getNewValue();
            case "isPlayer1Turn" -> isPlayer1Turn = (boolean) evt.getNewValue();
            case "timeUp" -> timeUpPlayer = (int) evt.getNewValue();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get panel dimensions
        int width = getWidth();
        int height = getHeight();

        // Set background color based on current turn
        if (isPlayer1Turn) {
            g.setColor(ColorConstants.BLUEWHITE); // Use blue color
        } else {
            g.setColor(ColorConstants.RED); // Use red color
        }
        g.fillRect(0, 0, width, height);

        // Calculate time ratio
        float ratio;
        Color currentColor;
        long totalTime = 1 * 60 * 1000; // 1 minute total time

        if (isPlayer1Turn) {
            ratio = (float) player1Time / totalTime;
            ratio = Math.max(0, Math.min(1, ratio));
            currentColor = ColorConstants.RED; // Player 1 color
        } else {
            ratio = (float) player2Time / totalTime;
            ratio = Math.max(0, Math.min(1, ratio));
            currentColor = ColorConstants.BLUEWHITE; // Player 2 color
        }

        // Calculate remaining time height
        int remainingHeight = (int) (height * ratio);

        // Draw remaining time area
        g.setColor(currentColor);
        g.fillRect(0, height - remainingHeight, width, remainingHeight);

        // Draw "Time Remaining" text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        String timeRemainingText = "Time Remaining";
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(timeRemainingText);
        g.drawString(timeRemainingText, (width - textWidth) / 2, height - 40);

        // Display remaining time at the bottom
        String timeText = formatTime(isPlayer1Turn ? player1Time : player2Time);
        textWidth = fm.stringWidth(timeText);
        g.drawString(timeText, (width - textWidth) / 2, height - 20);

        // Display current turn information at the top
        String turnText = isPlayer1Turn ? "Player 1's Turn" : "Player 2's Turn";
        g.drawString(turnText, 10, 40);

        // Display time-up message if applicable
        if (timeUpPlayer != 0) {
            String message = "Player " + timeUpPlayer + " time is up!";
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.setColor(Color.RED);
            textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (width - textWidth) / 2, height / 2);
        }
    }

    private String formatTime(long timeMillis) {
        long totalSeconds = timeMillis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return minutes + ":" + String.format("%02d", seconds);
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            togglePauseButtonIcon(ImageConstants.STARTBUTTON);
        } else {
            togglePauseButtonIcon(ImageConstants.PAUSEBUTTON);
        }
    }
}