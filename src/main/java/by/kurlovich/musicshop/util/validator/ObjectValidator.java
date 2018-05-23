package by.kurlovich.musicshop.util.validator;

import java.util.HashMap;
import java.util.Map;

public class ObjectValidator {
    public static Map<String, String> validateUser(Map<String, String[]> requestMap) {
        Map<String, String> validationResults = new HashMap<>();
        String isPassedValidation = "true";

        validationResults.put("nameResult", FieldValidator.validateTextField(requestMap.get("submit_name")));
        validationResults.put("surnameResult", FieldValidator.validateTextField(requestMap.get("submit_surname")));
        validationResults.put("loginResult", FieldValidator.validateLogPasField(requestMap.get("submit_login")));
        validationResults.put("passwordResult", FieldValidator.validateTextField(requestMap.get("submit_password")));
        validationResults.put("emailResult", FieldValidator.validateEmailField(requestMap.get("submit_email")));
        validationResults.put("roleResult", FieldValidator.validateTextField(requestMap.get("submit_role")));
        validationResults.put("statusResult", FieldValidator.validateTextField(requestMap.get("submit_status")));
        validationResults.put("pointsResult", FieldValidator.validateDigitField(requestMap.get("submit_points")));

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

        validationResults.put("nameResult", FieldValidator.validateSentenceField(requestMap.get("submit_name")));
        validationResults.put("authorResult", FieldValidator.validateSentenceField(requestMap.get("submit_author")));
        validationResults.put("genreResult", FieldValidator.validateTextField(requestMap.get("submit_genre")));
        validationResults.put("yearResult", FieldValidator.validateDigitField(requestMap.get("submit_year")));
        validationResults.put("lengthResult", FieldValidator.validateFloatDigitField(requestMap.get("submit_length")));

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

        validationResults.put("nameResult", FieldValidator.validateTextField(requestMap.get("submit_name")));

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

        validationResults.put("nameResult", FieldValidator.validateSentenceField(requestMap.get("submit_name")));
        validationResults.put("genreResult", FieldValidator.validateTextField(requestMap.get("submit_genre")));
        validationResults.put("typeResult", FieldValidator.validateTextField(requestMap.get("submit_type")));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }

    public static Map<String, String> validateAlbum(Map<String, String[]> requestMap) {
        Map<String, String> validationResults = new HashMap<>();
        String isPassedValidation = "true";

        validationResults.put("nameResult", FieldValidator.validateSentenceField(requestMap.get("submit_name")));
        validationResults.put("authorResult", FieldValidator.validateSentenceField(requestMap.get("submit_author")));
        validationResults.put("genreResult", FieldValidator.validateTextField(requestMap.get("submit_genre")));
        validationResults.put("yearResult", FieldValidator.validateDigitField(requestMap.get("submit_year")));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }

    public static Map<String, String> validateMix(Map<String, String[]> requestMap) {
        Map<String, String> validationResults = new HashMap<>();
        String isPassedValidation = "true";

        validationResults.put("nameResult", FieldValidator.validateSentenceField(requestMap.get("submit_name")));
        validationResults.put("genreResult", FieldValidator.validateTextField(requestMap.get("submit_genre")));
        validationResults.put("yearResult", FieldValidator.validateDigitField(requestMap.get("submit_year")));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }

    public static Map<String, String> validateSearch(Map<String, String[]> requestMap) {
        Map<String, String> validationResults = new HashMap<>();
        String isPassedValidation = "true";

        validationResults.put("nameResult", FieldValidator.validateSearchSentenceField(requestMap.get("search_name")));
        validationResults.put("authorResult", FieldValidator.validateSearchSentenceField(requestMap.get("search_author")));
        validationResults.put("genreResult", FieldValidator.validateSearchSentenceField(requestMap.get("search_genre")));
        validationResults.put("yearResult", FieldValidator.validateSearchDigitField(requestMap.get("search_year")));
        validationResults.put("typeResult", FieldValidator.validateSearchSentenceField(requestMap.get("search_type")));

        for (Map.Entry<String, String> results : validationResults.entrySet()) {
            if (!Boolean.parseBoolean(results.getValue())) {
                isPassedValidation = "false";
            }
        }

        if (validationResults.isEmpty()) {
            isPassedValidation = "false";
        }

        validationResults.put("isPassedValidation", isPassedValidation);

        return validationResults;
    }
}
