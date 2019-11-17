package ru.kamikadze_zm.zmedia.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MagnetsUtil {

    @Value("${dc.magnet-pattern}")
    private String magnetPattern;

    public String getHash(String magnet) {
        if (!checkMagnet(magnet)) {
            return null;
        }
        return magnet.substring(26, 65);
    }

    public boolean checkMagnet(String magnet) {
        return magnet.startsWith(magnetPattern);
    }
}
