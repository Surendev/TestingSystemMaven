package com.application.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by Suro Smith on 2/20/2017
 */
public class SecurityUtil {

    public static final String justString = "a4afabe8a097e70dca13b92e5ab00b36ea3711feae56f540f5ab71bc57914eaa";

    public static String encrypt(String plainText) throws UnsupportedEncodingException {
        return DigestUtils.sha256Hex(plainText);
    }


}
