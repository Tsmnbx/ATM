package main.java.ATM.Accounts.DebtAccount.DebtAccountsChildren;

import main.java.ATM.Accounts.DebtAccount.*;


public class LineofCredit extends DebtAccounts {

    public LineofCredit(){
        balance = 0;
        type = "Line of Credit";
    }

    /**
     * Transfers money out of the account.
     * @param amount the amount that wants to be transferred out.
     */
    public boolean withdraw(String amount) {
        if (isNumeric(amount) && Double.parseDouble(amount) >= 0) {
            if ((balance + Double.parseDouble(amount)) > 3000) {
                System.out.println("You have incurred the maximum amount of debt on this account, this transaction will not pass");
                return false;
            }
            else{
                balance += Double.parseDouble(amount);
                return true;
            }
        }else{return false;}
    }

}
