package data_access;

import entity.User;
import entity.UserFactory;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.logging.Logger;

/**
 * The DAO for user data.
 */
public class DBUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface {

    private static final Logger LOGGER = Logger.getLogger(DBUserDataAccessObject.class.getName());

    // Constants
    private static final int SUCCESS_CODE = 200;
    private static final int NOT_FOUND_CODE = 404;
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";
    private static final String SALT = "salt";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private final UserFactory userFactory;
    private Map<Boolean, String> currentUsernames = new HashMap<>();

    public DBUserDataAccessObject(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

    @Override
    public User get(String username) {
        LOGGER.info("Getting user with username: " + username);
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == SUCCESS_CODE) {
                final JSONObject responseBody = new JSONObject(response.body().string());
                final JSONObject userJSONObject = responseBody.getJSONObject("user");
                final String name = userJSONObject.getString(USERNAME);
                final String password = userJSONObject.getString(PASSWORD);
                final String salt = userJSONObject.getString(SALT);

                return userFactory.create(name, salt + ":" + password);
            } else if (response.code() == NOT_FOUND_CODE) {
                LOGGER.warning("User not found: " + username);
                return null;
            } else {
                throw new RuntimeException("Unexpected response code: " + response.code());
            }
        } catch (IOException | JSONException ex) {
            LOGGER.severe("Error getting user: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean existsByName(String username) {
        LOGGER.info("Checking if user exists: " + username);
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(String.format("http://vm003.teach.cs.toronto.edu:20112/user?username=%s", username))
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.code() == SUCCESS_CODE;
        } catch (IOException ex) {
            LOGGER.severe("Error checking user existence: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        LOGGER.info("Saving user: " + user.getName());
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        byte[] saltBytes = generateSalt();
        String salt = Base64.getEncoder().encodeToString(saltBytes);
        String hashedPassword = hashPassword(user.getPassword().toCharArray(), saltBytes);

        final MediaType mediaType = MediaType.parse(CONTENT_TYPE_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(USERNAME, user.getName());
        requestBody.put(PASSWORD, hashedPassword);
        requestBody.put(SALT, salt);

        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);
        final Request request = new Request.Builder()
                .url("http://vm003.teach.cs.toronto.edu:20112/user")
                .method("POST", body)
                .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.code() != SUCCESS_CODE) {
                final JSONObject responseBody = new JSONObject(response.body().string());
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        } catch (IOException | JSONException ex) {
            LOGGER.severe("Error saving user: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @Override
    public String getCurrentUsername() {
        return "";
    }

    @Override
    public void setCurrentUsername(String username) {

    }

    @Override
    public void logout(boolean isPlayer1) {
        // Log out the user (remove from current logged-in state)
        LOGGER.info("Logging out user: Player1 = " + isPlayer1);
        currentUsernames.remove(isPlayer1);
    }


    @Override
    public boolean authenticate(String username, String password) {
        LOGGER.info("Authenticating user: " + username);
        User user = get(username);
        if (user == null) {
            return false;
        }

        String storedPassword = user.getPassword(); // Format: "salt:hashedPassword"
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            throw new RuntimeException("Invalid stored password format.");
        }
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String storedHash = parts[1];

        return verifyPassword(password.toCharArray(), salt, storedHash);
    }

    @Override
    public void setCurrentUsername(String username, boolean isPlayer1) {

    }

    @Override
    public String getCurrentUsername(boolean isPlayer1) {
        return "";
    }

    private String hashPassword(final char[] password, final byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.severe("Error while hashing a password: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private boolean verifyPassword(final char[] password, final byte[] salt, final String expectedHash) {
        String pwdHash = hashPassword(password, salt);
        return pwdHash.equals(expectedHash);
    }

    @Override
    public void changePassword(User user) {

    }
}
