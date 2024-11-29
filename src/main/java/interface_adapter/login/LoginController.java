package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;

/**
 * The controller for the Login Use Case.
 */
public class LoginController {

    private final LoginInputBoundary loginUseCaseInteractor;

    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * Executes the Login Use Case.
     *
     * @param username  the username of the user logging in
     * @param password  the password of the user logging in
     * @param isPlayer1 indicates whether it's Player 1
     */
    public void execute(String username, String password, boolean isPlayer1) {
        final LoginInputData loginInputData = new LoginInputData(username, password, isPlayer1);
        loginUseCaseInteractor.execute(loginInputData);
    }
}
