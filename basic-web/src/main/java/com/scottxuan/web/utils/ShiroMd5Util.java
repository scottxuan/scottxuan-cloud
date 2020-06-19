package com.scottxuan.web.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author : scottxuan
 */
public class ShiroMd5Util {
    private static final String MD5 = "MD5";
    private static final Integer TIMES = 64;
    private static final Integer SALF_LENGTH = 6;
    private static final Integer PASSWORD_LENGTH = 8;

    public static String encry(String password, String salf) {
        ByteSource salt = ByteSource.Util.bytes(salf);
        SimpleHash hash = new SimpleHash(MD5, password, salt, TIMES);
        return hash.toString();
    }

    public static String getSalf() {
        StringBuilder salf = new StringBuilder(SALF_LENGTH);
        for (int i = 0; i < SALF_LENGTH; i++) {
            int inVal = (int) (Math.random() * 26 + 97);
            salf.append((char) inVal);
        }
        return salf.toString();
    }

    public static String getPassword() {
        StringBuilder salf = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int inVal = (int) (Math.random() * 26 + 97);
            salf.append((char) inVal);
        }
        return salf.toString();
    }

    public static String md5Entry(String str, int times) {
        SimpleHash hash = null;
        for (int i = 0; i < times; i++) {
            hash = new SimpleHash(MD5, str, null, times);
        }
        return hash == null ? null : hash.toString();
    }

}
