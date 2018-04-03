package by.kurlovich.musicshop.validator;

public class FieldValidator {
    public String validate(String field) {
        if (field == null || field.isEmpty()) {
            return "null";
        }
        return "true";
    }
}
