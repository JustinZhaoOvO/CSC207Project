package view.BoardView.PromotionView.PromotionImageView;
//CreateTime: 2024-11-20 6:37 p.m.

import view.BoardView.MouseListenerAdapter;
import view.BoardView.PromotionView.PromotionView;

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
        Component source = (Component) e.getSource();
        if (source instanceof PromotionImageView view){
            PromotionView parent = view.getParent();
            parent.setSelectedTo(view);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Component source = (Component) e.getSource();
        if (source instanceof PromotionImageView view){
            PromotionView parent = view.getParent();
            parent.setSelectedTo(null);
        }
    }
}
