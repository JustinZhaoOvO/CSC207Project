package view.Window;

import app.ViewStates;
import entity.ChariotBoard;
import interface_adapter.window.WindowViewModel;

import javax.swing.*;

public class WindowViewTest extends JFrame {
    public static void main(String[] args) {
        new WindowViewTest();
    }

    public WindowViewTest() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(814, 637);
        this.setLocation(250, 150);

        WindowViewModel windowViewModel = new WindowViewModel(ViewStates.BOARD_VIEW);

        WindowBuilder builder = new WindowBuilder(windowViewModel);
        builder.addBoard();
        builder.addTimer(); // Add timer to the window
        WindowView build = builder.build();
        build.startTheGameWith(new ChariotBoard());

        this.setContentPane(build);
        this.setVisible(true);
    }
}