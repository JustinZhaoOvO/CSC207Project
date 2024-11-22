package view.BoardView.PromotionView.PromotionImageView;
//CreateTime: 2024-11-20 6:37 p.m.

import view.BoardView.MouseListenerAdapter;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PromotionImageListener extends MouseListenerAdapter {

    private final String type;

    public PromotionImageListener(String type) {
        this.type = type;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Component component = e.getComponent();
        if (component instanceof PromotionImageView view){
            view.getParent().performPromotion(type);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
    }
}
