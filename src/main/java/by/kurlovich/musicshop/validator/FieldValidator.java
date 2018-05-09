package by.kurlovich.musicshop.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldValidator {
    public static String validateTextField(String fieldText) {
        String textOnlyRegex = "[a-zA-Zа-яА-Я]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText);

        if (fieldText == null || fieldText.isEmpty()) {
            return "null";
        }

        if (!matcher.matches()) {
            return "notText";
        }

        if (fieldText.length() < 3 || fieldText.length() > 15) {
            return "length";
        }

        return "true";
    }

    public static String validateLogPasField(String fieldText) {
        String textOnlyRegex = "^[a-zA-Zа-яА-Я_0-9]*$";
        Pattern pattern = Pattern.compile(textOnlyRegex);
        Matcher matcher = pattern.matcher(fieldText);

        if (fieldText == null || fieldText.isEmpty()) {
            return "null";
        }

        if (!matcher.matches()) {
            return "notLogPas";
        }

        if (fieldText.length() < 3 || fieldText.length() > 15) {
            return "length";
        }

        return "true";
    }
}
