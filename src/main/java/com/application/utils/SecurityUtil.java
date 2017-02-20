package com.application.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Suro Smith on 2/20/2017
 */
public class SecurityUtil {

    public static final String justString = "489a19ea13afa52ba751be7e4fdf69af1a2bf2a15845aa744f43ae47ee8cd901";

    public static String encrypt(String plainText) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(plainText.getBytes());
        return new String(digest.digest());
    }


}
