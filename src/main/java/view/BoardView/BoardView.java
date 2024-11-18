package view.BoardView;
//CreateTime: 2024-11-11 10:26 a.m.

import entity.Cell;
import entity.Coordinate;
import interface_adapter.BoardStateConstants;
import interface_adapter.board.BoardState;
import view.BoardView.PiecesView.PiecesView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BoardView extends JPanel implements PropertyChangeListener {

    Map<Coordinate, PiecesView> children;

    public BoardView() {
        this.children = new HashMap<>();
    }

    public Component add(PiecesView comp, int i, int j) { //idx = y-x
        children.put(new Coordinate(i, j), comp);
        return this.add(comp);
    }

    public Map<Coordinate, PiecesView> getChildren() {
        return children;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        BoardState newValue = (BoardState) evt.getNewValue();
        switch (propertyName) {
            case BoardStateConstants.REPAINT:
                repaintBoard(newValue);
        }
    }

    public void repaintBoard(BoardState state){
        Boolean repaintSuccess = state.getRepaintSuccess();

        if (repaintSuccess) {
            Cell[][] cells = state.getCells();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j] != null && children.containsKey(new Coordinate(i, j))) {
                        Cell cell = cells[i][j];
                        PiecesView piece = children.get(new Coordinate(i, j));
                        if (!(piece.getCurColor() == cell.getBackgroundColor())){
                            piece.setColor(cell.getBackgroundColor());
                            piece.repaint();
                        }if (piece.getImage() != cell.getImage()){
                            piece.setImage(cell.getImage());
                            piece.repaint();
                        }
                    }
                }
            }
        }
    }
}
