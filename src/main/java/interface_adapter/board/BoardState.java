package interface_adapter.board;
//CreateTime: 2024-11-14 11:13 p.m.

import entity.Cell;

public class BoardState {
    private Boolean repaintSuccess;

    private Cell[][] cells;

    private Boolean blackTurn;

    private String selected;

    public boolean getBlackTurn() {
        return blackTurn;
    }

    public void reverseTurn() {
        this.blackTurn = !this.blackTurn;
    }

    public BoardState() {
    }

    public String getSelected() {
        return selected;
    }
    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void cleanSelected() {
        this.selected = "";
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setRepaintSuccess(final Boolean repaintSuccess) {
        this.repaintSuccess = repaintSuccess;
    }

    public Boolean getRepaintSuccess() {
        return repaintSuccess;
    }
}
