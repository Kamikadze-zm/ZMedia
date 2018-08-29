package ru.kamikadze_zm.zmedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kamikadze_zm.zmedia.jwtauth.TokenService;
import ru.kamikadze_zm.zmedia.model.dto.RegistrationDTO;
import ru.kamikadze_zm.zmedia.model.entity.User;
import ru.kamikadze_zm.zmedia.service.AccountService;
import ru.kamikadze_zm.zmedia.service.UserService;
import ru.kamikadze_zm.zmedia.util.Constants;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public String refreshAccessToken(String refreshToken) {
        return tokenService.refreshAccessToken(refreshToken);
    }

    @Override
    public boolean existEmail(String email) {
        return userService.emailExist(email);
    }

    @Override
    public boolean existName(String name) {
        return userService.nameExist(name);
    }

    @Override
    public void registerUser(RegistrationDTO r) {
        userService.add(new User(r.getEmail(), r.getName(), passwordEncoder.encode(r.getPassword()), Constants.PATH_DEFAULT_AVATAR));
    }
}
