package interface_adapter.window;
//CreateTime: 2024-11-27 3:38 p.m.

public class WindowState {
    private boolean gameOver;   //TODO: (Timer) stop timer
    private boolean paused;     //TODO: (Board)ban mouse event
    private boolean switchTurn; //TODO: (Timer)reverse timer, (Record)record a step
    private boolean restart;  //TODO: (Record)clear records, (Board)reset Board, (Timer) reset Timer
    private boolean blackRanOutOfTime; //TODO: (Board) end the game
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

    public boolean isBlackRanOutOfTime() {
        return blackRanOutOfTime;
    }

    public void setBlackRanOutOfTime(boolean blackRanOutOfTime) {
        this.blackRanOutOfTime = blackRanOutOfTime;
    }
}
