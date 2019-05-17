package main.java.ATM.Accounts.DebtAccount;


public abstract class DebtAccounts extends main.java.ATM.Accounts.Account{
    
    /**
     * Transfers money into the account.
     * @param amount the amount that wants to be transferred in.
     */
    public boolean deposit(String amount) {
        if (isNumeric(amount) && Double.parseDouble(amount) >= 0) {
            balance -= Double.parseDouble(amount);
            return true;
        }else{return false;}
    }
}
