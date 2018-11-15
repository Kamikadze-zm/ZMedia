package ru.kamikadze_zm.zmedia.repository;

import ru.kamikadze_zm.zmedia.model.entity.VerificationCode;
import ru.kamikadze_zm.zmedia.model.entity.VerificationCodePK;

public interface VerificationCodeRepository extends Repository<VerificationCode, VerificationCodePK> {

    public VerificationCode findByCode(String code);
}
