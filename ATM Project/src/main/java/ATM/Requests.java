package main.java.ATM;

import main.java.ATM.Person.Persons.User_Elements.*;
import main.java.ATM.enums.UserStatus;
import main.java.ATM.Accounts.*;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class exists to hold information about requests users make to the Bank workers. The different requests are:
 * new user creation requests, account creation requests, and new joint account creation requests.
 * The fields of this class are what hold the necessary information. The fields are filled depending on the methods
 * called. Methods are self explanatory by their naming.
 */
public class Requests {
    public String requestedUserName;
    public String requestedPassword;
    public UserStatus accountType;
    public User userA;
    public User userB;
    public Account account;
    public HashMap<UserStatus,ArrayList<Account>> userA_Accounts;
    public HashMap<UserStatus,ArrayList<Account>> userB_Accounts;

    public Requests(){

    }

    public void requestUserCreation(String username, String password){
        this.requestedUserName = username;
        this.requestedPassword = password;
    }

    public void requestAccountCreation(UserStatus accountType, User user){
        this.accountType = accountType;
        this.userA = user;
    }
    public void makeAJointRequest(Account acc, User targetUser, User sourceUser){
        this.userA = sourceUser;
        this.userB = targetUser;
        this.account = acc;
    }

    public void makeNewJointAccountRequest(UserStatus accountType, User sourceUser, User targetUser){
        this.accountType = accountType;
        this.userA = sourceUser;
        this.userB = targetUser;
        this.userA_Accounts = this.userA.accounts;
        this.userB_Accounts = this.userB.accounts;
    }

}
