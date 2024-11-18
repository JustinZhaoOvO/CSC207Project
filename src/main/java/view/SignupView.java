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
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

/**
 * The View for the Signup Use Case.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private SignupController signupController;

    private final JButton signUpButton;
    private final JButton cancelButton;
    private final JButton toLoginButton;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        // Initialize components
        final JLabel titleLabel = new JLabel("Sign Up");
        final JLabel usernameLabel = new JLabel("Choose Username:");
        final JLabel passwordLabel = new JLabel("Password:");
        final JLabel repeatPasswordLabel = new JLabel("Re-enter Password:");

        signUpButton = new JButton("Sign Up");
        cancelButton = new JButton("Cancel");
        toLoginButton = new JButton("Back to Login");

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
        buttonPanel.add(cancelButton);
        buttonPanel.add(toLoginButton);
        this.add(buttonPanel, gbc);
    }

    /**
     * Add event listeners for components.
     */
    private void addListeners() {
        signUpButton.addActionListener(evt -> {
            final SignupState currentState = signupViewModel.getState();
            signupController.execute(
                    currentState.getUsername(),
                    currentState.getPassword(),
                    currentState.getRepeatPassword()
            );
        });

        toLoginButton.addActionListener(evt -> signupController.switchToLoginView());

        cancelButton.addActionListener(evt -> JOptionPane.showMessageDialog(this, "Cancel action not implemented."));

        // Add listeners to update state dynamically
        addDocumentListener(usernameInputField, text -> {
            SignupState state = signupViewModel.getState();
            state.setUsername(text);
            signupViewModel.setState(state);
        });

        addDocumentListener(passwordInputField, text -> {
            SignupState state = signupViewModel.getState();
            state.setPassword(text);
            signupViewModel.setState(state);
        });

        addDocumentListener(repeatPasswordInputField, text -> {
            SignupState state = signupViewModel.getState();
            state.setRepeatPassword(text);
            signupViewModel.setState(state);
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
