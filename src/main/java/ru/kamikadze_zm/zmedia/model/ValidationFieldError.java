package ru.kamikadze_zm.zmedia.model;

import org.springframework.validation.FieldError;

public class ValidationFieldError extends ValidationObjectError {

    private final String field;
    private final Object rejectedValue;

    public ValidationFieldError(FieldError fe) {
        super(fe);
        this.field = fe.getField();
        this.rejectedValue = fe.getRejectedValue();
    }

    public ValidationFieldError(String object, String field, Object rejectedValue, String message) {
        super(object, message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }
}
