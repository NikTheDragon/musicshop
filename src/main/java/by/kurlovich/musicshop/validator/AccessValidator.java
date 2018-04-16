package by.kurlovich.musicshop.validator;

import java.util.List;
import java.util.function.BiPredicate;

public class AccessValidator {

    public boolean validate(List<String> accessRoles, String userRole) {
        return accessRoles.stream().allMatch(s -> s.equals(userRole));
    }
}
