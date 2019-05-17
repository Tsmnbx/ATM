package main.java.ATM.Screens.ScreenChildren;


import main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren.UserCreator;
import main.java.ATM.Person.Persons.User_Elements.*;

import main.java.ATM.Person.Persons.BankWorkers.*;
import main.java.ATM.Person.*;

import main.java.ATM.SMS;
import main.java.ATM.enums.BankWorkerStatus;
import main.java.ATM.enums.Status;
import main.java.ATM.enums.UserStatus;
import main.java.ATM.Screens.*;
import main.java.ATM.ATM_Elements.*;

import static main.java.ATM.ATM_Elements.ATM.*;
import static main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren.BankManager.bankWorkerStatus;
import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;

public class LoginScreen extends Screen{

    public Person checkUsername(){
        System.out.println("\n");

        while (status == Status.USERNAME_CHECK || status == Status.USERNAME_RE_CHECK){
            changingAnsScreen();

            if (!allPeople.containsKey(ans) && commandList.contains(ans)) {
                ATMcomands ac = new ATMcomands(ans); // Processes ATM command

            } else if (ans.equals("1")){
                return new UserCreator();

            } else if (!allPeople.containsKey(ans)){ // allPeople Does not Contain usersname
                status = Status.USERNAME_RE_CHECK;

            } else if (allPeople.containsKey(ans)) { // allPeople contains username
                Person personLoggedIn = allPeople.get(ans);
                userAccessing(personLoggedIn);
            }
        }
        return allPeople.get(ans);
    }
    SMS Call_Example = new SMS();
    public void checkPassword(String password){
        while (status == Status.PASS_CHECK || status == Status.PASS_RE_CHECK){
            changingAnsScreen();

            if (ans.equals("e")) {
                System.out.println("Good Bye");
                status = Status.USERNAME_CHECK;

            } else if (ans.equals(password)){
                System.out.println("Access Granted");
                person.loginAttempts = 0;
                status = takeToPersonHome();

            } else if(person.loginAttempts == 2){
                person.loginAttempts += 2;
                System.out.println("\n YOU HAVE ENTERED THE WRONG PASSWORD TOO MANY TIMES GO TO LOCAL BRANCH AND VERIFY YOUR IDENTITY\n");
                Call_Example.call();
                status = Status.USERNAME_CHECK;
            }

            else {
                status = Status.PASS_RE_CHECK;
                person.loginAttempts += 1;
            }
        }
    }

    private void changingAnsScreen(){
        switch (status){
            case USERNAME_CHECK:    ans = commandATM("Enter username \n" +
                    "Or enter 1. to create a new User Account"); break;
            case USERNAME_RE_CHECK: ans = commandATM("Invalid username Try again" +
                    "Or enter 1. to create a new Username"); break;
            case PASS_CHECK:        ans = commandATM("Enter Password (enter \'e\' to exit)"); break;
            case PASS_RE_CHECK:     ans = commandATM("Invalid Password \nReenter Password (enter \'e\' to exit)"); break;
        }
    }

    private Status takeToPersonHome(){
        if (person instanceof User){
            userStatus = UserStatus.USER_HOME;
        } else if (person instanceof BankWorker) {
            bankWorkerStatus = BankWorkerStatus.BANK_WORKER_HOME;
        }
        return Status.NULL;
    }

    private void userAccessing(Person personLoggedIn){
        if (personLoggedIn.checkLoginAttemptsValid()) {
            status = Status.PASS_CHECK;
            System.out.println("Welcome to CSC207 financial " + ans);
        } else {
            System.out.println("Your account is blocked talk to your local bank manager\n");
            status = Status.USERNAME_CHECK;
        }
    }

}
