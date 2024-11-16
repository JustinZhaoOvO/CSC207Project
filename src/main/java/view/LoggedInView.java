package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.logout.LogoutController;

/**
 * The View for when the user is logged into the program.
 */
public class LoggedInView extends JPanel implements PropertyChangeListener {

    private final String viewName = "logged in";

    private final LoggedInViewModel loggedInViewModel;
    private final JTextField passwordInputField = new JTextField(15);
    private final JLabel usernameLabel = new JLabel();
    private final JLabel passwordErrorField = new JLabel();

    private ChangePasswordController changePasswordController;
    private LogoutController logoutController;

    private final JButton logOutButton;
    private final JButton changePasswordButton;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        // Initialize components
        final JLabel titleLabel = new JLabel("Logged In Screen");
        final JLabel usernameStaticLabel = new JLabel("Currently logged in:");
        final JLabel passwordLabel = new JLabel("New Password:");

        logOutButton = new JButton("Log Out");
        changePasswordButton = new JButton("Change Password");

        // Add listeners
        addListeners();

        // Set layout
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center title
        this.add(titleLabel, gbc);

        // Username Info
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END; // Align label to the right
        this.add(usernameStaticLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START; // Align field to the left
        this.add(usernameLabel, gbc);

        // Password Info
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        this.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        this.add(passwordInputField, gbc);

        // Password Error Field
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(passwordErrorField, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(logOutButton);
        buttonPanel.add(changePasswordButton);
        this.add(buttonPanel, gbc);
    }

    /**
     * Add event listeners for components.
     */
    private void addListeners() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void updatePassword() {
                final LoggedInState currentState = loggedInViewModel.getState();
                currentState.setPassword(passwordInputField.getText());
                loggedInViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { updatePassword(); }
            @Override
            public void removeUpdate(DocumentEvent e) { updatePassword(); }
            @Override
            public void changedUpdate(DocumentEvent e) { updatePassword(); }
        });

        changePasswordButton.addActionListener(evt -> {
            final LoggedInState currentState = loggedInViewModel.getState();
            changePasswordController.execute(
                    currentState.getUsername(),
                    currentState.getPassword()
            );
        });

        logOutButton.addActionListener(evt -> {
            final LoggedInState currentState = loggedInViewModel.getState();
            logoutController.execute(currentState.getUsername());
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            usernameLabel.setText(state.getUsername());
        } else if ("password".equals(evt.getPropertyName())) {
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, "Password updated for " + state.getUsername());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}
