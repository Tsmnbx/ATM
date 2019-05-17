package main.java.ATM.ATM_Elements;

import main.java.ATM.Accounts.AssetAccount.AssetAccountsChildren.*;
import main.java.ATM.Person.Persons.User_Elements.*;
import main.java.ATM.Person.*;
import main.java.ATM.Accounts.*;

import main.java.ATM.enums.UserStatus;

import static main.java.ATM.ATM_Elements.ATM.allPeople;

public class ATMcomands {
    /*
    This class is used to artificially create circumstances in the ATM.
     */

    /**
     *Sets the circumstance: past or present
     * @param command
     */
    public ATMcomands(String command){
        switch (command){
            case "ATM.past"   :   bringToPast(1970); break;
            case "ATM.pres"  :   bringToPast(2019); break;
        }
    }

    /**
     *Sets the date to a previous time for a simulation
     * @param pastYear
     */
    private void bringToPast(int pastYear){
        System.out.println("Bringing time to: " + pastYear +"\n");
        for (String userName : allPeople.keySet()){     // Iterating over allPeople to Find Users
            Person user = allPeople.get(userName);
            if (user instanceof User){                  // person is a User
                for (Account saving : ((User) user).getAccountTypeList(UserStatus.SAVINGS)){ // Iterating over their savings accounts (Remark only changes savings accounts dates)
                    ((Savings)saving).setRecent_time(pastYear);   // sets date to Jan 1st futureYear
                }
            }
        }
    }
}
