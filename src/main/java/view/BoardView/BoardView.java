package view.BoardView;
//CreateTime: 2024-11-11 10:26 a.m.

import entity.ChariotBoard;
import entity.BoardConstants;
import entity.Coordinate;
import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import interface_adapter.board.move.MoveController;
import interface_adapter.board.repaintboard.RepaintBoardController;
import interface_adapter.board.select.SelectController;
import interface_adapter.window.WindowState;
import interface_adapter.window.WindowViewModel;
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

    private boolean Paused;

    private boolean gameOver;

    private final WindowViewModel windowViewModel;

    public BoardView(WindowViewModel windowViewModel) {

        this.windowViewModel = windowViewModel;

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
        this.Paused = false;
        this.MouseEventbanned = false;
        this.gameOver = false;

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
        if (MouseEventbanned || Paused) return;
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
        if (evt.getNewValue() instanceof BoardState newValue){
            String propertyName = evt.getPropertyName();
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
                case BoardStateConstants.GAMEOVER -> gameOver(newValue.getMsg());
            }
        }else if (evt.getNewValue() instanceof WindowState newValue){
            String propertyName = evt.getPropertyName();
            if ("paused".equals(propertyName)){
                if (newValue.isPaused()){
                    freezeBoard();
                }else{
                    unfreezeBoard();
                }
            }else if ("restart".equals(propertyName)){
                if (newValue.isRestart()){
                    if (!this.gameOver){// stop timer, Update scores
                        WindowState windowState = new WindowState();
                        windowState.setGameOver(true);
                        windowViewModel.setState(windowState);
                        windowViewModel.firePropertyChanged("gameOver");
                        forfeit();
                    }
                }restartTheGameWith(new ChariotBoard());
            }else if ("blackRanOutOfTime".equals(propertyName)){
                timeOut(newValue.isBlackRanOutOfTime());
            }
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
        if (MouseEventbanned || Paused) return;
        lastClick = p;
        if (selected != null){
            moveController.execute(this.chariotBoard, p.getCoordinate(), this.validMoves);
        }else {
            selectController.execute(this.chariotBoard, p.getCoordinate());
        }
    }

    private void gameOver(String msg) {
        this.MouseEventbanned = true;
        this.gameOver = true;
        JOptionPane.showMessageDialog(this, msg);
    }

    public void timeOut(boolean blackRanOutOfTime){
        gameOver(blackRanOutOfTime ?  "Black ran out of time! White win" : "White ran out of time! Black win");
    }

    public void forfeit(){
        gameOver(chariotBoard.isBlackToMove() ?  "Black forfeited! White win" : "White forfeited! Black win");
    }

    public void freezeBoard(){
        this.selected = null;
        this.validMoves = null;
        repaintBoardController.execute(this.chariotBoard);
        this.Paused = true;
    }

    public void unfreezeBoard(){
        this.Paused = false;
    }

    public String getViewName() {
        return viewName;
    }
}
