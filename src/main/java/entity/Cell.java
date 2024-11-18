package entity;
//CreateTime: 2024-11-14 5:15 p.m.

import java.awt.*;

public class Cell {

    private Image image;

    private Color backgroundColor;


    public Cell(Image image, Color backgroundColor) {
        this.image = image;
        this.backgroundColor = backgroundColor;
    }

    public Image getImage() {
        return image;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
