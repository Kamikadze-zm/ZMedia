package ru.kamikadze_zm.zmedia.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Md5Util {

    private static final Logger LOG = LogManager.getLogger(Md5Util.class);

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMd5String(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte[] digest = md.digest();
            return md5DigestToString(digest);
        } catch (NoSuchAlgorithmException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private static String md5DigestToString(byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return new String(out);
    }
}
