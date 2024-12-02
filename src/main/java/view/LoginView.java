package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import app.ViewStates;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements PropertyChangeListener {

    private final String viewName;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private LoginController loginController;

    private final JButton logInButton;
    private final JButton backToSignupButton;

    private final JLabel titleLabel;

    public LoginView(LoginViewModel loginViewModel, String viewName, ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.viewName = viewName;
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel.addPropertyChangeListener(this);

        // Initialize components
        titleLabel = new JLabel(viewName.equals(ViewStates.LOGIN_PLAYER1) ? "Login - Black" : "Login - White");
        final JLabel usernameLabel = new JLabel("Username:");
        final JLabel passwordLabel = new JLabel("Password:");

        logInButton = new JButton("Log In");
        backToSignupButton = new JButton("Back to Signup");

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

        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logInButton);
        buttonPanel.add(backToSignupButton);
        this.add(buttonPanel, gbc);
    }

    /**
     * Add event listeners for components.
     */
    private void addListeners() {
        logInButton.addActionListener(evt -> {
            System.out.println("Log In button clicked"); // Debugging statement
            String username = usernameInputField.getText().trim();
            String password = new String(passwordInputField.getPassword());

            if (loginController != null) {
                loginController.execute(
                        username,
                        password,
                        viewName.equals(ViewStates.LOGIN_PLAYER1) // isPlayer1 flag
                );
            } else {
                System.out.println("LoginController is null!"); // Debugging statement
            }
        });

        backToSignupButton.addActionListener(evt -> {
            // Navigation back to the signup view
            if (viewName.equals(ViewStates.LOGIN_PLAYER1)) {
                viewManagerModel.setState(ViewStates.SIGNUP_PLAYER1);
            } else {
                viewManagerModel.setState(ViewStates.SIGNUP_PLAYER2);
            }
            viewManagerModel.firePropertyChanged();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(() -> {
            final LoginState state = (LoginState) evt.getNewValue();
            if (state.getLoginError() != null) {
                JOptionPane.showMessageDialog(this, state.getLoginError());
                state.setLoginError(null); // Reset error after displaying
            } else {
                JOptionPane.showMessageDialog(this, "Login successful!");
                // Navigation is handled by the presenter via ViewManagerModel
            }
        });
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController controller) {
        this.loginController = controller;
    }
}
