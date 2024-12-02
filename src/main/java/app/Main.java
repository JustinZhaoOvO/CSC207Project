package app;

import javax.swing.*;

/**
 * The Main class of our application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();

        // Build the application with distinct views for Player 1 and Player 2
        final JFrame application = appBuilder
                .addSignupView() // Adds both Player 1 and Player 2 Signup Views
                .addLoginView()  // Adds both Player 1 and Player 2 Login Views
                .addLoggedInView()
                .addBoardView()
                // Adds the logged-in view
                .addSignupUseCase() // Configures the Signup Use Case
                .addLoginUseCase()  // Configures the Login Use Case
                .addChangePasswordUseCase() // Configures the Change Password Use Case
                .build();

        // Set up and display the application window
        application.setSize(814, 637);
        application.setResizable(false);
        application.setVisible(true);
    }
}
