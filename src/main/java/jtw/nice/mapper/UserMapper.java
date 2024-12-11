package jtw.nice.mapper;

import jtw.nice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    int addUser(User user);

    int deleteUser(int userId);

    int updateUser(User user);

    User getUserById(int userId);

    List<User> getAllUsers();

    User getUserByUsername(String username);

    User validateUser(@Param("username") String username, @Param("password") String password);

    /**
     * Update user password.
     *
     * @param userId      the user ID
     * @param newPassword the new password
     * @return the number of rows affected
     */
    int updatePassword(@Param("userId") int userId, @Param("newPassword") String newPassword);
}

