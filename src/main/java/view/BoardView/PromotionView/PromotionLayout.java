package view.BoardView.PromotionView;
//CreateTime: 2024-11-20 6:10 p.m.

import view.LayoutAdapter;

import java.awt.*;

public class PromotionLayout extends LayoutAdapter {

    @Override
    public void layoutContainer(Container parent) {
        if (parent instanceof PromotionView promotionView) {
            int len = Math.min(parent.getHeight(), parent.getWidth()) / 2;
            promotionView.getMap().get(PromotionNameConstants.QUEEN).setBounds(0, 0, len, len);
            promotionView.getMap().get(PromotionNameConstants.KNIGHT).setBounds(0, len, len, len);
            promotionView.getMap().get(PromotionNameConstants.ROOK).setBounds(len, 0, len, len);
            promotionView.getMap().get(PromotionNameConstants.BISHOP).setBounds(len, len , len, len);
        }
    }
}
