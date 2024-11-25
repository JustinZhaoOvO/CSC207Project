package view.BoardView.PromotionView.PromotionImageView;
//CreateTime: 2024-11-20 6:25 p.m.

import view.BoardView.ColorConstants;
import view.BoardView.PromotionView.PromotionView;

import javax.swing.*;
import java.awt.*;

public class PromotionImageView extends JPanel {

    private final Image image;
    private final PromotionView parent;

    public PromotionImageView(Image image, PromotionView parent) {
        this.image = image;
        this.parent = parent;
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
    }
}
