package jtw.nice.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jtw.nice.entity.User;
import jtw.nice.entity.dto.PasswordResetRequest;
import jtw.nice.entity.dto.UserAuth;
import jtw.nice.entity.dto.UserToken;
import jtw.nice.response.ApiResponse;
import jtw.nice.service.UserService;
import jtw.nice.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping
@Validated
public class UserController {

    private final UserService userService;
    private final UserHolder userHolder;

    @Autowired
    public UserController(UserService userService, UserHolder userHolder) {
        this.userService = userService;
        this.userHolder = userHolder;
    }

    /**
     * Register a new user.
     * @param userAuth the user object containing registration details.
     * @return the ID of the newly registered user.
     */
    @Operation(summary = "Register a new user", description = "Create a new user account")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Integer>> registerUser(@RequestBody UserAuth userAuth) {
        int userId = userService.registerUser(userAuth.getUsername(), userAuth.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully", userId));
    }

    /**
     * Authenticate user login.
     * @param userAuth the username and password provided by the client.
     * @return the authenticated user object.
     */
    @Operation(summary = "Authenticate user login", description = "Validate user credentials and log in")
    @PostMapping("/guest_login")
    public ResponseEntity<ApiResponse<UserToken>> loginUser(@RequestBody UserAuth userAuth, HttpServletResponse httpServletResponse) {
        String token = userService.loginUser(userAuth.getUsername(), userAuth.getPassword());
        httpServletResponse.setHeader("Authorization", token);
        System.out.println(token);
        return ResponseEntity.ok(ApiResponse.success("Login successful", new UserToken(token)));
    }

    /**
     * Authenticate user login.
     * @param userAuth the username and password provided by the client.
     * @return the authenticated user object.
     */
    @Operation(summary = "Authenticate user login", description = "Validate user credentials and log in")
    @PostMapping("/employee_login")
    public ResponseEntity<ApiResponse<UserToken>> loginAdmin(@RequestBody UserAuth userAuth) {
        System.out.println(userAuth.getUsername());
        System.out.println(userAuth.getPassword());
        String token = userService.loginUser(userAuth.getUsername(), userAuth.getPassword());
        System.out.println(token);
        return ResponseEntity.ok(ApiResponse.success("Login successful", new UserToken(token)));
    }



    /**
     * Update user information.
     * @param user the user object containing updated information.
     * @return success message.
     */
    @Operation(summary = "Update user information", description = "Modify user details")
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateUser(@RequestBody @Valid User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully"));
    }

    /**
     * Delete a user by ID.
     * @param userId the ID of the user to be deleted.
     * @return success message.
     */
    @Operation(summary = "Delete a user", description = "Remove a user account by ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }

    /**
     * Retrieve a user by ID.
     * @param userId the ID of the user to retrieve.
     * @return the user object if found.
     */
    @Operation(summary = "Retrieve a user by ID", description = "Fetch user details by their ID")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
    }

    /**
     * Retrieve all users.
     * @return a list of all users in the system.
     */
    @Operation(summary = "Retrieve all users", description = "Fetch all user accounts")
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    /**
     * Reset password for the logged-in user.
     *
     * @param requestBody the password reset request body
     * @return success or error message
     */
    @Operation(summary = "Reset password", description = "Allow logged-in users to reset their password")
    @PostMapping("/reset_password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody @Valid PasswordResetRequest requestBody) {
        // Get the logged-in user's ID from UserHolder
        User currentUser = userHolder.getUser();
        if (currentUser == null) {
            throw new IllegalStateException("No logged-in user found. Token validation failed.");
        }

        // Reset password using the user ID
        userService.resetPassword(currentUser.getUserId(), requestBody.getOldPassword(), requestBody.getNewPassword());

        return ResponseEntity.ok(ApiResponse.success("Password reset successfully"));
    }
}
