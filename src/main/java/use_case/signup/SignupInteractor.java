package use_case.signup;

import entity.User;
import entity.UserFactory;

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String password = signupInputData.getPassword();
        final String repeatPassword = signupInputData.getRepeatPassword();
        final boolean isPlayer1 = signupInputData.isPlayer1();

        if (userDataAccessObject.existsByName(username)) {
            userPresenter.prepareFailView("User already exists.", isPlayer1);
            return;
        } else if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView("Passwords don't match.", isPlayer1);
            return;
        } else {
            final User user = userFactory.create(username, password);
            userDataAccessObject.save(user);

            final SignupOutputData signupOutputData = new SignupOutputData(username, isPlayer1);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }
}
