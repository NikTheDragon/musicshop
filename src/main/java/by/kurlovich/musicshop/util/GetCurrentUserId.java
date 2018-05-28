package by.kurlovich.musicshop.util;

import by.kurlovich.musicshop.entity.User;

public class GetCurrentUserId {
    public static String get(User currentUser) {
        if (currentUser != null) {
            return currentUser.getId();
        } else {
            return "0";
        }
    }
}
