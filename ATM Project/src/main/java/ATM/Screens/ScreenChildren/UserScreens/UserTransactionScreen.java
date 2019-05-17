package main.java.ATM.Screens.ScreenChildren.UserScreens;



import main.java.ATM.Person.Person;
import main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren.BankAssistantManager;
import main.java.ATM.Person.Persons.User_Elements.*;
import main.java.ATM.Accounts.*;


import main.java.ATM.enums.UserStatus;

import static main.java.ATM.ATM_Elements.ATM.allPeople;
import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;

public class UserTransactionScreen extends UserScreen {

    // Shows ballances of all accounts
    public static void showBallance(User user){
        for ( UserStatus accountType : user.accounts.keySet()){
            System.out.printf("%-20.30s  %-20.30s%n", accountType, user.accounts.get(accountType));
        }
    }

    // Selects the receiving account of the other user
    public Account selectRecevingOtherUserAccount(){
        boolean ansValid = false;
        while (!ansValid){
            System.out.println("Select user you would like to transfer account to");
            Person selectPerson = selectPerson();

            if (ans.equals("e")){
                ansValid = true;
                userStatus = UserStatus.USER_HOME;

            } if (selectPerson instanceof User){ // Is a valid Username
                User recevingUser = (User) allPeople.get(ans);

                System.out.println("Select User's account type");
                return selectAccountIndex(recevingUser, selectAccountType());
            }
            else if (selectPerson instanceof BankAssistantManager){
                User receivingUser = (User) ((BankAssistantManager) allPeople.get(ans)).getUser();
                return selectAccountIndex(receivingUser, selectAccountType());
            }
            else {
                System.out.println("Username does not belong to a user or Bank assistant manager try again, enter\'e\' to Exit");
            }
        }
        return null;
    }

    public String selectAmmount(){
        boolean ansValid = false;
        while (!ansValid && userStatus != UserStatus.USER_HOME) {
            ans = commandATM("enter $ amount for transaction (you may enter Zero)");
            if (isNumeric(ans) && Double.parseDouble(ans) >= 0){
                ansValid = true;
            } else{
                System.out.println("Invalid input Try again");
            }
        }
        return ans;
    }

    public String selectNonUserAdress(){
        boolean ansValid = false;
        while (!ansValid && userStatus != UserStatus.USER_HOME){
            ans = commandATM("Type Billing Address, (enter \'e\' to Exit)");

            if (ans.equals("e")){
                userStatus = UserStatus.USER_HOME;
                ansValid = true;
            }
            if (!ans.equals("")){
                ansValid = true;
            }
        }
        return ans;
    }

    public User selectUser(){
            boolean ansValid = false;
            while (!ansValid){
                System.out.println("Who you would like join this account to");
                Person user = selectPerson();

                if (ans.equals("e")){
                    ansValid = true;
                    userStatus = UserStatus.USER_HOME;

                } else if (user instanceof User){ // Is a valid Username
                    return (User) user;

                } else {
                    System.out.println("Username does not belong to a user try again, enter\'e\' to Exit");
                }
            }
            return null;


    }
}
