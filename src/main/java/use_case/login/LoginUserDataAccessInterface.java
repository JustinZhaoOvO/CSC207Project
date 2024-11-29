package use_case.login;

import entity.User;

public interface LoginUserDataAccessInterface {
    User get(String username);
    boolean existsByName(String username);
    boolean authenticate(String username, String password);
    void setCurrentUsername(String username, boolean isPlayer1); // Updated method
    String getCurrentUsername(boolean isPlayer1); // Updated method
}
