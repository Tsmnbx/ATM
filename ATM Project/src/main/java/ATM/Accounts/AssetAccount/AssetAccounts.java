package main.java.ATM.Accounts.AssetAccount;

import main.java.ATM.Accounts.*;


public abstract class AssetAccounts extends Account {

    /**
     * Increases the balance of the account by the amount.
     *
     * @param amount The amount deposited.
     */
    public boolean deposit(String amount) {
        if (isNumeric(amount) && Double.parseDouble(amount) > 0) {
            balance += Double.parseDouble(amount);
            //Always returns true because you are always able to deposit.
            return true;
        } else {
            return false;
        }
    }
}


