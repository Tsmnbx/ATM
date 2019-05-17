/**
 * Created by joshuaefe on 2019-02-25.
 */

package main.java.ATM.Accounts;

import main.java.ATM.ATM_Elements.ATMWithdraw;
import main.java.ATM.Transaction.*;
import main.java.ATM.enums.UserStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;

public abstract class Account {

    // Recent Transaction Information
   public ArrayList<Transaction> listOfRecentTransactions = new ArrayList(10);

    //Todo may add a joint status so a user can see all joint accounts

    public String type;

    public double balance;

    LocalDate date = LocalDate.now();
    LocalTime clockTime = LocalTime.now();
    String time = clockTime.toString() +" " +"on "+" " + date.toString();
    public String timeCreated = clockTime.toString() +" " +"on "+" " + date.toString();

    public boolean isJoint = false;
    public void addTransaction(Transaction t){
        if (listOfRecentTransactions.size() == 10){
            listOfRecentTransactions.remove(0);
            listOfRecentTransactions.add(t);
        }
        else{
            listOfRecentTransactions.add(t);
        }
    }

    public void removeTransaction(Transaction t){
        listOfRecentTransactions.remove(t);
    }

    public Object[] provideInformationForChoosingTransaction(){
        StringBuilder message = new StringBuilder("");
        int sizeOfList = listOfRecentTransactions.size() - 1;
        for (int i = 0; i < listOfRecentTransactions.size(); i++){
            String thingToAppend = i + "." + listOfRecentTransactions.get(i).toString() + "\n";
            message.append(thingToAppend);
        }
        Object[] informationForScreen = {message, sizeOfList};
        return informationForScreen;
    }

   public void setDate() {
       LocalDate date = LocalDate.now();
       LocalTime clockTime = LocalTime.now();
       this.timeCreated = clockTime.toString() +" " +"on "+" " + date.toString();
   }

   public void setDate(LocalDate date){
        this.date = date;
   }

    @Override // Needed to print primitive with strings
    public String toString() {
        Double holder = balance;
        return holder.toString();
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(String amount){return false;}

    public boolean deposit(String amount) {return false;}

    // This checks withdrawing cash from ATM, if yes only true if ATM supports action and ATM will update bills
    public boolean cashWithdrawCheckAct(String amount){
        double cashamount = Double.parseDouble(amount);
        if (userStatus == UserStatus.WITHDRAW) {
            ATMWithdraw atmWithdraw = new ATMWithdraw();
            return atmWithdraw.WithdrawContents(cashamount);
        }
        return true;
    }

    public boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }
}
