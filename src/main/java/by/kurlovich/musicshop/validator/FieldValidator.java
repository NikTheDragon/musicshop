package by.kurlovich.musicshop.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidator {
    public static String validateTextField(String[] fieldText) {
        String commonValidatorResult = common(fieldText[0]);

        if (!Boolean.parseBoolean(commonValidatorResult)) {
            return commonValidatorResult;
        }

        String textOnlyRegex = "^[a-zA-Zа-яА-Я]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText[0]);

        if (!matcher.matches()) {
            return "notText";
        }

        return "true";
    }

    public static String validateSentenceField(String[] fieldText) {
        String commonValidatorResult = common(fieldText[0]);

        if (!Boolean.parseBoolean(commonValidatorResult)) {
            return commonValidatorResult;
        }

        String textOnlyRegex = "^[a-zA-Zа-яА-Я\\s\\,\\.\\d]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText[0]);

        if (!matcher.matches()) {
            return "notText";
        }

        return "true";
    }

    public static String validateSearchSentenceField(String[] fieldText) {
        if (fieldText == null) {
            return "true";
        }

        String textOnlyRegex = "^[a-zA-Zа-яА-Я\\s\\,\\.\\d]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText[0]);

        if (!matcher.matches()) {
            return "notText";
        }

        return "true";
    }

    public static String validateLogPasField(String[] fieldText) {
        String commonValidatorResult = common(fieldText[0]);

        if (!Boolean.parseBoolean(commonValidatorResult)) {
            return commonValidatorResult;
        }

        String textOnlyRegex = "^[a-zA-Zа-яА-Я_0-9]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText[0]);

        if (!matcher.matches()) {
            return "notLogPas";
        }

        return "true";
    }

    public static String validateEmailField(String[] fieldText) {
        String commonValidatorResult = common(fieldText[0]);

        if (!Boolean.parseBoolean(commonValidatorResult)) {
            return commonValidatorResult;
        }

        String textOnlyRegex = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText[0]);

        if (!matcher.matches()) {
            return "notMail";
        }

        return "true";
    }

    public static String validateDigitField(String[] fieldText) {
        if (fieldText[0] == null || fieldText[0].isEmpty()) {
            return "null";
        }

        String textOnlyRegex = "^[\\d]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText[0]);

        if (!matcher.matches()) {
            return "notDigit";
        }

        return "true";
    }

    public static String validateSearchDigitField(String[] fieldText) {
        if (fieldText == null) {
            return "true";
        }

        String textOnlyRegex = "^[\\d]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText[0]);

        if (!matcher.matches()) {
            return "notDigit";
        }

        return "true";
    }

    public static String validateFloatDigitField(String[] fieldText) {
        if (fieldText[0] == null || fieldText[0].isEmpty()) {
            return "null";
        }

        String textOnlyRegex = "^[\\d]+\\.[\\d]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText[0]);

        if (!matcher.matches()) {
            return "notFloat";
        }

        return "true";
    }

    private static String common(String fieldText) {
        if (fieldText == null || fieldText.isEmpty()) {
            return "null";
        }

        if (fieldText.length() < 3 || fieldText.length() > 25) {
            return "length";
        }

        return "true";
    }
}

