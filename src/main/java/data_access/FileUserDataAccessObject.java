package data_access;

import entity.User;
import entity.UserFactory;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * DAO for user data implemented using a File to persist the data.
 */
public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface {

    private static final String HEADER = "username,password";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();
    private String currentUsername;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) throws IOException {
        csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);

        if (csvFile.length() == 0) {
            save();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be: %s but was: %s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String username = col[headers.get("username")];
                    final String password = col[headers.get("password")];
                    final User user = userFactory.create(username, password);
                    accounts.put(username, user);
                }
            }
        }
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                final String line = String.format("%s,%s", user.getName(), user.getPassword());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        String password = user.getPassword();
        byte[] salt = generateSalt();
        String saltStr = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashPassword(password.toCharArray(), salt);
        user.setPassword(saltStr + ":" + hashedPassword);
        accounts.put(user.getName(), user);
        this.save();
    }

    @Override
    public boolean authenticate(String username, String password) {
        User user = get(username);
        if (user == null) {
            System.out.println("Authentication failed: user not found.");
            return false;
        }

        String storedPassword = user.getPassword(); // Format: "salt:hashedPassword"
        String[] parts = storedPassword.split(":");
        if (parts.length != 2) {
            throw new RuntimeException("Invalid stored password format.");
        }

        byte[] salt = Base64.getDecoder().decode(parts[0]);
        String storedHash = parts[1];
        String inputHash = hashPassword(password.toCharArray(), salt);

        boolean isAuthenticated = inputHash.equals(storedHash);
        if (isAuthenticated) {
            System.out.println("Authentication successful for user: " + username);
        } else {
            System.out.println("Authentication failed: incorrect password.");
        }
        return isAuthenticated;
    }

    @Override
    public User get(String username) {
        User user = accounts.get(username);
        if (user == null) {
            System.out.println("User not found: " + username);
        } else {
            System.out.println("User found: " + username);
        }
        return user;
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    @Override
    public void changePassword(User user) {
        // Replace the User object in the map
        accounts.put(user.getName(), user);
        save();
    }

    @Override
    public void setCurrentUsername(String username, boolean isPlayer1) {
        this.currentUsername = username; // Set the current username
    }

    @Override
    public String getCurrentUsername(boolean isPlayer1) {
        return this.currentUsername; // Return the current username
    }

    @Override
    public void logout(boolean isPlayer1) {
        this.currentUsername = null; // Clear the current username
    }

    private String hashPassword(final char[] password, final byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error while hashing a password: " + e.getMessage(), e);
        }
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
