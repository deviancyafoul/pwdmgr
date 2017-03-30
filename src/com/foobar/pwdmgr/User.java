package com.foobar.pwdmgr;

import sun.jvm.hotspot.runtime.Bytes;
import sun.plugin2.message.Message;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by alisaarnold on 2/9/17.
 */
public class User {
    private final String userName;
    private String password;
    private int id;
    private static int count;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = hashPassword(userName, password);
    }

    public User(int id, String userName, String password){
        this(userName, password);
        this.id = id;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getID() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setID(int id) {
        this.id = id;
    }


    static String hashPassword(String userName, String password){
        MessageDigest md = Util.messageDigestSupplier.get();
        Util.hashFunction.accept(md,userName);
        Util.hashFunction.accept(md,password);
        return new String(md.digest());
    }

    public static boolean isPasswordValid(String userName, String password, String candidatePassword){
        return hashPassword(userName, password).equals(candidatePassword);


    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
