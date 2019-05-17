package main.java.ATM.Accounts.AssetAccount.AssetAccountsChildren;

import main.java.ATM.Accounts.AssetAccount.*;

import java.time.LocalDateTime;

public class Savings extends AssetAccounts {

    public LocalDateTime recent_time;
    public static LocalDateTime current_time = LocalDateTime.now();

    public Savings(){
        balance = 0;
        type = "Savings";
    }

    @Override
    public String toString(){
        this.determine_interest();
        return super.toString();
    }

    /**
     * Sets the recent time to 1/1/year
     * @param year the year that you want to set the time to.
     */
    public void setRecent_time(Integer year){
        recent_time = LocalDateTime.of(year, 1, 1, 1, 1); // Day, Month Minutes.. arbitrarily 1
    }

    /**
     * Determines the amount of interest that is added depending on the amount of time passed.
     */
    public void determine_interest(){
        setRecent_timeIfNull();
        int current_val = (12*current_time.getYear()) + current_time.getMonthValue();
        int recent_val = (12 *recent_time.getYear() + recent_time.getMonthValue());
        if(current_val > recent_val){
            for(int i = 0; i < current_val - recent_val; i++){
                this.add_interest();
            }
        }
        recent_time = LocalDateTime.now();
    }

    public void setRecent_timeIfNull(){
        if (recent_time == (null)){
            recent_time = LocalDateTime.now();
        }
    }

    /**
     * Overrides deposit in asset acount to allow for savings account to determine interest.
     *
     * @param amount The amount deposited.
     * @return Whether the transaction is valid.
     */
    public boolean deposit(String amount) {
        this.determine_interest();
        if (isNumeric(amount) && Double.parseDouble(amount) > 0) {
            balance += Double.parseDouble(amount);
            //Always returns true because you are always able to deposit.
            return true;
        } else {
            return false;
        }
    }

    /**
     * Savings implementation of withdraw.
     * @param amount The amount the user wants to withdraw.
     */
    public boolean withdraw(String amount){
        this.determine_interest();
        if(isNumeric(amount) && Double.parseDouble(amount) >= 0) {
            if (Double.parseDouble(amount) > balance) {
                balance += 0;
                System.out.println("This transaction would cause the balance of this AssetAccount to go negative. \n" +
                        "This is not permitted, the transaction has failed. \n" +
                        "Please be sure to enter a transaction amount that will have the balance stay above $0.00.");
                return false;
            } else if (cashWithdrawCheckAct(amount)){
                balance -= Double.parseDouble(amount);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds .1% interest if it is the first day of the month.
     */
    public void add_interest(){
            balance += (balance * .001);
        }
}
