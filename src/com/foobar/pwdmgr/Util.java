package com.foobar.pwdmgr;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Created by alisaarnold on 3/29/17.
 */
public class Util {

    public static Supplier<MessageDigest> messageDigestSupplier = () ->{
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(57474);
        }
        return md;
    };

    public static BiConsumer<MessageDigest, String> hashFunction = (messageDigest, value) ->{
        try {
            messageDigest.update(value.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(9483);
        }
    };
}
