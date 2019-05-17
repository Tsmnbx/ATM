package main.java.ATM.Accounts.DebtAccount.DebtAccountsChildren;

import main.java.ATM.Accounts.DebtAccount.*;

import main.java.ATM.enums.UserStatus;

import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;

public class CreditCard extends DebtAccounts {


    public CreditCard(){
        balance = 0;
        type = "Credit Card";
    }

    public boolean withdraw(String amount){
        if (userStatus == UserStatus.WITHDRAW) {
            return false;
        }
        else {
            if (isNumeric(amount) && Double.parseDouble(amount) >= 0) {
                if ((balance + Double.parseDouble(amount)) > 3000) {
                    System.out.println("You have incurred the maximum amount of debt on this account, this transaction will not pass");
                    return false;
                }
                else {
                    balance += Double.parseDouble(amount);
                    return true;
                }
            }else{return false;}
        }
    }
}
