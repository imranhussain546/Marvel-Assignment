package com.oyelabs.marvel.universe.constant;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.System.currentTimeMillis;

public class Constant {

    public static String apikey = "3d09d1ee77f9094d1041019f959c6ee6";
    public static String privatekey = "d3b975be8afebb08d8e1fc51141bda6a3df90f1d";
    public static String ts = String.valueOf(currentTimeMillis());
    public static String ID = "id";
    public static String hash = generateHash(ts, apikey, privatekey);

   static String generateHash(String timestamp, String publicKey, String privateKey) {
        String value = timestamp + privateKey + publicKey;
        MessageDigest md5Encoder = null;
        try {
            md5Encoder = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] md5Bytes = md5Encoder.digest(value.getBytes());

        StringBuilder md5 = new StringBuilder();
        for (int i = 0; i < md5Bytes.length; ++i) {
            md5.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return md5.toString();
    }
}
