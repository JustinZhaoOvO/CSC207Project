package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements PropertyChangeListener {

    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private SignupController signupController;

    private final JButton signUpButton;
    private final JButton toLoginButton;

    private String player1Username;
    private String player1Password;

    private String player2Username;
    private String player2Password;

    private boolean isPlayer1 = true; // Indicates which player is signing up

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Initialize components
        final JLabel titleLabel = new JLabel("Sign Up - Player 1");
        final JLabel usernameLabel = new JLabel("Choose Username:");
        final JLabel passwordLabel = new JLabel("Password:");
        final JLabel repeatPasswordLabel = new JLabel("Re-enter Password:");

        signUpButton = new JButton("Sign Up");
        toLoginButton = new JButton("Back to Login");

        // Add event listeners
        signUpButton.addActionListener(evt -> handleSignUp());
        toLoginButton.addActionListener(evt -> signupController.switchToLoginView());

        // Set layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);

        // Username Label and Field
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(usernameInputField, gbc);

        // Password Label and Field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(passwordInputField, gbc);

        // Repeat Password Label and Field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(repeatPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(repeatPasswordInputField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(signUpButton);
        buttonPanel.add(toLoginButton);
        this.add(buttonPanel, gbc);
    }

    /**
     * Handles the signup process for Player 1 and Player 2.
     */
    private void handleSignUp() {
        final SignupState currentState = signupViewModel.getState();
        String username = currentState.getUsername();
        String password = currentState.getPassword();
        String repeatPassword = currentState.getRepeatPassword();

        if (!password.equals(repeatPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        if (isPlayer1) {
            // Save Player 1's credentials
            player1Username = username;
            player1Password = password;
            isPlayer1 = false;

            // Reset state and clear input fields
            signupViewModel.resetState();
            clearInputFields();

            JOptionPane.showMessageDialog(this, "Player 1 registered! Now, Player 2, please sign up.");
            ((JLabel) this.getComponent(0)).setText("Sign Up - Player 2");
        } else {
            // Save Player 2's credentials
            player2Username = username;
            player2Password = password;

            JOptionPane.showMessageDialog(this, "Player 2 registered! Starting the game...");
            signupController.startGameWithPlayers(player1Username, player2Username);
        }
    }

    /**
     * Clears all input fields in the signup form.
     */
    private void clearInputFields() {
        usernameInputField.setText("");
        passwordInputField.setText("");
        repeatPasswordInputField.setText("");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
