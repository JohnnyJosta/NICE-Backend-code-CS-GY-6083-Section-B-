package jtw.nice.utils;

import jtw.nice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    private static final ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void remove() {
        users.remove();
    }

    public Integer getGroupId() {
        User user = users.get();
        return user != null ? user.getGroupId() : null;
    }
}

