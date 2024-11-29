package interface_adapter.signup;

import app.ViewStates;
import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

/**
 * The Presenter for the Signup Use Case.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModelPlayer1;
    private final SignupViewModel signupViewModelPlayer2;
    private final LoginViewModel loginViewModelPlayer1;
    private final LoginViewModel loginViewModelPlayer2;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModelPlayer1,
                           SignupViewModel signupViewModelPlayer2,
                           LoginViewModel loginViewModelPlayer1,
                           LoginViewModel loginViewModelPlayer2) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModelPlayer1 = signupViewModelPlayer1;
        this.signupViewModelPlayer2 = signupViewModelPlayer2;
        this.loginViewModelPlayer1 = loginViewModelPlayer1;
        this.loginViewModelPlayer2 = loginViewModelPlayer2;
    }

    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // Transition to the appropriate login view
        if (response.isPlayer1()) {
            viewManagerModel.setState(ViewStates.LOGIN_PLAYER1);
        } else {
            viewManagerModel.setState(ViewStates.LOGIN_PLAYER2);
        }
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData, boolean isPlayer1) {

    }

    @Override
    public void prepareFailView(String error, boolean isPlayer1) {
        final SignupState signupState = isPlayer1
                ? signupViewModelPlayer1.getState()
                : signupViewModelPlayer2.getState();
        signupState.setUsernameError(error);
        if (isPlayer1) {
            signupViewModelPlayer1.firePropertyChanged();
        } else {
            signupViewModelPlayer2.firePropertyChanged();
        }
    }
}
