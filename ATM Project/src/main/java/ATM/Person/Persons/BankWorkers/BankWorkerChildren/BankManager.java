package main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren;


import main.java.ATM.Person.Person;
import main.java.ATM.Person.Persons.BankWorkers.*;

import main.java.ATM.Person.Persons.User_Elements.*;
import main.java.ATM.Accounts.*;
import main.java.ATM.ATM_Elements.*;
import main.java.ATM.Screens.ScreenChildren.BankWorkerScreen;
import main.java.ATM.Transaction.*;



import main.java.ATM.enums.BankWorkerStatus;
import main.java.ATM.enums.UserStatus;
import main.java.ATM.enums.TransactionType;

import java.io.*;

import static main.java.ATM.ATM_Elements.ATM.allPeople;

/**
 * This is a Bank manager. It is a child to Bank Worker. Has functionality to: Create a single new user, process multiple requests for new
 * users, restock the atm, undo a transaction performed by a user's account, process the request for a new account, process the request for a new
 * joint account, and re-approve a user that was blocked from their account due to too many failed log-in attempts.
 */
public class BankManager extends BankWorker {
    public BankManager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * This act method is the primary method that is called by the program to perform tasks requests by the user.
     */
    @Override
    public void act(){
        BankWorkerScreen bankWorkerScreen = new BankWorkerScreen();
        if (bankWorkerStatus == BankWorkerStatus.BANK_WORKER_HOME){
            bankWorkerScreen.bankManagerHome();
        }
        if (bankWorkerStatus != BankWorkerStatus.NULL) {
            switch (bankWorkerStatus) {
                case CREATE_USER:                       createUser();                   break;
                case RESTOCK:                           restock();                      break;
                case REDO:                              redo();                         break;
                case PROCESS_ACCOUNTS:                  ProcessUserCreationRequests();  break;
                case APPROVE_ACCOUNT:                   approvedUser();                 break;
                case ACCOUNT_CREATION_REQUEST:          accountCreation();
                case JOINT_ACCOUNT_CREATION_REQUEST:    accountJointCreation();
            }
            bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
        }
    }

    /**
     * The method called to restock the ATM machine's bills
     */
    private void restock(){
        System.out.println("Bills before the restock:\n");
        System.out.println(ATM.numberOfBills);
        ATM.below20();
        fillATM();
        System.out.println("Bills after the restock:\n");
        System.out.println(ATM.numberOfBills);
    }

    /**
     * The method which uses information provided from screen inputs to get the necessary transaction which is to be undone.
     * called the undoTransaction method to perform the task.
     */
    private void redo(){
        BankWorkerScreen bankWorkerScreen = new BankWorkerScreen();
        User reUser = (User) allPeople.get(bankWorkerScreen.selectAccount());
        UserStatus reAccountType = bankWorkerScreen.selectAccountType();
        Account reAccount = bankWorkerScreen.selectAccountIndex(reUser, reAccountType);
        Transaction transactionToBeUndone = bankWorkerScreen.selectTransaction(reAccount);
        undoTransaction(reAccount, transactionToBeUndone);
    }


    /**
     * Reads the alert.txt file to refill the bills in the atm which are have a quantity below 20.
     */
    private void fillATM(){                                 //Seems to work after I did some tests.
        String directory = "phase2/alert.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(directory))){
            String line = br.readLine();
            while (line != null){
                if (line.contains("Restock")){
                    String[] parts = line.split(":");
                    String lowDenomination = parts[1];
                    ATM.numberOfBills.put(lowDenomination, ATM.numberOfBills.get(lowDenomination) + 30);
                }
                line = br.readLine();
            }
            String fileContent = "Refill Completed.";
            BufferedWriter writer = new BufferedWriter(new FileWriter("phase2/alert.txt"));
            writer.write(fileContent);
            writer.close();
        }
        catch (FileNotFoundException e){
            System.out.println("Yikes, Something failed");
        }
        catch (IOException e){
            System.out.println("OOPS, something failed");
        }
    }

    /**
     * This method is used to unblock/approve a user account which has become blocked due to too many failed log in attempts.
     */
    private void approvedUser(){
        BankWorkerScreen bankWorkerScreen = new BankWorkerScreen();
        System.out.println("Which user do you want to approve");

        Person approvingUser = bankWorkerScreen.selectPerson();
        if (bankWorkerStatus == BankWorkerStatus.APPROVE_ACCOUNT) {
            approvingUser.loginAttempts = 0;
        }
        bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
    }

    /**
     * Takes an Account and a Transaction as parameters and reverts that transaction from that account. Then removes that
     * transaction from the account's 'listOfRecentTransactions' ArrayList.
     * @param a - represents an Account object
     * @param t - Represents a Transaction object
     */
    private void undoTransaction(Account a, Transaction t) {
        if (t.typeOfRecentTransaction == null) { return; }
        TransactionReversal tr = new TransactionReversal();
        TransactionType transaction = t.typeOfRecentTransaction;
        double amount = Double.parseDouble(t.moneymoving);
        switch (transaction){
            case DATI:{   Account[] info = {a, t.receivedFrom};
                            tr.DebtAccountTransferInReversal(info, amount);
                            break;}
            case DATO:{   Account[] info = {a, t.sentTo};
                            tr.DebtAccountTransferOutReversal(info, amount);
                            break;}
            case AATI:{   Account[] info = {a, t.receivedFrom};
                            tr.AssetAccountTransferInReversal(info, amount);
                            break;}
            case AATO:{   Account[] info = {a, t.sentTo};
                            tr.AssetAccountTransferOutReversal(info, amount);
                            break;}
            case SW:{     Account[] info = {a};
                            tr.SimpleWithdrawalReversal(info, amount);
                            break;}
            case SD:{     Account[] info = {a};
                            tr.SimpleDepositReversal(info, amount);
                            break;}
        }
        a.removeTransaction(t);
    }
}