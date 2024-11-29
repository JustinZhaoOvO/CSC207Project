package view.Window;

import entity.ChariotBoard;

import javax.swing.*;

public class WindowViewTest extends JFrame {
    public static void main(String[] args) {
        new WindowViewTest();
    }

    public WindowViewTest() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(1014, 637);
        this.setLocation(250,150);


        windowBuilder builder = new windowBuilder();
        builder.addBoard();
        builder.addTimer(); // Add timer to the window
        WindowView build = builder.build();
        build.startTheGameWith(new ChariotBoard());

        this.setContentPane(build);
        this.setVisible(true);
    }
}
