package com.foobar.pwdmgr;

import java.security.MessageDigest;
import org.mindrot.jbcrypt.BCrypt;

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
        //this.password = hashPassword(userName, password);
        this.password = bHashPassword(password);
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


//    static String hashPassword(String userName, String password){
//        MessageDigest md = Util.messageDigestSupplier.get(); //This get the array from messageDigestSupplier that the SHA-256 encryption was set to.
//        Util.hashFunction.accept(md,userName); //This accepts the userName as a salt for the password and hashes it.
//        Util.hashFunction.accept(md,password); //This accepts the password and adds to to the digest to be hashed.
//        return new String(md.digest()); //This combines the username/salt with the hashed password to create a new hashed password in hexidecimal format for security.
//    }

    static String bHashPassword(String password){
       return BCrypt.hashpw(password, BCrypt.gensalt(12));
        //return null;
    }

//    public static boolean isPasswordValid(String userName, String password, String candidatePassword){
//        return hashPassword(userName, password).equals(candidatePassword);
//    }

    public static boolean bIsPasswordValid(String hashed, String candidate){
       return BCrypt.checkpw(hashed, candidate);
        //return false;
    }


    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
