package by.kurlovich.musicshop.validator;

import by.kurlovich.musicshop.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ObjectValidator {
    public static boolean validateUser2(Map<String, String> messageMap, User user) {
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

        message = FieldValidator.validateEmail(user.getEmail());
        if (!Boolean.parseBoolean(message)) {
            messageMap.put("emailMessage", message);
            errors++;
        }

        return (errors == 0);
    }

    public static boolean validateUser(HttpServletRequest request) {
        Map<String, String> validationResults = new HashMap<>();
        boolean isPassedValidation = true;

        validationResults.put("nameResult", FieldValidator.validateTextField(request.getParameter("submit_name")));
        validationResults.put("surnameResult", FieldValidator.validateTextField(request.getParameter("submit_surname")));
        validationResults.put("loginResult", FieldValidator.validateLogPasField(request.getParameter("submit_login")));
        validationResults.put("passwordResult", FieldValidator.validateTextField(request.getParameter("submit_password")));
        validationResults.put("emailResult", FieldValidator.validateEmail(request.getParameter("submit_email")));
        validationResults.put("roleResult", FieldValidator.validateTextField(request.getParameter("submit_role")));
        validationResults.put("statusResult", FieldValidator.validateTextField(request.getParameter("submit_status")));
        validationResults.put("pointsResult", FieldValidator.validateDigit(request.getParameter("submit_points")));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = false;
            }
            request.setAttribute(results.getKey(), results.getValue());
        }

        return isPassedValidation;
    }
}
