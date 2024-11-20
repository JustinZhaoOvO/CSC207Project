package view.BoardView;
//CreateTime: 2024-11-11 10:26 a.m.

import entity.BoardConstants;
import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import view.BoardView.PiecesView.PiecesListener;
import view.BoardView.PiecesView.PiecesView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BoardView extends JPanel implements PropertyChangeListener {

    private PiecesView selected;

    private PiecesView[][] board;

    private PiecesView mouseHoverOn;

    public BoardView() {
        this.board = new PiecesView[BoardConstants.SIZEOFABOARD][BoardConstants.SIZEOFABOARD];
        //add Pieces component to the Board component
        for (int i = 0; i < BoardConstants.SIZEOFABOARD; i++){
            for (int j = 0; j < BoardConstants.SIZEOFABOARD; j++){
                PiecesView piecesView = new PiecesView((i + j) % 2 == 0 ? BoardConstants.EVENCELLCOLOR :
                        BoardConstants.ODDCELLCOLOR, null);
                piecesView.addMouseListener(new PiecesListener(this));
                this.add(piecesView);
                this.board[i][j] = piecesView;
            }
        }
    }

    public void mouseEnterPiece(PiecesView p){

        if (this.mouseHoverOn != null){
            this.mouseHoverOn.setHovered(false);
        }if (p != null){
            p.setHovered(true);
        }
        this.mouseHoverOn = p;
        this.repaint();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        BoardState newValue = (BoardState) evt.getNewValue();
        if (propertyName.equals(BoardStateConstants.REPAINT)) {
            repaintBoard(newValue);
        }
    }

    public PiecesView[][] getBoard() {
        return board;
    }

    public void repaintBoard(BoardState state){

        Boolean repaintSuccess = state.getRepaintSuccess();

        if (repaintSuccess) {
            PiecesView[][] piecesViews = state.getPiecesViews();
            for (int i = 0; i < BoardConstants.SIZEOFABOARD; i++) {
                for (int j = 0; j < BoardConstants.SIZEOFABOARD; j++) {
                    PiecesView cur = board[i][j];
                    if (cur != null && cur != piecesViews[i][j]) {
                        cur.copyFrom(piecesViews[i][j]);
                    }
                }
            }this.repaint();
        }
    }
}
