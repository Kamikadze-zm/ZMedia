package ru.kamikadze_zm.zmedia.jwtauth;

public enum TokenClaims {
    NAME("name"),
    ROLE("role"),
    AVATAR("avatar");

    private final String key;

    private TokenClaims(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
