package by.kurlovich.musicshop.validator;

import java.util.List;

public class AccessValidator {

    public static boolean validate(List<String> accessRoles, String userRole) {
        return accessRoles.stream().anyMatch(e -> e.equals(userRole));
    }
}
