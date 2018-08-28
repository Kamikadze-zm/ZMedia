package ru.kamikadze_zm.zmedia.jwtauth;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
