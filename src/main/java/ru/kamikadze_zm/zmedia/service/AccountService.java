package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.model.dto.RegistrationDTO;

public interface AccountService {
    
    public String refreshAccessToken(String refreshToken);

    public boolean existEmail(String email);

    public boolean existName(String name);

    public void registerUser(RegistrationDTO registration);
}
