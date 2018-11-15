package ru.kamikadze_zm.zmedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kamikadze_zm.zmedia.exception.InvalidVerificationCodeException;
import ru.kamikadze_zm.zmedia.jwtauth.TokenService;
import ru.kamikadze_zm.zmedia.model.dto.NewPasswordDTO;
import ru.kamikadze_zm.zmedia.model.dto.RegistrationDTO;
import ru.kamikadze_zm.zmedia.model.entity.User;
import ru.kamikadze_zm.zmedia.model.entity.VerificationCode;
import ru.kamikadze_zm.zmedia.service.AccountService;
import ru.kamikadze_zm.zmedia.service.EmailService;
import ru.kamikadze_zm.zmedia.service.UserService;
import ru.kamikadze_zm.zmedia.service.VerificationCodeService;
import ru.kamikadze_zm.zmedia.util.Constants;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

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
        User u = new User(r.getEmail(), r.getName(), passwordEncoder.encode(r.getPassword()), Constants.PATH_DEFAULT_AVATAR);
        userService.add(u);
        sendEmailConfirmationMessage(u);
    }

    @Override
    public void sendEmailConfirmationMessage(User user) {
        if (!user.isConfirmed()) {
            emailService.sendEmailConfirmationMessage(user);
        }
    }

    @Override
    @Transactional
    public void confirmEmail(String code) throws InvalidVerificationCodeException {
        VerificationCode verificationCode = verificationCodeService.getValidCode(code);
        User u = userService.getByEmail(verificationCode.getEmail());
        if (!u.isConfirmed()) {
            u.setConfirmed(Boolean.TRUE);
            userService.update(u);
        }
        verificationCodeService.delete(verificationCode);
    }

    @Override
    public boolean sendPasswordRestoringMessage(String email) {
        User u = userService.getByEmail(email);
        if (u == null || !u.isConfirmed()) {
            return false;
        }
        emailService.sendPasswordRestoringMessage(u);
        return true;
    }

    @Override
    @Transactional
    public void restorePassword(String code, NewPasswordDTO passwordDTO) throws InvalidVerificationCodeException {
        VerificationCode verificationCode = verificationCodeService.getValidCode(code);
        User u = userService.getByEmail(verificationCode.getEmail());
        u.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userService.update(u);
        verificationCodeService.delete(verificationCode);
    }
}
