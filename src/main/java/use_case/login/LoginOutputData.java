package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean isPlayer1;

    public LoginOutputData(String username, boolean isPlayer1) {
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
