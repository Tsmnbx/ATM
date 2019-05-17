package main.java.ATM.Screens.ScreenChildren.UserScreens;

import main.java.ATM.Person.Persons.User_Elements.User;
import main.java.ATM.Person.Persons.User_Elements.UserProcessor;
import main.java.ATM.Screens.ScreenChildren.PeopleScreens;
import main.java.ATM.enums.BankWorkerStatus;
import main.java.ATM.enums.Status;
import main.java.ATM.enums.UserStatus;
import main.java.ATM.*;

import java.io.IOException;
import java.util.Scanner;

import static main.java.ATM.ATM_Elements.ATM.status;
import static main.java.ATM.Person.Persons.BankWorkers.BankWorker.bankWorkerStatus;
import static main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren.BankAssistantManager.actingAsUser;
import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;
import static main.java.ATM.enums.UserStatus.USER_HOME;

/**
 * This class is used to display the various options of the User to the User. It is a child class of People Screens and
 * is specific to User
 */
public class UserScreen extends PeopleScreens {

    private UserProcessor userProcessor = new UserProcessor();

    /**
     * Displays the Home Screen to the User allows them to see their starting options
     * @param user The User using this ATM
     * @throws IOException
     */
    public void userHome(User user) throws IOException {
        do {
            ans = commandATM("\nHOME SCREEN\n" +
                    "1. Select Account\n" +
                    "2. Quick Deposit\n" +
                    "3. Show all balances\n" +
                    "4. Request new Account creation\n" +
                    "5. Request new Joint Account creation\n" +
                    "6. View and Respond to Joint Account Request\n" +
                    "7. View JointAccount Request Responses\n"+
                    "8. Manage Investments\n" +
                    "9. Change Primary Account\n"+
                    "10. Log out\n" +
                    "Enter 1, 2, 3, 4, 5, 6, 7, 8 or 9");
            switch (ans) {
                case "1": userStatus = UserStatus.SELECT_ACCOUNT_TYPE;      break;
                case "2": quickDeposit(user);                               break;
                case "3": UserTransactionScreen.showBallance(user);         break;
                case "4": createAccount(user);                              break;
                case "5": createJointAccount(user);                         break;
                case "6": userProcessor.viewJointRequest(user);
                    userStatus = UserStatus.RESPOND_TO_JOINT_REQUEST;
                    break;
                case "7": userStatus = UserStatus.VIEW_JOINT_REQUEST_UPDATES;   break;
                case "8": userStatus = UserStatus.INVEST_HOME;                  break;
                case "9": userStatus = UserStatus.MAKE_PRIMARY;                 break;
                case "10":
                    // Attention must be payed to ABM acting as a user as well.
                    actingAsUser = false;
                    userStatus = UserStatus.NULL;
                    if (bankWorkerStatus == BankWorkerStatus.NULL) {
                        status = Status.USERNAME_CHECK;
                    }
                    break;
                default : System.out.println("Invalid Command\n");
            }
        } while (userStatus == USER_HOME && status == Status.NULL);
    }

    /**
     * helper method that selects the Accounttype
     */
    public void selectAccount(){
        System.out.println("\nSELECT TYPE OF ACCOUNT \n");
        userStatus = selectAccountType();
    }

    /**
     * Selects the transaction type User is going to make
     * @return userStatus the ENUM that determines the action the User will make
     */
    public UserStatus selectAction() {

        boolean ansValid = false;
        while (!ansValid && userStatus != USER_HOME) {
            ans = commandATM(
                    "\n SELECT AN ACTION \n" +
                            "1. Transfer Money to one of your Accounts\n" +
                            "2. Make a withdraw\n" +
                            "3. Transfer money to another users account\n" +
                            "4. Transfer money to a non users account\n" +
                            "5. Deposit money\n" +
                            "6. See time of Account creation\n" +
                            "7. Make Account Joint\n" +
                            "Enter 1, 2, 3, 4, 5, 6, 7, 8, or \'e\' to Exit");
            switch (ans) {
                case "1": return UserStatus.TRANS_MY_ACCOUNT;
                case "2": return UserStatus.WITHDRAW;
                case "3": return UserStatus.TRANS_OTHER_USER;
                case "4": return UserStatus.TRANS_NONUSER_ACCOUNT;
                case "5": return UserStatus.DEPOSIT;
                case "6":
                    userStatus = USER_HOME;
                    return UserStatus.SEE_TIME;
                case "7":return UserStatus.REQUEST_MAKE_JOINT;
                case "e":
                    userStatus = USER_HOME;
                    return USER_HOME;
                default : System.out.println("invalid input try again\n");
            }
        }
        return USER_HOME;
    }

    /**
     * A deposit that automatically allows a User to put money in their primary chequing account
     * @param user The User using this ATM
     * @throws IOException
     */
    public void quickDeposit(User user)throws IOException {
        ans = commandATM("enter how much you would like to deposit to your default account: ");
        userProcessor.quickDeposit(ans, user);
    }

    /**
     * Allows the creation of an Account
     * @param user
     */
    public void createAccount(User user){
        System.out.println("Select New Account Type");
        UserStatus accountType = selectAccountType();
        userProcessor.requestAccountCreation(user, accountType);
    }

    public void createJointAccount(User user){
        UserTransactionScreen userTransactionScreen = new UserTransactionScreen();
        User        targetUser     = userTransactionScreen.selectUser();
        System.out.println("Select New Account Type");
        UserStatus accountType = selectAccountType();
        userProcessor.requestJointAccountCreation(accountType, user, targetUser);
    }

    /**
     * Handles the functionality of Choosing and responding to joint account Request of existing accounts
     * from another user
     * @param user
     */
    public void jointRequestHandling(User user){
        Scanner index = new Scanner(System.in);
        System.out.println("Which request would you like to respond to? Press 'e' to exit:");
        int i = index.nextInt() - 1;
        Requests request = userProcessor.getRequest(user, i);
        Scanner inputResponse = new Scanner(System.in);
        System.out.println("Enter '1' To Accept Request"+"\n" +" Enter '2' To Reject Request");
        String response = inputResponse.nextLine();
        if(response.equals("1")){
            userProcessor.addJointAccount(request,user);
        }
        else if(response.equals("2")){
            userProcessor.removeJointAccount(request,user);
        }
        else if(response.equals("e")){
            User.userStatus = USER_HOME;
        }
        else{
            System.out.println("Invalid response");
        }
    }

    /**
     * Allows you to view the Responses to pre-existing Accounts to wanted to make joint with other users
     * @param user The User currently using this ATM
     */
    public void viewRequestHandling(User user){
        userProcessor.viewRequestUpdates(user);
        Scanner preString = new Scanner(System.in);
        System.out.println("Would you like to clear your Update List? \n 1. YES \n 2. NO");
        String string = preString.nextLine();
        if(string.equals("1")){
            userProcessor.clearUpdates(user);
        }
        else{
            System.out.println("Make sure you clear later or else this list will get big");
        }
    }
}