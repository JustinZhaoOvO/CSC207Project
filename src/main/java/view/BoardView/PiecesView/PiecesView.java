package view.BoardView.PiecesView;
//CreateTime: 2024-11-11 9:29 a.m.

import view.BoardView.ColorConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PiecesView extends JPanel {
    private Image image;
    private final Color backgroundColor;
    private boolean selected;
    private boolean validMoveToHere;
    private boolean hovered;

    public PiecesView(Color backgroundColor, Image image) {
        this.image = image;
        this.backgroundColor = backgroundColor;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.backgroundColor);
        if (selected && image != null) {
            g.setColor(ColorConstants.LIGHTWHITE);
        }if (validMoveToHere && image != null) {
            g.setColor(ColorConstants.TRANSLUCENTRED);
        }g.fillRect(0, 0, getWidth(), getHeight());
        if (validMoveToHere && image == null) {
            g.setColor(ColorConstants.TRANSLUCENTRED);
            g.fillOval(this.getWidth()/3,this.getHeight()/3,this.getWidth()/3,this.getHeight()/3);
        }if (image != null) {
            g.drawImage(this.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }if (hovered){
            g.setColor(ColorConstants.CYAN);
            for (int i = 0; i < this.getWidth() / 6; i+=2) {
                g.drawRect(i,i,this.getWidth() - i*2,getHeight() - i*2);
            }
        }
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isValidMoveToHere() {
        return validMoveToHere;
    }

    public boolean isSelected() {
        return selected;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;


    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setValidMoveToHere(boolean validMoveToHere) {
        this.validMoveToHere = validMoveToHere;
    }

    
    public void copyFrom(PiecesView p){
        if (p == null) return;
        this.setImage(p.getImage());
        this.setSelected(p.isSelected());
        this.setValidMoveToHere(p.isValidMoveToHere());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PiecesView that = (PiecesView) o;
        return isSelected() == that.isSelected()
                && isValidMoveToHere() == that.isValidMoveToHere()
                && Objects.equals(getImage(), that.getImage())
                && Objects.equals(backgroundColor, that.backgroundColor);
    }

}
