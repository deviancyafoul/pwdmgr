package com.foobar.pwdmgr;

import com.foobar.pwdmgr.Database;

import java.util.Optional;
import java.util.Scanner;

/**
 * Created by alisaarnold on 2/9/17.
 */
public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        Scanner keyboard = new Scanner(System.in);
        String answer = getUserSelection(keyboard);

        if("1".equals(answer)){
            answer = doAuthenticate(database, keyboard);
        }

        if("2".equals(answer)){
            createUser(database, keyboard);

        }

    }

    private static String doAuthenticate(Database database, Scanner keyboard) {
        String answer = "";
        System.out.println("Please enter your username");
        String userName = keyboard.nextLine();
        System.out.println("Please enter your password");
        String password = keyboard.nextLine();
        int attempts = 3;
        Optional<User> user = database.authenticate(userName, password);
        while(!user.isPresent() && --attempts > 0){
            user = database.authenticate(userName, password);
            System.out.println("Sorry. Either the username or password you entered is incorrect.\nPlease try again.\nAttempts remaining: " + attempts);
            System.out.println("Please enter your username");
            userName = keyboard.nextLine();
            System.out.println("Please enter your password");
            password = keyboard.nextLine();
        }
        if(attempts == 0){
            System.out.println("Sorry. We were not able to authenticate you;");
            return answer;
        } else {
            System.out.println("Authenticated");
            return siteSearch(database, keyboard, user);
        }
    }

    private static String siteSearch(Database database, Scanner keyboard, Optional<User> user) {
        String answer;
        String answer2 = "";
        System.out.println("Press 1 for Site Search.\nPress 2 for New Site.\nPress 3 to Logout.");
        answer = keyboard.nextLine();
        while(!"1".equals(answer) && !"2".equals(answer) && !"3".equals(answer)){
            System.out.println("Press 1 for Site Search.\nPress 2 for New Site.\nPress 3 to Logout");
            answer = keyboard.nextLine();
        }
        if("2".equals(answer)){
            createNewSite(database, keyboard, user.get());
        } else if ("3".equals(answer)){
            System.out.println("Logged out.");
        }
        return answer2;
    }

    private static void createNewSite(Database database, Scanner keyboard, User currentUser) {

        System.out.println("Please enter site address:");
        String website = keyboard.nextLine();
        System.out.println("Please enter the username:");
        String loginUserName = keyboard.nextLine();
        System.out.println("Please enter the password:");
        String loginPswd = keyboard.nextLine();
        Login newLogin = new Login(currentUser, website, loginUserName, loginPswd);
        database.addLogin(newLogin);
    }

    private static void createUser(Database database, Scanner keyboard) {
        System.out.println("Please create a username");
        String userName = keyboard.nextLine();
        System.out.println("Please create a password");
        String password = keyboard.nextLine();

        User newUser = new User(userName, password);
        database.addUser(newUser);
    }

    private static String getUserSelection(Scanner keyboard) {
        String  answer;
        System.out.println("Press 1 for Returning User.\nPress 2 for New User.");
        answer = keyboard.nextLine();

        while(!"1".equals(answer) && !"2".equals(answer)){
            System.out.println("Press 1 for Returning User.\nPress 2 for New User.");
            answer = keyboard.nextLine();
        }
        return answer;
    }

}
