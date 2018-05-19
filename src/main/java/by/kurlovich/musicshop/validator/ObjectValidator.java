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
        validationResults.put("emailResult", FieldValidator.validateEmailField(requestMap.get("submit_email")[0]));
        validationResults.put("roleResult", FieldValidator.validateTextField(requestMap.get("submit_role")[0]));
        validationResults.put("statusResult", FieldValidator.validateTextField(requestMap.get("submit_status")[0]));
        validationResults.put("pointsResult", FieldValidator.validateDigitField(requestMap.get("submit_points")[0]));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }

    public static Map<String, String> validateTrack(Map<String, String[]> requestMap) {
        Map<String, String> validationResults = new HashMap<>();
        String isPassedValidation = "true";

        validationResults.put("nameResult", FieldValidator.validateSentenceField(requestMap.get("submit_name")[0]));
        validationResults.put("authorResult", FieldValidator.validateSentenceField(requestMap.get("submit_author")[0]));
        validationResults.put("genreResult", FieldValidator.validateTextField(requestMap.get("submit_genre")[0]));
        validationResults.put("yearResult", FieldValidator.validateDigitField(requestMap.get("submit_year")[0]));
        validationResults.put("lengthResult", FieldValidator.validateFloatDigitField(requestMap.get("submit_length")[0]));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }

    public static Map<String, String> validateGenre(Map<String, String[]> requestMap) {
        Map<String, String> validationResults = new HashMap<>();
        String isPassedValidation = "true";

        validationResults.put("nameResult", FieldValidator.validateTextField(requestMap.get("submit_name")[0]));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }

    public static Map<String, String> validateAuthor(Map<String, String[]> requestMap) {
        Map<String, String> validationResults = new HashMap<>();
        String isPassedValidation = "true";

        validationResults.put("nameResult", FieldValidator.validateSentenceField(requestMap.get("submit_name")[0]));
        validationResults.put("genreResult", FieldValidator.validateTextField(requestMap.get("submit_genre")[0]));
        validationResults.put("typeResult", FieldValidator.validateTextField(requestMap.get("submit_type")[0]));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }
}
