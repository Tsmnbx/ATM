package main.java.ATM.Person.Persons.User_Elements;


import main.java.ATM.Screens.ScreenChildren.UserScreens.*;
import main.java.ATM.Accounts.AssetAccount.AssetAccountsChildren.*;
import main.java.ATM.Person.*;
import main.java.ATM.Accounts.*;
import main.java.ATM.*;


import main.java.ATM.enums.UserStatus;




import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static main.java.ATM.enums.UserStatus.*;

/**
 * A child class of Person, this class is the User: an individual who will be using the ATM, which has a username,
 * password, a Hashmap which contains its accounts and a few helper methods and a method named act that outlines the
 * types of actions a user can take
 */

public class User extends Person{
    /**
     * This field is static as it only changes when the user changes
     */
    public static UserStatus userStatus = UserStatus.NULL;

    /**
     * This is a hashmap that contains the vavious accounts that a User can have, the key is the Eunm UserStatus and
     * a value which is an ArrayList which contains accounts of a specfic type based of the Enum value(CREDIT_CARD as the
     * Enum and the ArrayList has all of the Credit Card accounts that the User has)
     *
     */
    public HashMap<UserStatus, ArrayList<Account>> accounts = new HashMap<UserStatus, ArrayList<Account>>();
    /**
     * This ArrayList contains the Request from other Users to add an account from pre-existing accounts and make it a
     * Joint Account that the User has to respond to. It contains Request which hold the type of Account,
     * The User its from and the User its going towhich is this User
     */
    public ArrayList <Requests> jointRequest = new ArrayList<Requests>();
    /**
     * When another User responds a request to make a pre-existing account Joint, the response is stored as a string in
     * this array
     */
    public ArrayList<String> updateToJoint = new ArrayList<String>();

    public User(String u, String p){
        this.username = u;
        this.password = p;

        accounts.put(CREDIT_CARD,       new ArrayList<Account>());
        accounts.put(LINE_OF_CREDIT,    new ArrayList<Account>());
        accounts.put(CHEQUING,          new ArrayList<Account>());
        accounts.put(SAVINGS,           new ArrayList<Account>());
        accounts.put(INVESTMENT,        new ArrayList<Account>());
    }

    // Getters and Setters
    public boolean accountInRange(UserStatus accountType, int index){
        return (index < this.accounts.get(accountType).size() && index >= 0);
    }

    public Account getAccount(UserStatus accountType, int index){                                                              // we don't need this stuff
        return accounts.get(accountType).get(index);
    }

    public ArrayList<Account> getAccountTypeList(UserStatus accountType){
        return accounts.get(accountType);
    }

    // Screen

    /**
     * Allows for the User to undergo its actions
     * @throws IOException
     */
    @Override
    public void act()throws IOException {
        // Necessary helper objects
        UserScreen              userScreen              = new UserScreen();
        UserTransactionScreen   userTransactionScreen   = new UserTransactionScreen();
        UserProcessor           userProcessor           = new UserProcessor();
        UserActChooser          userActChooser          = new UserActChooser();

        switch (userStatus)  {

            // Home Screen
            case USER_HOME:
                userScreen.userHome(this);
                break;
            case SELECT_ACCOUNT_TYPE:
                userScreen.selectAccount();
                break;

            // Simple Actions
            case MAKE_PRIMARY:
                Account newPrimaryAccount = userTransactionScreen.selectAccountIndex(this, CHEQUING);
                userProcessor.makePrimary(this, (Chequing)newPrimaryAccount);
                userStatus = USER_HOME;
                break;

            case RESPOND_TO_JOINT_REQUEST:
                userScreen.jointRequestHandling(this);
                userProcessor.viewJointRequest(this);
                userStatus = USER_HOME;
                break;

            case VIEW_JOINT_REQUEST_UPDATES:
                userScreen.viewRequestHandling(this);
                userStatus = USER_HOME;
                break;

            // Investment Actions
            case INVEST_HOME:
            case INVEST_BUY:
            case INVEST_SELL:
                userActChooser.investmentAction(this);
                break;

            // Transaction Actions
            case CREDIT_CARD:
            case LINE_OF_CREDIT:
            case CHEQUING:
            case SAVINGS:
            case INVESTMENT:
                userActChooser.transactionAction(userStatus, this);
                break;
        }
    }
}
