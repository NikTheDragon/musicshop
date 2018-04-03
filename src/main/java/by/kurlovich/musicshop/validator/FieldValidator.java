package by.kurlovich.musicshop.validator;

public class FieldValidator {
    public String validate(String field) {
        if (field.length() == 0 || field == null) {
            return "null";
        }
        return "true";
    }
}
