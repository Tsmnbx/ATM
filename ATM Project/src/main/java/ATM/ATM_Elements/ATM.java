package main.java.ATM.ATM_Elements;

import main.java.ATM.Person.Persons.BankWorkers.BankWorkerChildren.*;

import main.java.ATM.Screens.ScreenChildren.*;
import main.java.ATM.Screens.*;
import main.java.ATM.Person.Persons.User_Elements.*;
import main.java.ATM.Person.*;


import main.java.ATM.enums.Status;

import java.util.HashMap;
import java.io.*;

/**
 * The Central Class in which the program is run through, the ATM, it has the various Person objects Stored in the allPeople
 * filed undergo their various operations, such as refilling the ATM or making transactions
 */
public class ATM {

    // Static Fields
    public static Status status = Status.USERNAME_CHECK;
    public static Person person;

    public static HashMap<String, Integer> numberOfBills = new HashMap<String, Integer>();
    public static HashMap<String, Person> allPeople = new HashMap<String, Person>();

    // Static Objects

    /**
     * This method is to set up the ATM with bills, a Bank manger and print the Welcome Screen
     * @param theBankManager The Bank manager of this ATM
     */
     private static void setUp(BankManager theBankManager){
        initNumberOfBills();
        allPeople.put(theBankManager.getUsername(), theBankManager);
        status = Status.USERNAME_CHECK;
        BankAssistantManager abm = new BankAssistantManager("abm", "123");
        Screen.printWelcome();

        // This is purely for testing and is relevant to the Read me text
        User user1 = theBankManager.createSingleUser("user1", "abc");
        User user2 = theBankManager.createSingleUser("user2", "123");

        allPeople.put("user1", user1);
        allPeople.put("user2", user2);
        allPeople.put("bm", theBankManager);
        allPeople.put("abm", abm);
    }

    /**
     * Sets up the initial number of bills in the ATM
     */
    private static void initNumberOfBills() {
        for (String typeBill : new String[]{"5", "10", "20", "50"} ){
            numberOfBills.put(typeBill, 40);
        }
    }

    /**
     * throw the alert to alert text file
     * @param i
     */
    private static void writeFile(String i){
        String directory = "phase2/alert.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(directory))){
            String content = "Restock the quantity:" +i+ "\n";
            bw.write(content);
            bw.close();
        }
        catch(IOException e){
            System.out.println("Exception occurred:");
            e.printStackTrace();
        }
    }

    //if any of $5, $10, $20, and $50 bills below $20, throw an alert to alert text file
    public static void below20(){
        for (String typeBill : numberOfBills.keySet()) {
            if (numberOfBills.get(typeBill) < 20) {
                writeFile(typeBill);
            }
        }
    }

    /**
     * THe main method in which the project is run
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        BankManager theBankManager = new BankManager("bm", "123");
        setUp(theBankManager);
        LoginScreen loginScreen = new LoginScreen();

        //noinspection InfiniteLoopStatement (Expected to run an infinenet amount of times)
        while (true) {
            switch (status){
                case USERNAME_CHECK: person = loginScreen.checkUsername();              break;
                case PASS_CHECK:     loginScreen.checkPassword(person.getPassword());   break;
                case NULL:          person.act();                                       break;
            }
        }
    }
}