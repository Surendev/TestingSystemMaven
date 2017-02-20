package com.application.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Suro Smith on 2/20/2017
 */
public class SecurityUtil {

    public static final String justString = "adminchik";

    public static String encrypt(String plainText) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(plainText.getBytes());
        return new String(digest.digest());
    }


}
