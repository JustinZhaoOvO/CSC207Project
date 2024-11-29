package use_case.signup;

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String username;
    private final boolean isPlayer1;

    public SignupOutputData(String username, boolean isPlayer1) {
        this.username = username;
        this.isPlayer1 = isPlayer1;
    }

    public String getUsername() {
        return username;
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }
}
