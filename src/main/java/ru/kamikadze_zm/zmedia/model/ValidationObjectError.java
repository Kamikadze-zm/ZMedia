package ru.kamikadze_zm.zmedia.model;

import org.springframework.validation.ObjectError;

public class ValidationObjectError {

    private final String object;
    private final String message;

    public ValidationObjectError(ObjectError oe) {
        this(oe.getObjectName(), oe.getDefaultMessage());
    }

    public ValidationObjectError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }
}
