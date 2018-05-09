package by.kurlovich.musicshop.validator;

import by.kurlovich.musicshop.entity.User;

import java.util.Map;

public class ObjectValidator {
    public static boolean validateUser(Map<String, String> messageMap, User user) {
        int errors = 0;
        String message;

        message = FieldValidator.validateTextField(user.getName());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("nameMessage", message);
            errors++;
        }

        message = FieldValidator.validateTextField(user.getSurname());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("surnameMessage", message);
            errors++;
        }

        message = FieldValidator.validateLogPasField(user.getLogin());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("loginMessage", message);
            errors++;
        }

        message = FieldValidator.validateLogPasField(user.getPassword());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("passwordMessage", message);
            errors++;
        }

        message = FieldValidator.validateTextField(user.getEmail());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("emailMessage", message);
            errors++;
        }

        return (errors == 0);
    }
}
