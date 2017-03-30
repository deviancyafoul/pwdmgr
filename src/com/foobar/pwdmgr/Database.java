package com.foobar.pwdmgr;


import java.sql.*;
import java.util.Optional;
import java.util.Properties;

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
            /*String userName = login.getUserName();*/
            PreparedStatement stmt = conn.prepareStatement("INSERT into public.user_passwords (user_id,site_name,username, password) values (?,?,?,?)");
            /*PreparedStatement id = conn.prepareStatement("SELECT id FROM public.users WHERE username = ?");
            id.setString(1,userName);
            ResultSet rs = id.executeQuery();
            if(rs.next()) {*/
                stmt.setInt(1, login.getUserId()/*rs.getInt(1)*/);
                stmt.setString(2, login.getWebsite());
                stmt.setString(3, login.getLoginUserName());
                stmt.setString(4, login.getLoginPswd());
                stmt.executeUpdate();
           /* }*/
        } catch(SQLException e){
            e.printStackTrace();
            System.exit(1);
        }

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
            if(User.isPasswordValid(userName, password, candidatePassword)){
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

