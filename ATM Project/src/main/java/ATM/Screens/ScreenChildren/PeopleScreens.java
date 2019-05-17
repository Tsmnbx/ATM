package main.java.ATM.Screens.ScreenChildren;


import main.java.ATM.Person.Person;
import main.java.ATM.Screens.*;
import main.java.ATM.Person.Persons.User_Elements.*;
import main.java.ATM.Accounts.*;

import main.java.ATM.enums.BankWorkerStatus;
import main.java.ATM.enums.UserStatus;

import static main.java.ATM.ATM_Elements.ATM.allPeople;
import static main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren.BankManager.bankWorkerStatus;
import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;

public class PeopleScreens extends Screen{
    public UserStatus selectAccountType(){
        boolean ansValid = false;

        while (!ansValid) {
            ans = commandATM(
                    "1. Credit Card\n" +
                            "2. Line Of Credit\n" +
                            "3. Chequing\n" +
                            "4. Saving\n" +
                            "5. Investment\n" +
                            "enter 1, 2, 3, 4, or \'e\' to Exit");
            switch (ans) {
                case "1": return UserStatus.CREDIT_CARD;
                case "2": return UserStatus.LINE_OF_CREDIT;
                case "3": return UserStatus.CHEQUING;
                case "4": return UserStatus.SAVINGS;
                case "5": return UserStatus.INVESTMENT;
                case "e":
                    setPersonToHome();
                    return UserStatus.USER_HOME;
            }
            System.out.println("In valid input Try again");
        }
        return UserStatus.NULL;
    }

    // Select what account of spesific Type (Credit Card, Line of Credit, Checking, Saving) you want
    public Account selectAccountIndex(User user, UserStatus typeOfAccount) {
        Account subjAccount;
        boolean ansValid = false;

        while (!ansValid && userStatus != UserStatus.USER_HOME) {
            ans = commandATM("The " + typeOfAccount + " Balances are\\is:\n" +
                    user.accounts.get(typeOfAccount).toString() +
                    "\nEnter the index of the account to select account (starting from zero)" +
                    "\nEnter \'e\' If you would like to exit to Home");

            if (ans.equals("e")) {
                setPersonToHome();
                ansValid = true;

            } else if (isInteger(ans) && user.accountInRange(typeOfAccount, Integer.parseInt(ans))) {
                subjAccount = user.getAccount(typeOfAccount, Integer.parseInt(ans));
                System.out.println("You have chosen the account with balance: " + subjAccount.balance);
                return subjAccount;
            }
        }
        return user.getAccount(typeOfAccount, 0);
    }

    // Displays message saying if transaction works
    public static void transactionValid(boolean isValid){
        if (isValid){
            System.out.println("Transaction VALID");
        } else{
            System.out.println("Transaction INVALID");
        }
    }

    public Person selectPerson(){
        boolean ansValid = false;

        while (!ansValid) {
            ans = commandATM("Type Username");

            if (ans.equals("e")){
                bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
                ansValid = true;
            }
            if (allPeople.containsKey(ans)) {
                return allPeople.get(ans);
            }
            System.out.println("Invalid input");
        }
        return null;
    }

    // Helpers
    // Checking and setting which type of person to send to home screen.
    void setPersonToHome(){
        userStatus = (userStatus != UserStatus.NULL) ?  UserStatus.USER_HOME : UserStatus.NULL;
        bankWorkerStatus = (bankWorkerStatus != BankWorkerStatus.NULL) ? BankWorkerStatus.BANK_WORKER_HOME : BankWorkerStatus.NULL;
    }

    public boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
