package com.foobar.pwdmgr;

/**
 * Created by alisaarnold on 2/12/17.
 */
public class Login {
    private final int userId;
    private final String website;
    private String loginUsername;
    private String loginPswd;

    public Login(int userId,String website, String loginUserName, String loginPswd){
        this.userId = userId;
        this.website = website;
        this.loginUsername = loginUserName;
        this.loginPswd = loginPswd;
    }

    public int getUserId() { return userId;}

    public String getWebsite(){
        return website;
    }

    public String getLoginUserName(){
        return loginUsername;
    }

    public String getLoginPswd() {
        return loginPswd;
    }

    public void setLoginPswd(String loginPswd) {

        this.loginPswd = loginPswd;
    }

  /*  @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }*/


}
