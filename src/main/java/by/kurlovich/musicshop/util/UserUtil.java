package by.kurlovich.musicshop.util;

import by.kurlovich.musicshop.entity.User;

public class UserUtil {
    public static String getId(User currentUser) {
        if (currentUser != null) {
            return currentUser.getId();
        } else {
            return "0";
        }
    }
}
