package by.kurlovich.musicshop.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidator {
    public static String validateTextField(String fieldText) {
        String textOnlyRegex = "[a-zA-Zа-яА-Я]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText);

        String commonValidatorResult = common(fieldText);

        if (!Boolean.parseBoolean(commonValidatorResult)) {
            return commonValidatorResult;
        }

        if (!matcher.matches()) {
            return "notText";
        }

        return "true";
    }

    public static String validateLogPasField(String fieldText) {
        String textOnlyRegex = "^[a-zA-Zа-яА-Я_0-9]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText);

        String commonValidatorResult = common(fieldText);

        if (!Boolean.parseBoolean(commonValidatorResult)) {
            return commonValidatorResult;
        }

        if (!matcher.matches()) {
            return "notLogPas";
        }

        return "true";
    }

    public static String validateEmail(String fieldText) {
        String textOnlyRegex = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText);

        String commonValidatorResult = common(fieldText);

        if (!Boolean.parseBoolean(commonValidatorResult)) {
            return commonValidatorResult;
        }

        if (!matcher.matches()) {
            return "notMail";
        }

        return "true";
    }

    public static String validateDigit(String fieldText) {
        String textOnlyRegex = "^[\\d]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText);

        if (fieldText == null || fieldText.isEmpty()) {
            return "null";
        }

        if (!matcher.matches()) {
            return "notDigit";
        }

        return "true";
    }

    private static String common (String fieldText) {
        if (fieldText == null || fieldText.isEmpty()) {
            return "null";
        }

        if (fieldText.length() < 3 || fieldText.length() > 15) {
            return "length";
        }

        return "true";
    }
}

