package ru.kamikadze_zm.zmedia.jwtauth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserCredentials {

    private final String email;
    private final String password;

    @JsonCreator
    public UserCredentials(@JsonProperty("login") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
