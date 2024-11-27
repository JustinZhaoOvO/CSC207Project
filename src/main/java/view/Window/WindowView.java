package view.Window;
//CreateTime: 2024-11-16 12:11 p.m.


import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class WindowView extends JPanel{

    private final String viewName = "Mainwindow";

    public WindowView() {

    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
