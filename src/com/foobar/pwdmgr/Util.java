package com.foobar.pwdmgr;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Created by alisaarnold on 3/29/17.
 */
public class Util {

    public static Supplier<MessageDigest> messageDigestSupplier = () ->{
        MessageDigest md    = null;
        try {
            md = MessageDigest.getInstance("SHA-256"); //This implements a specific digest algorithm (SHA-256) to a new MessageDigest object. SHA means Secure Hashing Algorithm.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(57474);
        }
        return md;
    };

    public static BiConsumer<MessageDigest, String> hashFunction = (messageDigest, value) ->{ //This creates a method hashFunction using "burger" notation to update MessageDigest with the byte array from the value parameter. This is called in the hashPassword method in User.
        try {
            messageDigest.update(value.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(9483);
        }
    };




}
