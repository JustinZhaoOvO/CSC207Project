package use_case.board.move;
//CreateTime: 2024-11-20 3:15 p.m.

import entity.ChariotBoard;
import entity.Coordinate;

public class MoveOutputData {

    private boolean repaint;
    private boolean select;
    private boolean promotion;
    private boolean gameOver;
    private final ChariotBoard board;
    private final Coordinate coordinate;
    private String msg;

    public MoveOutputData(ChariotBoard board, Coordinate coordinate) {
        this.board = board;
        this.coordinate = coordinate;
    }

    public boolean isRepaint() {
        return repaint;
    }

    public void setRepaint(boolean repaint) {
        this.repaint = repaint;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

    public ChariotBoard board() {
        return board;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
