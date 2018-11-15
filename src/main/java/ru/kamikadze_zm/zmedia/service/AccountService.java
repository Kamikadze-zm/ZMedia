package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.exception.InvalidVerificationCodeException;
import ru.kamikadze_zm.zmedia.model.dto.NewPasswordDTO;
import ru.kamikadze_zm.zmedia.model.dto.RegistrationDTO;
import ru.kamikadze_zm.zmedia.model.entity.User;

public interface AccountService {

    public String refreshAccessToken(String refreshToken);

    public boolean existEmail(String email);

    public boolean existName(String name);

    public void registerUser(RegistrationDTO registration);

    public void sendEmailConfirmationMessage(User user);

    public void confirmEmail(String code) throws InvalidVerificationCodeException;

    /**
     *
     * @param email email пользователя
     * @return {@code false} если пользователь не найден или email не подтвержден, иначе {@code true}
     */
    public boolean sendPasswordRestoringMessage(String email);

    public void restorePassword(String code, NewPasswordDTO passwordDTO) throws InvalidVerificationCodeException;
}
