package ru.kamikadze_zm.zmedia.model;

import java.util.List;

public class ValidationError extends ApiError {

    private final List<ValidationObjectError> objectErrors;
    private final List<ValidationFieldError> fieldErrors;

    public ValidationError(String message, String details, List<ValidationObjectError> objectErrors, List<ValidationFieldError> fieldErrors) {
        super(ErrorCode.VALIDATION, message, details);
        this.objectErrors = objectErrors;
        this.fieldErrors = fieldErrors;
    }

    public ValidationError(Throwable throwable, List<ValidationObjectError> objectErrors, List<ValidationFieldError> fieldErrors) {
        super(ErrorCode.VALIDATION, throwable);
        this.objectErrors = objectErrors;
        this.fieldErrors = fieldErrors;
    }

    public List<ValidationObjectError> getObjectErrors() {
        return objectErrors;
    }

    public List<ValidationFieldError> getFieldErrors() {
        return fieldErrors;
    }
}
