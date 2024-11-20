package interface_adapter.board;
//CreateTime: 2024-11-14 11:13 p.m.

import view.BoardView.PiecesView.PiecesView;

import java.util.List;

public class BoardState {
    private Boolean repaintSuccess;

    private PiecesView[][] piecesViews;

    private PiecesView selected;

    private List<String> validMoves;


    public BoardState() {
    }


    public PiecesView getSelected() {
        return selected;
    }

    public void setSelected(PiecesView selected) {
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
}
