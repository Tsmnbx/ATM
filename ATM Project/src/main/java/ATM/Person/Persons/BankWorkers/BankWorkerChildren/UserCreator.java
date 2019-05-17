package main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren;

import java.util.Random;
import main.java.ATM.Person.Persons.BankWorkers.BankWorker;
import main.java.ATM.Screens.ScreenChildren.BankWorkerScreen;
import main.java.ATM.enums.BankWorkerStatus;
import main.java.ATM.enums.Status;

import static main.java.ATM.ATM_Elements.ATM.status;

/**
 * Contains the functionality to request for the creation of a new user from the log in screen. The request must be
 * approved by the BankManager.
 */
public class UserCreator extends BankWorker {

    private String newUserName;
    private String newPass;
    public String securityCode ;
    private BankWorkerScreen bms = new BankWorkerScreen();

    public UserCreator(){
        status              = Status.NULL;
        bankWorkerStatus    = BankWorkerStatus.CREATE_USERNAME;
        securityCode        = Integer.toString(10000 + new Random().nextInt(90000));
    }

    @Override
    public void act(){
        switch (bankWorkerStatus){
            case BANK_WORKER_HOME:  bankWorkerStatus = BankWorkerStatus.NULL;
                                    status = Status.USERNAME_CHECK;             break;
            case CREATE_USERNAME:   newUserName = bms.createUsername();         break;
            case CREATE_PASSWORD:   creatPass();                                break;
            case CODE_CHECK:        codeCheck();                                break;
        }
    }

    /**
     * Creates a password for the new User
     */
    private void creatPass(){
        newPass = bms.createPassword();
        bankWorkerStatus = BankWorkerStatus.CODE_CHECK;

        codeCheck();
        bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
    }

    /**
     * Checks to make sure the security code is correct
     */
    private void codeCheck(){
        if (bms.verifyCode(securityCode) || bms.verifyCode("1234")){
            userMade(newUserName, newPass);
            System.out.println("Your request for a new User has been made. Please wait for the Bank Manager to process it.");
        }
    }
}
