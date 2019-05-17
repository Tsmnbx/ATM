package main.java.ATM.Accounts.AssetAccount.AssetAccountsChildren;

import main.java.ATM.Accounts.AssetAccount.*;

public class Chequing extends  AssetAccounts {

    public boolean is_primary;

     public Chequing(){
        balance = 0;
        is_primary = false;
        setDate();
        type = "Chequing";
    }

    public boolean getIs_primary(){
        return this.is_primary;
    }

    /**
     * @param value The value that we want to set is_primary too.
     */
    public void set_is_primary(boolean value){
        is_primary = value;
    }

    /**
     * Withdraws money from the checking account under certain conditions.
     * @param amount The amount to be withdrawn.
     */
    public boolean withdraw(String amount) {
        if (isNumeric(amount) && Double.parseDouble(amount) >= 0) {
            if (balance < 0) {
                balance += 0;
                System.out.println("This Asset Account currently has a negative balance. \n No transactions can be performed at this time");
                return false;
            } else if (Double.parseDouble(amount) - balance > 100) {
                System.out.println("This transaction would cause the balance of this AssetAccount to go below $-100. \n" +
                        "This is not permitted, the transaction has failed. \n" +
                        "Please be sure to enter a transaction amount that will have the balance stay above $-100.");
                return false;
            } else if (cashWithdrawCheckAct(amount)){
                balance -= Double.parseDouble(amount);
                return true;
            }
        }
        return false;
    }
}
