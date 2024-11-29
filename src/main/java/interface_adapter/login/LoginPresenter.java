package interface_adapter.login;

import app.ViewStates;
import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModelPlayer1;
    private final LoginViewModel loginViewModelPlayer2;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          LoginViewModel loginViewModelPlayer1,
                          LoginViewModel loginViewModelPlayer2) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModelPlayer1 = loginViewModelPlayer1;
        this.loginViewModelPlayer2 = loginViewModelPlayer2;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        if (response.isPlayer1()) {
            // Player 1 successfully logged in, now transition to Signup for Player 2
            viewManagerModel.setState(ViewStates.SIGNUP_PLAYER2);
        } else {
            // Player 2 successfully logged in, proceed to the logged-in view
            viewManagerModel.setState(ViewStates.LOGGED_IN_VIEW);
        }
        viewManagerModel.firePropertyChanged(); // Notify view manager to update the view
    }

    @Override
    public void prepareFailView(String errorMessage, boolean isPlayer1) {
        final LoginState loginState = isPlayer1
                ? loginViewModelPlayer1.getState()
                : loginViewModelPlayer2.getState();
        loginState.setLoginError(errorMessage);
        if (isPlayer1) {
            loginViewModelPlayer1.firePropertyChanged();
        } else {
            loginViewModelPlayer2.firePropertyChanged();
        }
    }
}
