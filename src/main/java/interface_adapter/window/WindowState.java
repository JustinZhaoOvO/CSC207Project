package interface_adapter.window;
//CreateTime: 2024-11-27 3:38 p.m.

public class WindowState {
    private boolean gameOver;
    private boolean paused;
    private boolean switchTurn;
    private boolean restart;
    private String move;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isSwitchTurn() {
        return switchTurn;
    }

    public void setSwitchTurn(boolean switchTurn) {
        this.switchTurn = switchTurn;
    }

    public boolean isRestart() {
        return restart;
    }

    public void setRestart(boolean restart) {
        this.restart = restart;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
