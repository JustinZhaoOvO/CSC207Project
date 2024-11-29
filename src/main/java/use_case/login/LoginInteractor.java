package use_case.login;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        final boolean isPlayer1 = loginInputData.isPlayer1();

        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.", isPlayer1);
            return;
        }

        boolean isAuthenticated = userDataAccessObject.authenticate(username, password);
        if (!isAuthenticated) {
            loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".", isPlayer1);
            return;
        }

        userDataAccessObject.setCurrentUsername(username, isPlayer1);
        final LoginOutputData loginOutputData = new LoginOutputData(username, isPlayer1);
        loginPresenter.prepareSuccessView(loginOutputData);
    }
}
