package entity;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name; // The name should be immutable
    private String password; // Password should be mutable

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = newPassword;
    }
}
