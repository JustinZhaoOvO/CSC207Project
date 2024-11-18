package view.BoardView;
//CreateTime: 2024-11-15 5:15 p.m.

import entity.Coordinate;
import view.BoardView.PiecesView.PiecesView;
import view.LayoutAdapter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BoardLayout extends LayoutAdapter {

    @Override
    public void layoutContainer(Container parent) {
        if (parent instanceof BoardView){
            int len = Math.min(parent.getHeight(), parent.getWidth()) / 8;
            BoardView boardView = (BoardView) parent;
            Map<Coordinate, PiecesView> children = boardView.getChildren();
            for (Coordinate coordinate : children.keySet()) {
                PiecesView piecesView = children.get(coordinate);
                piecesView.setBounds(coordinate.getX() * len, coordinate.getY() *len , len, len);
            }
        }
    }
}
