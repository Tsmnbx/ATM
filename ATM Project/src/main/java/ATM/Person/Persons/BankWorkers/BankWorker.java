package main.java.ATM.Person.Persons.BankWorkers;

import main.java.ATM.Person.Persons.BankWorkers.*;

import main.java.ATM.Requests;
import main.java.ATM.Screens.ScreenChildren.*;
import main.java.ATM.Accounts.AssetAccount.AssetAccountsChildren.*;
import main.java.ATM.Person.Persons.User_Elements.*;
import main.java.ATM.Accounts.*;
import main.java.ATM.Accounts.DebtAccount.DebtAccountsChildren.*;


import main.java.ATM.enums.*;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static main.java.ATM.ATM_Elements.ATM.allPeople;

/**
 * This Class is an Abstract class which holds the shared methods and fields for the Bank Manager and the Assistant Bank Manager.
 */
public abstract class BankWorker extends main.java.ATM.Person.Person{
    public static BankWorkerStatus bankWorkerStatus = BankWorkerStatus.NULL;

    //For potential future expansion of BankWorkers
    public static HashMap<String, BankWorker> bankWorkers = new HashMap<>();

    //These below are static since they must be accessed by both the Assistant Bank Manager and the Bank Manger
    public static LinkedList<Requests> accountRequests = new LinkedList<>();  // Stores the requests to create a new account
    public static LinkedList<Requests> accountJointRequests = new LinkedList<>(); // Stores the requests to create a new joint account
    public static LinkedList<Requests> requestsForUsers = new LinkedList<>(); // Stores user creation requests


    /**
     * Completely goes through the queue (LinkedList) of requestsForUsers and dequeues each request. Uses the Request object's fields as information
     * for the creation of a new user. Finishes by adding the newly created user to the allPeople field.
     */
    public void ProcessUserCreationRequests(){
        while (!requestsForUsers.isEmpty()){
            Requests userRequest = requestsForUsers.remove();
            User newU = createSingleUser(userRequest.requestedUserName,userRequest.requestedPassword);
            allPeople.put(newU.username, newU);
        }
    }

    /**
     * Creates and returns a single instance of User object. Used in niche locations of the code for setup of processes.
     * @param username - The username of the requested user
     * @param password - The password of the requested user
     * @return - A User Object
     */
    public User createSingleUser(String username, String password){
        User newU = new User(username, password);
        newU.accounts.get(UserStatus.CREDIT_CARD).add(new CreditCard());
        newU.accounts.get(UserStatus.LINE_OF_CREDIT).add(new LineofCredit());
        newU.accounts.get(UserStatus.CHEQUING).add(new Chequing());
        newU.accounts.get(UserStatus.SAVINGS).add(new Savings());
        newU.accounts.get(UserStatus.INVESTMENT).add(new Investment());
        return newU;
    }

    /**
     * Goes through the queue field (LinkedList) of accountRequests and dequeues each request. Uses the Request object's fields as information
     * for the creation of the new account. Finishes by putting the account in the user's accounts map field in the proper location.
     */
    public void accountCreation(){              //Easy to use. Just call it and it does all the work
        if (accountRequests.isEmpty()) {
            System.out.println("There are no new account requests\n");
        }
        while (!accountRequests.isEmpty()) {
            Requests accountRequest = accountRequests.remove();
            UserStatus accountType = accountRequest.accountType;
            HashMap<UserStatus, ArrayList<Account>> accounts = accountRequest.userA.accounts;

            switch (accountType){
                case CREDIT_CARD:       accounts.get(UserStatus.CREDIT_CARD).add(new CreditCard());        break;
                case LINE_OF_CREDIT:    accounts.get(UserStatus.LINE_OF_CREDIT).add(new LineofCredit());  break;
                case CHEQUING:          accounts.get(UserStatus.CHEQUING).add(new Chequing());              break;
                case SAVINGS:           accounts.get(UserStatus.SAVINGS).add(new Savings());                break;
                case INVESTMENT:        accounts.get(UserStatus.SAVINGS).add(new Investment());             break;
            }
        }
    }

    /**
     * A method which is used by the act() method in the Bank Manager and Bank Assistant Manager. Uses information from screen
     * input to create a user.
     */
    public void createUser(){
        BankWorkerScreen bankWorkerScreen   = new BankWorkerScreen();
        bankWorkerStatus                    = BankWorkerStatus.CREATE_USERNAME;
        String newUsername                  = bankWorkerScreen.createUsername();
        String newPassword                  = bankWorkerScreen.createPassword();

        if (newUsername != null && newPassword != null) { // Create user was not exited
            userMade(newUsername, newPassword);
        }
    }

    /**
     * This method is called upon the request to make a new user from the log in screen. Its used when 2-factor authentication
     * passes. It files a request for a new user in the BankWorker's requestsForUsers field. It can be processed by logging in as a Bank Manager.
     * @param username - The requested username
     * @param password - The requested password
     */
    public void userMade(String username, String password){
        Requests newUserRequest = new Requests();
        newUserRequest.requestUserCreation(username, password);
        requestsForUsers.add(newUserRequest);
    }

    /**
     * A method which is used to process Joint account creation. Goes through the LinkedList Queue field named, accountJointRequests, to
     * dequeue the Request object. This Request object holds information for the joint request which is provided to this method.
     * This method places the requested account in the two users map of accounts.
     */
    public void accountJointCreation(){
        while (!accountJointRequests.isEmpty()){
            Requests info = accountJointRequests.remove();
            UserStatus accountType = info.accountType;
            HashMap<UserStatus, ArrayList<Account>> userAAccounts = info.userA_Accounts;
            HashMap<UserStatus, ArrayList<Account>> userBAccounts = info.userB_Accounts;

            switch (accountType){
                case CREDIT_CARD:{
                    CreditCard cc = new CreditCard();
                    userAAccounts.get(UserStatus.CREDIT_CARD).add(cc);
                    userBAccounts.get(UserStatus.CREDIT_CARD).add(cc);
                    break;}
                case LINE_OF_CREDIT:{
                    LineofCredit loc = new LineofCredit();
                    userAAccounts.get(UserStatus.LINE_OF_CREDIT).add(loc);
                    userBAccounts.get(UserStatus.LINE_OF_CREDIT).add(loc);
                    break;}
                case CHEQUING:{
                    Chequing c = new Chequing();
                    userAAccounts.get(UserStatus.CHEQUING).add(c);
                    userBAccounts.get(UserStatus.CHEQUING).add(c);
                    break;}
                case SAVINGS:{
                    Savings s = new Savings();
                    userAAccounts.get(UserStatus.SAVINGS).add(s);
                    userBAccounts.get(UserStatus.SAVINGS).add(s);
                    break;}
            }
        }
    }

}