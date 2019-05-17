package main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren;

import main.java.ATM.Person.Persons.User_Elements.*;

import main.java.ATM.Person.Persons.BankWorkers.*;

import main.java.ATM.Screens.ScreenChildren.BankWorkerScreen;
import main.java.ATM.enums.BankWorkerStatus;

import java.io.IOException;

/**
 * This is a BankAssistantManager. A child to the Bank Worker. it holds the functionality to create a new single user, process
 * account creation requests and process joint account creation requests.
 * Also can act as a user. Does this by having a field called actingAsUser. This field's boolean value changes to dictate
 * what the instance of this class is acting as.
 */
public class BankAssistantManager extends BankWorker {

    public static boolean actingAsUser = false;
    public User baUser;

    public BankAssistantManager(String username, String password){
        this.username = username;
        this.password = password;

        //The User Version of Bank Assistant Manager bing initialized, starts off acting as amb
        BankManager tempBm = new BankManager("no mater", "no mater");
        baUser = tempBm.createSingleUser(username + ".User", password);
        actingAsUser = false;
    }

    public User getUser(){
        return baUser;
    }

    /**
     * This act method is the primary method that is called by the program to perform tasks requests by the user.
     * @throws IOException - If there is failed input
     */
    @Override
    public void act() throws IOException {
        BankWorkerScreen bankWorkerScreen = new BankWorkerScreen();
        if (actingAsUser) {
            baUser.act();
        }
        else {
            if (bankWorkerStatus == BankWorkerStatus.BANK_WORKER_HOME) {
                bankWorkerScreen.bankAssistantManagerHome();
            } else {
                switch (bankWorkerStatus) {
                    case CREATE_USER:                       createUser();       break;
                    case ACCOUNT_CREATION_REQUEST:          accountCreation();  break;
                    case JOINT_ACCOUNT_CREATION_REQUEST:    accountJointCreation(); break;
                    case LOGIN_AS_USER:
                        actingAsUser = true;
                        bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
                        break;
                }
                bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
            }
        }
    }
}
