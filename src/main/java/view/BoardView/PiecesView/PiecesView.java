package view.BoardView.PiecesView;
//CreateTime: 2024-11-11 9:29 a.m.

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class PiecesView extends JPanel {
    private Image image;
    private Color curColor;
    private final Color backgroundColor;

    public PiecesView(Color backgroundColor, Image image) {
        this.image = image;
        this.curColor = backgroundColor;
        this.backgroundColor = backgroundColor;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.getCurColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (image != null) {
            g.drawImage(this.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }

    public Color getCurColor() {
        return curColor;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setColor(Color color) {
        this.curColor = color;
    }

    public void resetColor(){
        this.curColor = backgroundColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PiecesView that = (PiecesView) o;
        return Objects.equals(image, that.image) && Objects.equals(curColor, that.curColor);
    }
}
