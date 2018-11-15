package ru.kamikadze_zm.zmedia.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kamikadze_zm.zmedia.exception.InvalidVerificationCodeException;
import ru.kamikadze_zm.zmedia.model.entity.VerificationCode;
import ru.kamikadze_zm.zmedia.model.entity.VerificationCode.CodeType;
import ru.kamikadze_zm.zmedia.repository.VerificationCodeRepository;
import ru.kamikadze_zm.zmedia.service.VerificationCodeService;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private Environment environment;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    @Transactional(readOnly = true)
    public VerificationCode getValidCode(String code) throws InvalidVerificationCodeException {
        VerificationCode vc = verificationCodeRepository.findByCode(code);
        if (!validate(vc)) {
            throw new InvalidVerificationCodeException();
        }
        return vc;
    }

    @Override
    public VerificationCode create(String email, CodeType codeType) {
        return new VerificationCode(email, codeType, UUID.randomUUID().toString(), new Date());
    }

    @Override
    @Transactional
    public void save(VerificationCode code) {
        Optional<VerificationCode> oevc = verificationCodeRepository.findById(code.getVerificationCodePK())
                .map(vc -> {
                    vc.setCode(code.getCode());
                    vc.setCreatedAt(code.getCreatedAt());
                    return vc;
                });
        verificationCodeRepository.save(oevc.orElse(code));
    }

    @Override
    @Transactional
    public VerificationCode createAndSave(String email, CodeType codeType) {
        VerificationCode vc = create(email, codeType);
        save(vc);
        return vc;
    }

    @Override
    public boolean validate(VerificationCode code) {
        if (code != null) {
            String type = code.getType().name().toLowerCase();
            long expirationTime = environment.getRequiredProperty("verificationCode." + type + ".expirationTime", long.class);
            if (code.getCreatedAt().before(new Date(code.getCreatedAt().getTime() + expirationTime))) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    @Transactional
    public void delete(VerificationCode code) {
        verificationCodeRepository.delete(code);
    }
}
