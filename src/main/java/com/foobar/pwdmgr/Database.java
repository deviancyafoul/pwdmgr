package com.foobar.pwdmgr;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public List<UserPassword> searchLogin(String website, User user){
        List<UserPassword> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(JDBC_URL,connectionProps)){
            PreparedStatement stmt = conn.prepareStatement("select user_passwords.* from user_passwords where user_passwords.site_name like ? and user_id = ?");
            stmt.setString(1, website + "%");
            stmt.setInt(2, user.getID());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                result.add(new UserPassword(rs.getInt("user_id"),rs.getString("site_name"),rs.getString("username"), rs.getString("password")));

            }
            conn.close();
        } catch(SQLException e){
            e.printStackTrace();
            System.exit(10);
        }
        return result;

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

