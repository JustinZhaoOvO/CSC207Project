package view.BoardView.PromotionView.PromotionImageView;
//CreateTime: 2024-11-20 6:25 p.m.

import view.BoardView.ColorConstants;
import view.BoardView.PromotionView.PromotionView;

import javax.swing.*;
import java.awt.*;

public class PromotionImageView extends JPanel {

    private final Image image;
    private final PromotionView parent;
    private boolean selected;

    public PromotionImageView(Image image, PromotionView parent) {
        this.image = image;
        this.parent = parent;
        this.setOpaque(false);
    }

    @Override
    public PromotionView getParent() {
        return parent;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ColorConstants.BLUEWHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        if (selected) {
            g.setColor(ColorConstants.SELECTPROMOTIONCOMPONENT);
            for (int i = 0; i < this.getWidth() / 6; i+=2) {
                g.drawRect(i,i,this.getWidth() - i*2,getHeight() - i*2);
            }
        }
    }

    public void selected(){
        this.selected = true;
        repaint();
    }

    public void unselected() {
        this.selected = false;
        repaint();
    }

}
