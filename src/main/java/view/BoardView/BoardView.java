package view.BoardView;
//CreateTime: 2024-11-11 10:26 a.m.

import api_adapters.ChariotAPI.ChariotBoard;
import chariot.util.Board;
import entity.BoardConstants;
import entity.Coordinate;
import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import interface_adapter.board.move.MoveController;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.select.SelectController;
import view.BoardView.PiecesView.PiecesListener;
import view.BoardView.PiecesView.PiecesView;
import view.BoardView.PromotionView.PromotionLayout;
import view.BoardView.PromotionView.PromotionView;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class BoardView extends JPanel implements PropertyChangeListener {

    private final String viewName = "chessboard";

    private PiecesView selected;

    private List<String> validMoves;

    private final PiecesView[][] board;

    private PiecesView mouseHoverOn;

    private RepaintBoardController repaintBoardController;

    private SelectController selectController;

    private MoveController moveController;

    private ChariotBoard chariotBoard;

    private PiecesView lastClick;

    private boolean MouseEventbanned;

    private final PromotionView blackPromotion;

    private final PromotionView whitePromotion;

    public BoardView() {

        //add Promotion Components
        this.blackPromotion = new PromotionView(true, this);
        this.whitePromotion = new PromotionView(false, this);
        this.blackPromotion.setLayout(new PromotionLayout());
        this.whitePromotion.setLayout(new PromotionLayout());
        this.add(this.blackPromotion);
        this.add(this.whitePromotion);
        this.blackPromotion.setVisible(false);
        this.whitePromotion.setVisible(false);

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

    public PromotionView getBlackPromotion() {
        return blackPromotion;
    }

    public PromotionView getWhitePromotion() {
        return whitePromotion;
    }

    public void restartTheGameWith(ChariotBoard chariotBoard) {
        this.chariotBoard = chariotBoard;
        this.selected = null;
        this.validMoves = null;
        this.MouseEventbanned = false;

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
        if (MouseEventbanned) return;
        if (this.mouseHoverOn != null){
            this.mouseHoverOn.setHovered(false);
        }if (p != null){
            p.setHovered(true);
        }
        this.mouseHoverOn = p;
        this.repaint();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        BoardState newValue = (BoardState) evt.getNewValue();
        switch (propertyName) {
            case BoardStateConstants.REPAINT -> {
                if (newValue.getRepaintSuccess()) {
                    repaintBoard(newValue);
                } else {
                    JOptionPane.showMessageDialog(this, "Repainting the board failed");
                }
            }
            case BoardStateConstants.SELECT -> selectHelper(newValue);
            case BoardStateConstants.PROMOTION -> setPromotionComponentVisible(newValue.isBlackTurn());
            case BoardStateConstants.GAMEOVER -> gameOver(newValue.isBlackTurn(), newValue.getGameState());
        }
    }

    public PiecesView[][] getBoard() {
        return board;
    }

    private void selectHelper(BoardState state) {
        Coordinate coor = state.getSelected();
        this.selected = board[coor.y()][coor.x()];
        this.validMoves = state.getValidMoves();
        selected.setSelected(true);
        for (String validMove : validMoves) {
            String stringCoordinate = validMove.substring(2, 4);// a UCI move is like f5a1
            Coordinate coordinate = Coordinate.fromString(stringCoordinate);
            assert coordinate != null;
            board[coordinate.y()][coordinate.x()].setValidMoveToHere(true);
        }this.repaint();
    }

    private void repaintBoard(BoardState state) {

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
        }
    }

    private void setPromotionComponentVisible(boolean isBlack){
        MouseEventbanned = true;
        if (isBlack){
            this.blackPromotion.setVisible(true);
        }else {
            this.whitePromotion.setVisible(true);
        }
    }

    public void promoteTo(String type) {
        MouseEventbanned = false;
        this.blackPromotion.setVisible(false);
        this.whitePromotion.setVisible(false);
        String move = selected.getCoordinate().toString() + lastClick.getCoordinate().toString() + type;
        ArrayList<String> newValidMove = new ArrayList<>();
        newValidMove.add(move);
        moveController.execute(this.chariotBoard, lastClick.getCoordinate(), newValidMove);
    }

    public void clickOn(PiecesView p) {
        if (MouseEventbanned) return;
        lastClick = p;
        if (selected != null){
            moveController.execute(this.chariotBoard, p.getCoordinate(), this.validMoves);
        }else {
            selectController.execute(this.chariotBoard, p.getCoordinate());
        }
    }

    private void gameOver(boolean isWhiteWin, Board.GameState state) {
        this.MouseEventbanned = true;

        //TODO : Update scores & clear records

        switch (state) {
            case Board.GameState.draw_by_fifty_move_rule,
                 Board.GameState.draw_by_threefold_repetition,
                 Board.GameState.stalemate:
                JOptionPane.showMessageDialog(this, "Draw");
                break;
            case Board.GameState.checkmate:
                if (isWhiteWin) {
                    JOptionPane.showMessageDialog(this, "White win");
                }else {
                    JOptionPane.showMessageDialog(this, "Black win");
                }
        }
    }

    public void freezeBoard(){
        this.MouseEventbanned = true;
    }

    public void unfreezeBoard(){
        this.MouseEventbanned = false;
    }

    public String getViewName() {
        return viewName;
    }
}
