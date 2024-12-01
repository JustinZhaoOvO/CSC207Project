package app;

import data_access.FileUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.ChangePasswordController;
import interface_adapter.change_password.ChangePasswordPresenter;
import interface_adapter.change_password.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.window.WindowViewModel;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.BoardView.BoardView;
import view.LoggedInView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;
import entity.ChariotBoard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The AppBuilder class is responsible for constructing the application components.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final UserFactory userFactory = new CommonUserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private FileUserDataAccessObject fileUserDataAccessObject;

    // Views
    private SignupView signupViewPlayer1;
    private SignupView signupViewPlayer2;
    private SignupViewModel signupViewModelPlayer1;
    private SignupViewModel signupViewModelPlayer2;

    private LoginView loginViewPlayer1;
    private LoginView loginViewPlayer2;
    private LoginViewModel loginViewModelPlayer1;
    private LoginViewModel loginViewModelPlayer2;

    private LoggedInView loggedInView;
    private LoggedInViewModel loggedInViewModel;

    public AppBuilder() {
        try {
            // Set the path to the CSV file where user data will be stored
            String csvPath = "users.csv"; // Adjust the path as necessary
            fileUserDataAccessObject = new FileUserDataAccessObject(csvPath, userFactory);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize FileUserDataAccessObject: " + e.getMessage(), e);
        }

        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSignupView() {
        signupViewModelPlayer1 = new SignupViewModel();
        signupViewPlayer1 = new SignupView(signupViewModelPlayer1, ViewStates.SIGNUP_PLAYER1, viewManagerModel);

        signupViewModelPlayer2 = new SignupViewModel();
        signupViewPlayer2 = new SignupView(signupViewModelPlayer2, ViewStates.SIGNUP_PLAYER2, viewManagerModel);

        cardPanel.add(signupViewPlayer1, ViewStates.SIGNUP_PLAYER1);
        cardPanel.add(signupViewPlayer2, ViewStates.SIGNUP_PLAYER2);
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModelPlayer1 = new LoginViewModel();
        loginViewPlayer1 = new LoginView(loginViewModelPlayer1, ViewStates.LOGIN_PLAYER1, viewManagerModel);

        loginViewModelPlayer2 = new LoginViewModel();
        loginViewPlayer2 = new LoginView(loginViewModelPlayer2, ViewStates.LOGIN_PLAYER2, viewManagerModel);

        cardPanel.add(loginViewPlayer1, ViewStates.LOGIN_PLAYER1);
        cardPanel.add(loginViewPlayer2, ViewStates.LOGIN_PLAYER2);
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel);
        cardPanel.add(loggedInView, ViewStates.LOGGED_IN_VIEW);
        return this;
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(
                viewManagerModel, signupViewModelPlayer1, signupViewModelPlayer2,
                loginViewModelPlayer1, loginViewModelPlayer2
        );
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                fileUserDataAccessObject, signupOutputBoundary, userFactory
        );

        final SignupController signupController = new SignupController(userSignupInteractor);
        signupViewPlayer1.setSignupController(signupController);
        signupViewPlayer2.setSignupController(signupController);

        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(
                viewManagerModel,
                loginViewModelPlayer1, loginViewModelPlayer2
        );
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                fileUserDataAccessObject, loginOutputBoundary
        );

        final LoginController loginController = new LoginController(loginInteractor);
        loginViewPlayer1.setLoginController(loginController);
        loginViewPlayer2.setLoginController(loginController);

        return this;
    }

    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary =
                new ChangePasswordPresenter(loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(fileUserDataAccessObject, changePasswordOutputBoundary, userFactory);

        final ChangePasswordController changePasswordController =
                new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    // Add Board View in the AppBuilder class
    public AppBuilder addBoardView() {
        WindowViewModel windowViewModel = new WindowViewModel(ViewStates.BOARD_VIEW);
        BoardView boardView = new BoardView(windowViewModel);
        cardPanel.add(boardView, ViewStates.BOARD_VIEW);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Chess Game");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        // Initialize the ViewManager to listen for state changes
        viewManagerModel.addPropertyChangeListener(viewManager);

        // Start the application with the signup view for Player 1
        viewManagerModel.setState(ViewStates.SIGNUP_PLAYER1);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setLocationRelativeTo(null); // Center the window
        application.setVisible(true);

        return application;
    }
}
