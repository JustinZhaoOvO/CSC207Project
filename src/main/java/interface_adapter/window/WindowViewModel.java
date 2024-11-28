package interface_adapter.window;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WindowViewModel {

    private final PropertyChangeSupport support;
    private final String viewName;
    private WindowState windowState;

    public WindowViewModel(String viewName) {
        this.viewName = viewName;
        this.support = new PropertyChangeSupport(this);
        this.windowState = new WindowState();
    }

    // 添加监听器
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    // 移除监听器
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    // 设置整个状态
    public void setState(WindowState newState) {
        this.windowState = newState;
    }

    // 为特定属性触发属性更改事件
    public void firePropertyChanged(String propertyName) {
        Object newValue = null;

        switch (propertyName) {
            case "paused":
                newValue = windowState.isPaused();
                break;
            case "restart":
                newValue = windowState.isRestart();
                break;
            case "switchTurn":
                newValue = windowState.isSwitchTurn();
                break;
            case "gameOver":
                newValue = windowState.isGameOver();
                break;
            case "blackRanOutOfTime":
                newValue = windowState.isBlackRanOutOfTime();
                break;
            case "move":
                newValue = windowState.getMove();
                break;
            default:
                // 无效的属性名称
                return;
        }

        support.firePropertyChange(propertyName, null, newValue);
    }

    // 现有的 Setter 方法（可选，如果需要在其他地方使用）
    public void setPaused(boolean paused) {
        boolean oldPaused = windowState.isPaused();
        windowState.setPaused(paused);
        support.firePropertyChange("paused", oldPaused, paused);
    }

    public void setRestart(boolean restart) {
        boolean oldRestart = windowState.isRestart();
        windowState.setRestart(restart);
        support.firePropertyChange("restart", oldRestart, restart);
    }

    public void setSwitchTurn(boolean switchTurn) {
        boolean oldSwitchTurn = windowState.isSwitchTurn();
        windowState.setSwitchTurn(switchTurn);
        support.firePropertyChange("switchTurn", oldSwitchTurn, switchTurn);
    }

    public void setGameOver(boolean gameOver) {
        boolean oldGameOver = windowState.isGameOver();
        windowState.setGameOver(gameOver);
        support.firePropertyChange("gameOver", oldGameOver, gameOver);
    }

    public void setBlackRanOutOfTime(boolean blackRanOutOfTime) {
        boolean oldValue = windowState.isBlackRanOutOfTime();
        windowState.setBlackRanOutOfTime(blackRanOutOfTime);
        support.firePropertyChange("blackRanOutOfTime", oldValue, blackRanOutOfTime);
    }

    public void setMove(String move) {
        String oldMove = windowState.getMove();
        windowState.setMove(move);
        support.firePropertyChange("move", oldMove, move);
    }

    // Getter 方法
    public WindowState getWindowState() {
        return windowState;
    }

    public String getViewName() {
        return viewName;
    }
}