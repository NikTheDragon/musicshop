package by.kurlovich.musicshop.validator;

import java.util.List;
import java.util.function.BiPredicate;

public class AccessValidator {

    public static boolean validate(List<String> accessRoles, String userRole) {
        for (String roles : accessRoles) {
            if (roles.equals(userRole)) {
                return true;
            }
        }
        return false;
    }
}
