package com.foobar.pwdmgr;

import sun.jvm.hotspot.runtime.Bytes;

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
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(userName.getBytes("UTF-8"));
            md.update(password.getBytes("UTF-8"));
            return new String(md.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.exit(255);
        }
        return null;
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
