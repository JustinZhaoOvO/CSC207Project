package interface_adapter.board;
//CreateTime: 2024-11-14 11:13 p.m.

import chariot.util.Board;
import entity.Coordinate;
import view.BoardView.PiecesView.PiecesView;

import java.util.List;

public class BoardState {
    private Boolean repaintSuccess;

    private String msg;

    private PiecesView[][] piecesViews;

    private Coordinate selected;

    private List<String> validMoves;

    private boolean blackTurn;



    public BoardState() {
    }

    public boolean isBlackTurn() {
        return blackTurn;
    }

    public void setBlackTurn(boolean blackTurn) {
        this.blackTurn = blackTurn;
    }

    public Coordinate getSelected() {
        return selected;
    }

    public void setSelected(Coordinate selected) {
        this.selected = selected;
    }

    public PiecesView[][] getPiecesViews() {
        return this.piecesViews;
    }

    public void setPiecesViews(PiecesView[][] piecesViews) {
        this.piecesViews = piecesViews;
    }

    public void setRepaintSuccess(final Boolean repaintSuccess) {
        this.repaintSuccess = repaintSuccess;
    }

    public Boolean getRepaintSuccess() {
        return repaintSuccess;
    }

    public List<String> getValidMoves() {
        return validMoves;
    }

    public void setValidMoves(List<String> validMoves) {
        this.validMoves = validMoves;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
