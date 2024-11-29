package use_case.signup;

/**
 * The Input Data for the Signup Use Case.
 */
public class SignupInputData {

    private final String username;
    private final String password;
    private final String repeatPassword;
    private final boolean isPlayer1; // Added this flag

    public SignupInputData(String username, String password, String repeatPassword, boolean isPlayer1) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.isPlayer1 = isPlayer1;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }
}
