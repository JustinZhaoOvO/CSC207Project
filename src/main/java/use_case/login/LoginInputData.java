package use_case.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {

    private final String username;
    private final String password;
    private final boolean isPlayer1; // Added this flag

    public LoginInputData(String username, String password, boolean isPlayer1) {
        this.username = username;
        this.password = password;
        this.isPlayer1 = isPlayer1;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }

}
