package ru.kamikadze_zm.zmedia.service;

import ru.kamikadze_zm.zmedia.exception.InvalidVerificationCodeException;
import ru.kamikadze_zm.zmedia.model.entity.VerificationCode;
import ru.kamikadze_zm.zmedia.model.entity.VerificationCode.CodeType;

public interface VerificationCodeService {

    public VerificationCode getValidCode(String code) throws InvalidVerificationCodeException;

    public VerificationCode create(String email, CodeType codeType);

    public void save(VerificationCode code);

    public VerificationCode createAndSave(String email, CodeType codeType);

    public boolean validate(VerificationCode code);
    
    public void delete(VerificationCode code);
}
