package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

/**
 * Controller for the Signup Use Case.
 */
public class SignupController {

    private final SignupInputBoundary userSignupUseCaseInteractor;

    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     *
     * @param username       the username to sign up
     * @param password1      the password
     * @param password2      the password repeated
     * @param isPlayer1      whether the current player is Player 1
     */
    public void execute(String username, String password1, String password2, boolean isPlayer1) {
        final SignupInputData signupInputData = new SignupInputData(username, password1, password2, isPlayer1);
        userSignupUseCaseInteractor.execute(signupInputData);
    }
}
