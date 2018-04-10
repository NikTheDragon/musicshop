package by.kurlovich.musicshop.validator;

public class AccessValidator {
    public boolean validate(String[] accessRoles, String userRole) {
        boolean result = false;
        for (String role : accessRoles) {
            if (role.equals(userRole)) {
                result = true;
            }
        }
        return result;
    }
}
