package view;

import app.ViewStates;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements PropertyChangeListener {

    private final String viewName;
    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private SignupController signupController;

    private final JButton signUpButton;
    private final JButton toLoginButton;

    private final JLabel titleLabel;

    public SignupView(SignupViewModel signupViewModel, String viewName, ViewManagerModel viewManagerModel) {
        this.signupViewModel = signupViewModel;
        this.viewName = viewName;
        this.viewManagerModel = viewManagerModel;
        signupViewModel.addPropertyChangeListener(this);

        // Initialize components
        titleLabel = new JLabel(viewName.equals(ViewStates.SIGNUP_PLAYER1) ? "Sign Up - Black" : "Sign Up - White");
        final JLabel usernameLabel = new JLabel("Choose Username:");
        final JLabel passwordLabel = new JLabel("Password:");
        final JLabel repeatPasswordLabel = new JLabel("Re-enter Password:");

        signUpButton = new JButton("Sign Up");
        toLoginButton = new JButton("Login Instead");

        // Add event listeners
        addListeners();

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
     * Add event listeners for components.
     */
    private void addListeners() {
        signUpButton.addActionListener(evt -> {
            String username = usernameInputField.getText().trim();
            String password = new String(passwordInputField.getPassword());
            String repeatPassword = new String(repeatPasswordInputField.getPassword());

            signupController.execute(username, password, repeatPassword, viewName.equals(ViewStates.SIGNUP_PLAYER1));
        });

        toLoginButton.addActionListener(evt -> {
            // Navigation to the login view
            if (viewName.equals(ViewStates.SIGNUP_PLAYER1)) {
                System.out.println("Navigating to LOGIN_PLAYER1");
                viewManagerModel.setState(ViewStates.LOGIN_PLAYER1);
            } else {
                System.out.println("Navigating to LOGIN_PLAYER2");
                viewManagerModel.setState(ViewStates.LOGIN_PLAYER2);
            }
            viewManagerModel.firePropertyChanged();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(() -> {
            final SignupState state = (SignupState) evt.getNewValue();
            if (state.getUsernameError() != null) {
                JOptionPane.showMessageDialog(this, state.getUsernameError());
                state.setUsernameError(null); // Reset error after displaying
            } else {
                JOptionPane.showMessageDialog(this, "Signup successful!");
                // Navigation is handled by the presenter via ViewManagerModel
            }
        });
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
