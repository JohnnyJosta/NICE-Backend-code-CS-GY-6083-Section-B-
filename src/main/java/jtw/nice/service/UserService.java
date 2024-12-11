package jtw.nice.service;

import jtw.nice.entity.User;

import java.util.List;

public interface UserService {

    /**
     * Register a new user.
     * @param username the username provided by the client.
     * @param password the raw password provided by the client.
     * @return the ID of the newly registered user.
     */
    int registerUser(String username, String password);

    /**
     * Authenticate user login.
     * @param username the username provided by the client.
     * @param password the raw password provided by the client.
     * @return the authenticated user object.
     */
    String loginUser(String username, String password);

    /**
     * Update user information.
     * @param user the user object containing updated details.
     */
    void updateUser(User user);

    /**
     * Delete a user by user ID.
     * @param userId the ID of the user to be deleted.
     */
    void deleteUser(int userId);

    /**
     * Retrieve a user by user ID.
     * @param userId the ID of the user to be retrieved.
     * @return the user object if found.
     */
    User getUserById(int userId);

    /**
     * Retrieve all users in the system.
     * @return a list of all users.
     */
    List<User> getAllUsers();

    /**
     * Reset the user's password.
     *
     * @param userId      the user ID
     * @param oldPassword the old password
     * @param newPassword the new password
     */
    void resetPassword(int userId, String oldPassword, String newPassword);
}
