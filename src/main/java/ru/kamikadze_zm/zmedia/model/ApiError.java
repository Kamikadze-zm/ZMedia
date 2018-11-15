package ru.kamikadze_zm.zmedia.model;

public class ApiError {

    private final int errorCode;
    private final String message;
    private final String details;

    public ApiError(int errorCode, String message, String details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public ApiError(ErrorCode errorCode, String message, String details) {
        this(errorCode.getCode(), message, details);
    }

    public ApiError(ErrorCode errorCode, String message) {
        this(errorCode.getCode(), message, message);
    }

    public ApiError(int errorCode, Throwable throwable) {
        this(errorCode, throwable.getLocalizedMessage(), throwable.getMessage());
    }

    public ApiError(ErrorCode errorCode, Throwable throwable) {
        this(errorCode.getCode(), throwable);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public static enum ErrorCode {
        MESSAGE(0),
        VALIDATION(1),
        INVALID_VERIFICATION_CODE(2),
        OTHER(-1);

        private final int code;

        private ErrorCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
