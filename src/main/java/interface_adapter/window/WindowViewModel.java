package interface_adapter.window;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class WindowViewModel extends ViewModel<WindowState> {

    public WindowViewModel(String viewName) {
        super(viewName);
    }


    // 现有的 Setter 方法（可选，如果需要在其他地方使用）
    public void setPaused(boolean paused) {
        WindowState windowState = new WindowState();
        windowState.setPaused(paused);
        this.setState(windowState);
        this.firePropertyChanged("paused");
    }

    public void setRestart(boolean restart) {
        WindowState windowState = new WindowState();
        windowState.setRestart(restart);
        this.setState(windowState);
        this.firePropertyChanged("restart");
    }

    public void setSwitchTurn(boolean switchTurn) {
        WindowState windowState = new WindowState();
        windowState.setSwitchTurn(switchTurn);
        this.setState(windowState);
        this.firePropertyChanged("switchTurn");
    }

    public void setGameOver(boolean gameOver) {
        WindowState windowState = new WindowState();
        windowState.setGameOver(gameOver);
        this.setState(windowState);
        this.firePropertyChanged("gameOver");
    }

    public void setBlackRanOutOfTime(boolean blackRanOutOfTime) {
        WindowState windowState = new WindowState();
        windowState.setBlackRanOutOfTime(blackRanOutOfTime);
        this.setState(windowState);
        this.firePropertyChanged("blackRanOutOfTime");
    }

    public void setMove(String move) {
        WindowState windowState = new WindowState();
        windowState.setMove(move);
        this.setState(windowState);
        this.firePropertyChanged("move");
    }

}