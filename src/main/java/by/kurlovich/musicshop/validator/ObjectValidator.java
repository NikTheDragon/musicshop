package by.kurlovich.musicshop.validator;

import by.kurlovich.musicshop.entity.User;

import java.util.Map;

public class ObjectValidator {
    public static boolean validateUser(Map<String, String> messageMap, User user) {
        FieldValidator validator = new FieldValidator();
        int errors = 0;
        String message;

        message = validator.validate(user.getName());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("nameMessage", message);
            errors++;
        }

        message = validator.validate(user.getSurname());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("surnameMessage", message);
            errors++;
        }

        message = validator.validate(user.getLogin());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("loginMessage", message);
            errors++;
        }

        message = validator.validate(user.getPassword());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("passwordMessage", message);
            errors++;
        }

        message = validator.validate(user.getEmail());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("emailMessage", message);
            errors++;
        }

        return (errors == 0);
    }
}
