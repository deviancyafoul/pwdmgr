package com.foobar.pwdmgr;

/**
 * Created by alisaarnold on 3/15/17.
 */
public class UserPassword {
    private int userId;
    private String siteName;
    private String userName;
    private String password;

    public UserPassword(int userId, String siteName, String userName, String password) {
        this.userId = userId;
        this.siteName = siteName;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return
                "siteName='" + siteName + '\'' +
                ", userName='" + userName;
    }
}
