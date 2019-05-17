package main.java.ATM.ATM_Elements;

import java.util.HashMap;

import static main.java.ATM.ATM_Elements.ATM.numberOfBills;

public class ATMWithdraw {
    /*
    This class is used to withdraw cash from ATM
    and make sure this amount of money is supported
     */

    /**
     * This field stores the number of bills of each type for the withdrawl
     */
    public HashMap<String, Integer> newNumberOfBills;
    public double returnAmount;

    public ATMWithdraw(){
        newNumberOfBills = numberOfBills;
        returnAmount = 0;

    }

    /**
     * Determines if the withdrawal is legal
     * @param amount
     * @return
     */
    public boolean WithdrawContents(double amount){
        if (amount % 5 == 0){// bear minimum requirement
            for (String billType : new String[]{"50", "20", "10", "5"}){
                whileUnder(amount, billType);
            }
        }
        if (amount == returnAmount){
            numberOfBills = newNumberOfBills;
            return true;
        } else {
            System.out.println("ATM does not support the withdraw amount");
            return false;
        }
    }

    /**
     * Helper method of WithdrawContents
     * @param amount
     * @param billType
     */
    public void whileUnder(double amount, String billType){
        double billValue = Integer.parseInt(billType);

        while ((amount >= (returnAmount + billValue)) && newNumberOfBills.get(billType) > 0){
            returnAmount += billValue;
            newNumberOfBills.put(billType, newNumberOfBills.get(billType) - 1); // Updates the the new ATM balance
        }
    }
}
