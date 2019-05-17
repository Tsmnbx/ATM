package main.java.ATM.Person.Persons.User_Elements;

import main.java.ATM.Accounts.Account;
import main.java.ATM.Screens.ScreenChildren.UserScreens.UserInvestmentScreen;
import main.java.ATM.Screens.ScreenChildren.UserScreens.UserTransactionScreen;
import main.java.ATM.enums.UserStatus;

import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;

import java.io.IOException;

import static main.java.ATM.enums.UserStatus.USER_HOME;

public class UserActChooser {
    /*
    This class decides what methods to use in User Processor
    */

    /**
     * The UserProcessor that allows the functionality of this class
     */
    private UserProcessor userProcessor = new UserProcessor();

    /**
     * The Actions that a user can use for investments
     * @param user The user currently using the ATM
     */
    void investmentAction(User user){
        UserInvestmentScreen userInvestScreen = new UserInvestmentScreen();

        switch (userStatus){
            case INVEST_HOME:    userInvestScreen.investHome(user);  break;
            case INVEST_BUY:    userInvestScreen.buyStock(user);    break;
            case INVEST_SELL:   userInvestScreen.sellStock(user);   break;
        }
    }

    /**
     * Allows the transaction actions a user can pick from in UserScreens that does a specific action based on the
     * UserStatus determined by UserScreens
     * @param accountType The type of Account
     * @param user The User currently using this ATM
     * @throws IOException
     */
    void transactionAction(UserStatus accountType, User user) throws IOException {
        UserTransactionScreen userTransactionScreen = new UserTransactionScreen();

        // Selecting account and Action
        Account     sourceAccount   = userTransactionScreen.selectAccountIndex(user, accountType);
        userStatus                  = userTransactionScreen.selectAction();
        String      amount          = userTransactionScreen.selectAmmount();

        // Using UserProcessor to process the action
        switch (userStatus){
            case DEPOSIT:
                userProcessor.deposit(sourceAccount, amount);
                break;

            case WITHDRAW:
                userProcessor.cashWithdraw(sourceAccount, amount);
                break;

            case TRANS_MY_ACCOUNT:
                System.out.println("which type account would you like to transfer to");
                Account recevingAccount = userTransactionScreen.selectAccountIndex(user,
                        userTransactionScreen.selectAccountType());
                userProcessor.selfTransfer(sourceAccount, recevingAccount, amount);
                break;

            case TRANS_OTHER_USER:
                recevingAccount = userTransactionScreen.selectRecevingOtherUserAccount();
                System.out.println("which type account would you like to transfer to");
                if(recevingAccount != null) {
                    userProcessor.transferMoneyToUser(sourceAccount, recevingAccount, amount);
                }
                break;

            case TRANS_NONUSER_ACCOUNT:
                String address = userTransactionScreen.selectNonUserAdress();
                if (userStatus != USER_HOME){ //Checks if the user Exited
                    userProcessor.payBill(sourceAccount, address, amount);
                }
                userStatus = USER_HOME; //Brings to Home screen
                break;

            case REQUEST_MAKE_JOINT:
                User        otherUser      = userTransactionScreen.selectUser();

                userProcessor.makeAccountJoint(sourceAccount, otherUser,user);
                break;


            case SEE_TIME:
                assert sourceAccount != null;
                System.out.println("creation time of selected account is: " + sourceAccount.timeCreated);
        }
        userStatus = USER_HOME;
    }
}
