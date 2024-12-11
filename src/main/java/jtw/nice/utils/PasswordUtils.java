package jtw.nice.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    /**
     * Hash a plaintext password with BCrypt.
     * @param plainTextPassword the plaintext password to hash.
     * @return the hashed password.
     */
    public static String hashPassword(String plainTextPassword) {
        // Adjust cost factor if needed (default is 10)
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));
    }

    /**
     * Check if a plaintext password matches a hashed password.
     * @param plainTextPassword the plaintext password to check.
     * @param hashedPassword the hashed password to compare against.
     * @return true if the password matches, false otherwise.
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
