package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";

    private final LoginViewModel loginViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private LoginController loginController;

    private final JButton logInButton;
    private final JButton cancelButton;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        // Initialize components
        final JLabel titleLabel = new JLabel("Login Screen");
        final JLabel usernameLabel = new JLabel("Username:");
        final JLabel passwordLabel = new JLabel("Password:");

        logInButton = new JButton("Log In");
        cancelButton = new JButton("Cancel");

        // Add event listeners
        addListeners();

        // Set layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center title
        this.add(titleLabel, gbc);

        // Username Label and Field
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END; // Align label to right
        this.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START; // Align field to left
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
        buttonPanel.add(cancelButton);
        this.add(buttonPanel, gbc);
    }

    /**
     * Add event listeners for components.
     */
    private void addListeners() {
        logInButton.addActionListener(evt -> {
            final LoginState currentState = loginViewModel.getState();
            loginController.execute(
                    currentState.getUsername(),
                    currentState.getPassword()
            );
        });

        cancelButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Cancel action not implemented."));

        // Add listeners to update state dynamically
        addDocumentListener(usernameInputField, text -> {
            LoginState state = loginViewModel.getState();
            state.setUsername(text);
            loginViewModel.setState(state);
        });

        addDocumentListener(passwordInputField, text -> {
            LoginState state = loginViewModel.getState();
            state.setPassword(text);
            loginViewModel.setState(state);
        });
    }

    /**
     * Add a document listener to a text field or password field.
     */
    private void addDocumentListener(JTextField field, java.util.function.Consumer<String> callback) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                callback.accept(field instanceof JPasswordField
                        ? new String(((JPasswordField) field).getPassword())
                        : field.getText());
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }
            @Override
            public void removeUpdate(DocumentEvent e) { update(); }
            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Button action not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController controller) {
        this.loginController = controller;
    }
}
