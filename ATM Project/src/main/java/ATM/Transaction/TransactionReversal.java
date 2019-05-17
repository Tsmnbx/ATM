package main.java.ATM.Transaction;

import main.java.ATM.Accounts.AssetAccount.*;
import main.java.ATM.Accounts.*;
import main.java.ATM.Accounts.DebtAccount.*;

/**
 * This class exists to hold the necessary methods which revert the transactions for each type of account.
 * To reduce duplicate code, methods are re-used along side altered arguments as a simple way to perform similar tasks.
 */
public class TransactionReversal {

    public void DebtAccountTransferInReversal(Account[] info, double amount){
        DebtAccounts da = (DebtAccounts) info[0];
        Account secondaryAcc = info[1];
        da.balance += amount;
        if (secondaryAcc instanceof DebtAccounts) {
            secondaryAcc.balance -= amount;
        }
        else{
            secondaryAcc.balance += amount;
        }
    }

    public void DebtAccountTransferOutReversal(Account[] info, double amount){
        DebtAccountTransferInReversal(info, -amount);
    }

    public void AssetAccountTransferInReversal(Account[] info, double amount){
        AssetAccounts aa = (AssetAccounts) info[0];
        Account secondaryAcc = info[1];
        aa.balance -= amount;
        if (secondaryAcc instanceof DebtAccounts) {
            secondaryAcc.balance -= amount;
        }
        else{
            secondaryAcc.balance += amount;
        }
    }

    public void AssetAccountTransferOutReversal(Account[] info, double amount){
        AssetAccountTransferInReversal(info, -amount);
    }

    public void SimpleWithdrawalReversal(Account[] info, double amount){
        Account a = info[0];
        if (a instanceof DebtAccounts){ a.balance -= amount; }
        else{ a.balance += amount; }
    }

    public void SimpleDepositReversal(Account[] info, double amount){
        SimpleWithdrawalReversal(info, -amount);
    }

}
