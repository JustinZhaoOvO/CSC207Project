package view.timer;

import entity.ImageConstants;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;

public class TimerView extends JPanel implements PropertyChangeListener {
    long player1Time; // Player 1 剩余时间
    long player2Time; // Player 2 剩余时间
    boolean isPlayer1Turn; // 是否为 Player 1 的回合
    int timeUpPlayer; // 时间到的玩家编号（1 或 2）
    JButton pauseButton;
    JButton startButton;
    JButton restartButton;
    boolean isPaused;
    final long totalTime; // 每个玩家的总时间（毫秒）
    boolean gameOver;

    // 构造函数，接受每个玩家的总时间作为参数
    public TimerView(long totalTimePerPlayer) {
        this.setPreferredSize(new Dimension(200, 600));
        this.player1Time = totalTimePerPlayer;
        this.player2Time = totalTimePerPlayer;
        this.isPlayer1Turn = true;
        this.timeUpPlayer = 0;
        this.isPaused = false;
        this.totalTime = totalTimePerPlayer;
        this.gameOver = false;

        setLayout(null); // 使用绝对定位

        // 加载并缩放图像
        Image pauseImage = ImageConstants.PAUSEBUTTON.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image startImage = ImageConstants.STARTBUTTON.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        Image restartImage = ImageConstants.RESTARTBUTTON.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

        // 初始化暂停按钮
        pauseButton = new JButton(new ImageIcon(pauseImage));
        pauseButton.setBounds(10, 550, 50, 50);
        styleButton(pauseButton);
        add(pauseButton);

        // 初始化开始按钮
        startButton = new JButton(new ImageIcon(startImage));
        startButton.setBounds(70, 550, 50, 50);
        styleButton(startButton);
        add(startButton);

        // 初始化重启按钮
        restartButton = new JButton(new ImageIcon(restartImage));
        restartButton.setBounds(130, 550, 50, 50);
        styleButton(restartButton);
        add(restartButton);

        // 初始时，开始按钮不可见
        startButton.setVisible(false);

        // 调试：验证图像是否加载
        System.out.println("Pause Button Image: " + (ImageConstants.PAUSEBUTTON != null));
        System.out.println("Start Button Image: " + (ImageConstants.STARTBUTTON != null));
        System.out.println("Restart Button Image: " + (ImageConstants.RESTARTBUTTON != null));
    }

    private void styleButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setFocusable(false);
        button.setRolloverEnabled(false);
        // 确保按下和选中图标与默认图标相同
        button.setPressedIcon(button.getIcon());
        button.setSelectedIcon(button.getIcon());
    }

    // 添加监听器的方法
    public void addPauseActionListener(ActionListener listener) {
        pauseButton.addActionListener(listener);
    }

    public void addStartActionListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void addRestartActionListener(ActionListener listener) {
        restartButton.addActionListener(listener);
    }

    // 显示/隐藏按钮的方法
    public void showPauseButton() {
        pauseButton.setVisible(true);
        startButton.setVisible(false);
        pauseButton.setEnabled(true);
        startButton.setEnabled(false);
    }

    public void showStartButton() {
        pauseButton.setVisible(false);
        startButton.setVisible(true);
        pauseButton.setEnabled(false);
        startButton.setEnabled(true);
    }

    // 新增方法：禁用暂停和开始按钮
    public void disablePauseAndStartButtons() {
        pauseButton.setEnabled(false);
        startButton.setEnabled(false);
    }

    // 新增方法：启用暂停和开始按钮
    public void enablePauseAndStartButtons() {
        pauseButton.setEnabled(true);
        startButton.setEnabled(true);
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
                gameOver = (boolean) evt.getNewValue();
                if (gameOver) {
                    disablePauseAndStartButtons();
                }
            }
            case "paused" -> {
                isPaused = (boolean) evt.getNewValue();
                setPaused(isPaused);
            }
            case "restart" -> {
                boolean isRestart = (boolean) evt.getNewValue();
                if (isRestart) {
                    // 重置计时器视图的状态
                    resetView();
                }
            }
        }
        repaint(); // 当属性变化时重绘面板
    }

    private void resetView() {
        this.player1Time = totalTime;
        this.player2Time = totalTime;
        this.isPlayer1Turn = true;
        this.timeUpPlayer = 0;
        this.isPaused = false;
        this.gameOver = false;
        showPauseButton();
        enablePauseAndStartButtons();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 将 Graphics 转换为 Graphics2D
        Graphics2D g2 = (Graphics2D) g;

        // 获取面板尺寸
        int width = getWidth();
        int height = getHeight();

        // 将面板垂直分为上下两块
        int blockHeight = height / 2;

        // 计算时间比例
        float ratio1 = (float) player1Time / totalTime;
        ratio1 = Math.max(0, Math.min(1, ratio1)); // 限制在0到1之间

        float ratio2 = (float) player2Time / totalTime;
        ratio2 = Math.max(0, Math.min(1, ratio2)); // 限制在0到1之间

        // 绘制 Player 1 的块（上半部分）
        int player1RemainingHeight = (int) (blockHeight * ratio1);
        int player1Y = 0;

        // 绘制背景为白色
        g2.setColor(Color.WHITE);
        g2.fillRect(0, player1Y, width, blockHeight);

        // 绘制 Player 1 剩余时间的部分
        g2.setColor(Color.CYAN); // 使用 CYAN 颜色
        g2.fillRect(0, player1Y + (blockHeight - player1RemainingHeight), width, player1RemainingHeight);

        // 如果是 Player 1 的回合，绘制黄色边框
        if (isPlayer1Turn) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(0, player1Y, width - 1, blockHeight - 1);
        }

        // 绘制 Player 1 的信息
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 16));
        String player1Text = "White";
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(player1Text);
        g2.drawString(player1Text, (width - textWidth) / 2, player1Y + 20);

        // 显示 Player 1 剩余时间
        String timeText1 = formatTime(player1Time);
        textWidth = fm.stringWidth(timeText1);
        g2.drawString(timeText1, (width - textWidth) / 2, player1Y + blockHeight - 10);

        // 绘制 Player 2 的块（下半部分）
        int player2RemainingHeight = (int) (blockHeight * ratio2);
        int player2Y = blockHeight;

        // 绘制背景为白色
        g2.setColor(Color.WHITE);
        g2.fillRect(0, player2Y, width, blockHeight);

        // 绘制 Player 2 剩余时间的部分
        g2.setColor(Color.RED); // 使用 RED 颜色
        g2.fillRect(0, player2Y + (blockHeight - player2RemainingHeight), width, player2RemainingHeight);

        // 如果是 Player 2 的回合，绘制黄色边框
        if (!isPlayer1Turn) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(5));
            g2.drawRect(0, player2Y, width - 1, blockHeight - 1);
        }

        // 绘制 Player 2 的信息
        g2.setColor(Color.BLACK);
        String player2Text = "Black";
        textWidth = fm.stringWidth(player2Text);
        g2.drawString(player2Text, (width - textWidth) / 2, player2Y + 20);

        // 显示 Player 2 剩余时间
        String timeText2 = formatTime(player2Time);
        textWidth = fm.stringWidth(timeText2);
        g2.drawString(timeText2, (width - textWidth) / 2, player2Y + blockHeight - 10);

        // 如果有玩家时间到或游戏结束，显示提示信息
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

    // 将毫秒转换为 mm:ss 格式
    private String formatTime(long timeMillis) {
        long totalSeconds = timeMillis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return minutes + ":" + String.format("%02d", seconds);
    }

    // 更新暂停状态
    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            showStartButton();
        } else {
            showPauseButton();
        }
    }
}