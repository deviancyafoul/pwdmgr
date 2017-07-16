package com.foobar.pwdmgr;


import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.awt.*;


/**
 * Created by alisaarnold on 2/9/17.
 */
public class Database {
    private Properties connectionProps = new Properties();
    private Connection conn = null;
    private final String JDBC_URL = "jdbc:postgresql:pswdmgr";

    public Database(){
        connectionProps.put("user", "alisa");
        connectionProps.put("password", "kittens");
    }


    public void addUser(User user){
        try (Connection conn = DriverManager.getConnection(JDBC_URL,connectionProps)){
            PreparedStatement stmt = conn.prepareStatement("INSERT into public.users (username, password) values (?,?)");
            stmt.setString(1,user.getUserName());
            stmt.setString(2,user.getPassword());
            stmt.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
            System.exit(1);
        }

    }


  public void addLogin(Login login){
        try (Connection conn = DriverManager.getConnection(JDBC_URL,connectionProps)){
            PreparedStatement stmt = conn.prepareStatement("INSERT into public.user_passwords (user_id,site_name,username, password) values (?,?,?,?)");
            PreparedStatement id = conn.prepareStatement("SELECT id FROM public.users WHERE username = ?");
            id.setString(1,login.getUser().getUserName());
            ResultSet rs = id.executeQuery();
            if(rs.next()) {
                stmt.setInt(1, rs.getInt(1));
                stmt.setString(2, login.getWebsite());
                stmt.setString(3, login.getLoginUserName());
                stmt.setString(4, login.getLoginPswd());
                stmt.executeUpdate();
            }
        } catch(SQLException e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    public  String searchLogin(String website){
        //String[] username = new String [3];
        String username = "";
        String password = "";
        String site = website;
        try (Connection conn = DriverManager.getConnection(JDBC_URL,connectionProps)){
            PreparedStatement stmt = conn.prepareStatement("select user_passwords.* from user_passwords, users where user_passwords.user_id = users.id and user_passwords.site_name = ?");
            //PreparedStatement id = conn.prepareStatement("SELECT id FROM public.users WHERE username = ?");
            //id.setString(1,site);
            stmt.setString(1, website);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
               username = rs.getString("username") + "\n" + rs.getString("password");
                //username[0] = rs.getString("username");
                //username[1] = rs.getString("password");
                //username[2] = rs.getString("site_name");
                password = rs.getString("password");
                StringSelection selection = new StringSelection(password);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
            }
        } catch(SQLException e){
            e.printStackTrace();
            System.exit(10);
        }
        return username;

    }

    public int getUserId(User user) {
        int anInt = 0;
        try (Connection conn = DriverManager.getConnection(JDBC_URL, connectionProps)) {
            String userName = user.getUserName();
            PreparedStatement id = conn.prepareStatement("SELECT id FROM public.users WHERE username = ?");
            id.setString(1, userName);
            ResultSet rs = id.executeQuery();
            if (rs.next()) {
                anInt = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(2);
        }
        return anInt;
    }

    public Connection getConnection() throws SQLException {

        return conn;
    }


    public Optional<User> authenticate(String userName, String password){
        try (Connection conn = DriverManager.getConnection(JDBC_URL,connectionProps)){
            PreparedStatement stmt = conn.prepareStatement("select * from users where userName = ?");
            stmt.setString(1,userName);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            String candidatePassword = resultSet.getString("password");
            int candidateId = resultSet.getInt("id");
            String candidateUserName = resultSet.getString("username");
            if(User.bIsPasswordValid(password, candidatePassword)){
                return Optional.of(new User(candidateId, candidateUserName, candidatePassword));
            } else {
                return Optional.empty();
            }

        } catch(SQLException e){
            e.printStackTrace();
            System.exit(1);
        }
        return Optional.empty();
    }
}

