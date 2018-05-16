package by.kurlovich.musicshop.validator;

import java.util.HashMap;
import java.util.Map;

public class ObjectValidator {
    public static Map<String, String> validateUser(Map<String, String[]> requestMap) {
        Map<String, String> validationResults = new HashMap<>();
        String isPassedValidation = "true";

        validationResults.put("nameResult", FieldValidator.validateTextField(requestMap.get("submit_name")[0]));
        validationResults.put("surnameResult", FieldValidator.validateTextField(requestMap.get("submit_surname")[0]));
        validationResults.put("loginResult", FieldValidator.validateLogPasField(requestMap.get("submit_login")[0]));
        validationResults.put("passwordResult", FieldValidator.validateTextField(requestMap.get("submit_password")[0]));
        validationResults.put("emailResult", FieldValidator.validateEmail(requestMap.get("submit_email")[0]));
        validationResults.put("roleResult", FieldValidator.validateTextField(requestMap.get("submit_role")[0]));
        validationResults.put("statusResult", FieldValidator.validateTextField(requestMap.get("submit_status")[0]));
        validationResults.put("pointsResult", FieldValidator.validateDigit(requestMap.get("submit_points")[0]));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }
}
