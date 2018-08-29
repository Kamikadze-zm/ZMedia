package ru.kamikadze_zm.zmedia.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.validation.FieldError;

public class ValidationFieldsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Validation fields exception";

    private final List<FieldError> fieldsErrors;

    public ValidationFieldsException(FieldError fieldError) {
        super(DEFAULT_MESSAGE);
        this.fieldsErrors = new ArrayList<>(1);
        this.fieldsErrors.add(fieldError);
    }

    public ValidationFieldsException(List<FieldError> fieldErrors) {
        super(DEFAULT_MESSAGE);
        this.fieldsErrors = fieldErrors;
    }

    public List<FieldError> getFieldsErrors() {
        if (fieldsErrors != null) {
            return fieldsErrors;
        }
        return Collections.emptyList();
    }

    public static FieldError createError(String objectName, String field, Object rejectedValue, String defaultMessage) {
        return new FieldError(objectName, field, rejectedValue, false, null, null, defaultMessage);
    }
}
