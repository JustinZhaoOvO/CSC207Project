package view.BoardView.PromotionView;
//CreateTime: 2024-11-20 6:07 p.m.

import entity.ImageConstants;
import view.BoardView.BoardView;
import view.BoardView.PromotionView.PromotionImageView.PromotionImageListener;
import view.BoardView.PromotionView.PromotionImageView.PromotionImageView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PromotionView extends JPanel {

    private final BoardView boardView;
    private final Map<String, PromotionImageView> map;

    public PromotionView(boolean isBlack, BoardView boardView) {
        this.map = new HashMap<>();
        this.boardView = boardView;

        PromotionImageView bishop = new PromotionImageView(
                isBlack ? ImageConstants.BLACKBISHOP : ImageConstants.WHITEBISHOP, this);
        PromotionImageView knight = new PromotionImageView(
                isBlack ? ImageConstants.BLACKKNIGHT : ImageConstants.WHITEKNIGHT, this);
        PromotionImageView queen = new PromotionImageView(
                isBlack ? ImageConstants.BLACKQUEEN : ImageConstants.WHITEQUEEN, this);
        PromotionImageView rook = new PromotionImageView(
                isBlack ? ImageConstants.BLACKROOK : ImageConstants.WHITEROOK, this);

        this.add(bishop);
        this.add(knight);
        this.add(queen);
        this.add(rook);

        map.put(PromotionNameConstants.BISHOP, bishop);
        map.put(PromotionNameConstants.KNIGHT, knight);
        map.put(PromotionNameConstants.QUEEN, queen);
        map.put(PromotionNameConstants.ROOK, rook);

        bishop.addMouseListener(new PromotionImageListener(PromotionNameConstants.PromoteToBishop));
        knight.addMouseListener(new PromotionImageListener(PromotionNameConstants.PromoteToKnight));
        queen.addMouseListener(new PromotionImageListener(PromotionNameConstants.PromoteToQueen));
        rook.addMouseListener(new PromotionImageListener(PromotionNameConstants.PromoteToRook));
    }

    public void performPromotion(String type) {
        boardView.promoteTo(type);
    }

    public Map<String, PromotionImageView> getMap() {
        return map;
    }
}
