package com.maninder.marvelcomics.data.remote.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Maninder on 14/11/16.
 */

public class Utils {
    /**
     * Creates an md5 hash string from the string input
     *
     * @param value String to be hashed
     * @return
     */
    public static final String md5(final String value) {
        try {
            MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5Encoder.digest(value.getBytes());

            StringBuilder md5 = new StringBuilder();
            for (int i = 0; i < md5Bytes.length; ++i) {
                md5.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
