package jtw.nice.service.impl;

import jtw.nice.entity.Group;
import jtw.nice.entity.User;
import jtw.nice.mapper.GroupMapper;
import jtw.nice.mapper.UserMapper;
import jtw.nice.service.UserService;
import jtw.nice.utils.PasswordUtils;
import jtw.nice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final GroupMapper groupMapper;
    private final TokenUtils tokenUtils;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, GroupMapper groupMapper, TokenUtils tokenUtils) {
        this.userMapper = userMapper;
        this.groupMapper = groupMapper;
        this.tokenUtils = tokenUtils;
    }

    /**
     * Register a new user with encrypted password.
     * @param username the username of the new user.
     * @param password the password of the new user.
     * @return the ID of the newly registered user.
     */
    @Override
    @Transactional
    public int registerUser(String username, String password) {
        System.out.println("enter service");
        // Check if the username already exists
        if (userMapper.getUserByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User user = new User();
        if ("admin".equalsIgnoreCase(username)) {
            // If username is "admin", assign role ADMIN
            user.setRole("ADMIN");
            user.setUsername(username);
            user.setPassword(PasswordUtils.hashPassword(password));
            userMapper.addUser(user); // Directly insert the user
        } else {
            // For other users, assign role USER and create a group
            user.setRole("USER");
            user.setUsername(username);
            user.setPassword(PasswordUtils.hashPassword(password));

            // Create a new group for the user
            Group group = new Group();
            group.setGroupName(username); // Group name uses the username
            group.setPassengerNumber(0); // Initially, no members
            group.setTripId(null); // Optional: Handle trip association logic if needed

            groupMapper.addGroup(group); // Insert the group and get the generated group ID

            // Assign the created group ID to the user
            user.setGroupId(group.getGroupId());
            userMapper.addUser(user); // Insert the user into the database
        }

        return user.getUserId(); // Return the generated user ID
    }


    /**
     * Authenticate user login by validating username and password.
     * @param username the username provided by the client.
     * @param password the raw password provided by the client.
     * @return the authenticated user object.
     */
    @Override
    public String loginUser(String username, String password) {
        System.out.println("Enter service");
        User user = userMapper.getUserByUsername(username);
        System.out.println(user == null);
        if (user == null || !PasswordUtils.checkPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect username or password.");
        }
        System.out.println("Before token");
        return tokenUtils.getToken(user.getUserId(), user.getRole(), user.getGroupId());
    }

    /**
     * Update user information.
     * @param user the user object containing updated details.
     */
    @Override
    public void updateUser(User user) {
        int rows = userMapper.updateUser(user);
        if (rows == 0) {
            throw new RuntimeException("Failed to update user with ID: " + user.getUserId());
        }
    }

    /**
     * Delete a user by user ID.
     * @param userId the ID of the user to be deleted.
     */
    @Override
    public void deleteUser(int userId) {
        int rows = userMapper.deleteUser(userId);
        if (rows == 0) {
            throw new RuntimeException("Failed to delete user with ID: " + userId);
        }
    }

    /**
     * Retrieve a user by user ID.
     * @param userId the ID of the user to be retrieved.
     * @return the user object if found.
     */
    @Override
    public User getUserById(int userId) {
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("User not found for ID: " + userId);
        }
        return user;
    }

    /**
     * Retrieve all users in the system.
     * @return a list of all users.
     */
    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Override
    public void resetPassword(int userId, String oldPassword, String newPassword) {
        // Retrieve user by ID
        User user = userMapper.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        // Verify old password
        if (!PasswordUtils.checkPassword(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // Validate new password (e.g., complexity, length)
//        if (newPassword.length() < 8) {
//            throw new IllegalArgumentException("New password must be at least 8 characters long");
//        }

        // Hash new password
        String hashedPassword = PasswordUtils.hashPassword(newPassword);

        // Update password in database
        int rows = userMapper.updatePassword(userId, hashedPassword);
        if (rows == 0) {
            throw new RuntimeException("Failed to update password");
        }
    }
}
