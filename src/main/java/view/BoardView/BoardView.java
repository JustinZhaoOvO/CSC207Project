package view.BoardView;
//CreateTime: 2024-11-11 10:26 a.m.

import api_adapters.ChariotAPI.ChariotBoard;
import entity.BoardConstants;
import entity.Coordinate;
import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import interface_adapter.board.move.MoveController;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.select.SelectController;
import view.BoardView.PiecesView.PiecesListener;
import view.BoardView.PiecesView.PiecesView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class BoardView extends JPanel implements PropertyChangeListener {

    private PiecesView selected;

    private List<String> validMoves;

    private PiecesView[][] board;

    private PiecesView mouseHoverOn;

    private RepaintBoardController repaintBoardController;

    private SelectController selectController;

    private MoveController moveController;

    private ChariotBoard chariotBoard;

    public BoardView() {

        this.board = new PiecesView[BoardConstants.SIZEOFABOARD][BoardConstants.SIZEOFABOARD];
        //add Pieces component to the Board component
        for (int i = 0; i < BoardConstants.SIZEOFABOARD; i++){
            for (int j = 0; j < BoardConstants.SIZEOFABOARD; j++){
                PiecesView piecesView = new PiecesView((i + j) % 2 == 0 ? BoardConstants.EVENCELLCOLOR :
                        BoardConstants.ODDCELLCOLOR, null, new Coordinate(i, j));
                piecesView.addMouseListener(new PiecesListener(this));
                this.add(piecesView);
                this.board[i][j] = piecesView;
            }
        }
    }

    public void restartTheGameWith(ChariotBoard chariotBoard) {
        this.chariotBoard = chariotBoard;
        repaintBoardController.execute(chariotBoard);
    }


    public void setRepaintBoardController(RepaintBoardController repaintBoardController) {
        this.repaintBoardController = repaintBoardController;
    }

    public void setSelectController(SelectController selectController) {
        this.selectController = selectController;
    }

    public void setMoveController(MoveController moveController) {
        this.moveController = moveController;
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
            if (newValue.getRepaintSuccess()){
                repaintBoard(newValue);
            }else {
                JOptionPane.showMessageDialog(this, "Repainting the board failed");
            }
        }else if (propertyName.equals(BoardStateConstants.SELECT)) {
            selectHelper(newValue);
        }else if (propertyName.equals(BoardStateConstants.MOVE)) {

        }
    }

    public PiecesView[][] getBoard() {
        return board;
    }

    public void selectHelper(BoardState state) {
        this.selected = state.getSelected();
        this.validMoves = state.getValidMoves();
        selected.setSelected(true);
        for (String validMove : validMoves) {
            String stringCoordinate = validMove.substring(2, 4);// a UCI move is like f5a1
            Coordinate coordinate = Coordinate.fromString(stringCoordinate);
            assert coordinate != null;
            board[coordinate.getY()][coordinate.getX()].setValidMoveToHere(true);
        }this.repaint();
    }

    public void repaintBoard(BoardState state) {

        Boolean repaintSuccess = state.getRepaintSuccess();

        if (repaintSuccess) {
            this.selected = null;
            this.validMoves = null;
            PiecesView[][] piecesViews = state.getPiecesViews();
            for (int i = 0; i < BoardConstants.SIZEOFABOARD; i++) {
                for (int j = 0; j < BoardConstants.SIZEOFABOARD; j++) {
                    PiecesView cur = board[i][j];
                    if (cur != null && cur != piecesViews[i][j]) {
                        cur.copyFrom(piecesViews[i][j]);
                    }
                }
            }this.repaint();
        }else {
            JOptionPane.showMessageDialog(this, "Repaint Failed");
        }
    }

    public void clickOn(PiecesView p) {
        if (selected != null){
            moveController.execute(this.chariotBoard, p, this.validMoves);
        }else {
            selectController.execute(this.chariotBoard, p);
        }
    }
}
