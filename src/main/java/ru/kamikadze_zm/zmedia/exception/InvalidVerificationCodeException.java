package ru.kamikadze_zm.zmedia.exception;

public class InvalidVerificationCodeException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Недействительный код подтверждения";

    public InvalidVerificationCodeException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidVerificationCodeException(String message) {
        super(message);
    }
}
