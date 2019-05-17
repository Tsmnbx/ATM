package main.java.ATM.Screens.ScreenChildren;



import main.java.ATM.Accounts.*;
import main.java.ATM.SMS;
import main.java.ATM.Transaction.*;


import main.java.ATM.enums.BankWorkerStatus;
import main.java.ATM.enums.Status;
import main.java.ATM.enums.UserStatus;

import static main.java.ATM.ATM_Elements.ATM.allPeople;
import static main.java.ATM.ATM_Elements.ATM.status;
import static main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren.BankManager.bankWorkerStatus;
import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;

public class BankWorkerScreen extends PeopleScreens {

    public static String ans;

    public void bankManagerHome() {
        while (bankWorkerStatus == BankWorkerStatus.BANK_WORKER_HOME && status == Status.NULL){
            ans = commandATM("1. Create a new user\n" +
                    "2. Process User creations\n" +
                    "3. Restock the machine\n" +
                    "4. Undo recent transaction\n" +
                    "5. Process account creation requests\n" +
                    "6. Process joint account creation requests\n" +
                    "7. Un-block User\n" +
                    "8. Log Out \n" +
                    "Enter 1, 2, 3, 4, 5 or 6");
            switch (ans) {
                case "1": bankWorkerStatus = BankWorkerStatus.CREATE_USER;                      break;
                case "2": bankWorkerStatus = BankWorkerStatus.PROCESS_ACCOUNTS;                 break;
                case "3": bankWorkerStatus = BankWorkerStatus.RESTOCK;                          break;
                case "4": bankWorkerStatus = BankWorkerStatus.REDO;                             break;
                case "5": bankWorkerStatus = BankWorkerStatus.ACCOUNT_CREATION_REQUEST;         break;
                case "6": bankWorkerStatus = BankWorkerStatus.JOINT_ACCOUNT_CREATION_REQUEST;   break;
                case "7": bankWorkerStatus = BankWorkerStatus.APPROVE_ACCOUNT;                   break;
                case "8":
                    bankWorkerStatus = BankWorkerStatus.NULL;
                    status = Status.USERNAME_CHECK;
                    break;
                default:
                    System.out.println("Invalid Command\n");
            }
        }
    }

    public void bankAssistantManagerHome(){
        while (bankWorkerStatus == BankWorkerStatus.BANK_WORKER_HOME){
            ans = commandATM("BANK ASSISTANT MANAGER HOME\n" +
                    "1. Log in as a user\n" +
                    "2. Create a User\n" +
                    "3. Process an account request\n" +
                    "4. Process joint account request\n"+
                    "5. Log out");
            switch (ans){
                case "1":
                    bankWorkerStatus = BankWorkerStatus.LOGIN_AS_USER;
                    userStatus = UserStatus.USER_HOME;
                    break;
                case "2": bankWorkerStatus = BankWorkerStatus.CREATE_USER;              break;
                case "3": bankWorkerStatus = BankWorkerStatus.ACCOUNT_CREATION_REQUEST; break;
                case "4": bankWorkerStatus = BankWorkerStatus.JOINT_ACCOUNT_CREATION_REQUEST; break;
                case "5":
                    bankWorkerStatus = BankWorkerStatus.NULL;
                    status = Status.USERNAME_CHECK;
            }
        }
    }

    public String createUsername() {
        while (bankWorkerStatus == BankWorkerStatus.CREATE_USERNAME) {
            ans = commandATM("Type new Username, enter \"e\" to Exit");

            if (ans.equals("e")){
                bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
            } else if (!ans.equals("") && !ans.equals("1") && !commandList.contains(ans) && !allPeople.containsKey(ans)) { // Username must be valid
                bankWorkerStatus = BankWorkerStatus.CREATE_PASSWORD;
                return ans;
            } else{
                System.out.println("Invalid username: Username has already exist or empty. Try again");
            }
        }
        return null;
    }

    public String createPassword() {
    while (bankWorkerStatus == BankWorkerStatus.CREATE_PASSWORD){
        ans = commandATM("Initialize password, enter \"e\" to Exit)");
        if (ans.equals("e")){
            bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
        }
        else if (!(ans.equals(""))) {
            bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
            return ans;
        } else {
            System.out.println("Password can not be empty. Try again");
        }
    }
    return null;
    }
    SMS SMS_Example = new SMS();

    public boolean verifyCode(String code){

        SMS_Example.send_text(code);
        while(bankWorkerStatus == BankWorkerStatus.CODE_CHECK) {
            ans = commandATM("Enter the Code Received by SMS. enter\"e\" to exit");
            if (ans.equals(code)) {
                System.out.println("valid code User Created");
                bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
                return true;
            }
            else if (ans.equals("e")){
                bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
            }
        }
        return false;
    }

    public String selectAccount() {
        while (bankWorkerStatus != BankWorkerStatus.CHOOSE_ACCOUNT) {
            ans = commandATM("Which User requested the undoTransaction?\n" +
                             "Type Username or \"e\" to exit");
            if (allPeople.containsKey(ans)) {
                return ans;
            } else if(ans.equals("e")) {
                bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
            }
            else {
                System.out.println("User does not exists. Try again");
            }
        }
        return  null;
    }

    public Transaction selectTransaction(Account a){
        Transaction t = null;
        boolean ansValid = false;

        while (!ansValid){
            Object[] informationForScreen = a.provideInformationForChoosingTransaction();
            StringBuilder me = (StringBuilder) informationForScreen[0];
            String message = me.toString();
            Integer numberOfOptions = (Integer) informationForScreen[1];
            ans = commandATM("Please select a Transaction from the list below:\n" + message +
                    "\nEnter the index of the account to select account (starting from zero)" +
                    "\nEnter \'e\' If you would like to exit to Home");
            if (ans.equals("e")) {
                bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
                ansValid = true;
            }
            else if (isInteger(ans) && (Integer.parseInt(ans) <= numberOfOptions)){
                t = a.listOfRecentTransactions.get(Integer.parseInt(ans));
                ansValid = true;
            }
        }
        return t;
    }

}