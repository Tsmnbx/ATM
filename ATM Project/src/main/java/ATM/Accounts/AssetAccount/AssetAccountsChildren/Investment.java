package main.java.ATM.Accounts.AssetAccount.AssetAccountsChildren;

import main.java.ATM.Accounts.AssetAccount.*;
import main.java.ATM.Person.Persons.User_Elements.*;

import java.util.Collection;
import java.util.HashMap;

public class Investment extends AssetAccounts{
    public HashMap<String, UserStock> stocks = new HashMap<>(); //of the form <tickersymbol : stock object>

    public Investment(){
        balance = 10000;
        type = "Investment";
    }

    /**
     * Checks whether the stock is owned by the user.
     *
     * @param ticker The ticker you are trying to check.
     * @return whether the stock is owned by the user.
     */
    public boolean tickerInStocks(String ticker){
        for (String tickersymbol : stocks.keySet()){
            if(tickersymbol.equals(ticker)){
                return true;
            }
        }
        return false;
    }

    /**
     * Getter for the userstocks.
     *
     * @param ticker
     * @return
     */
    public UserStock getUsersStock(String ticker){
        if (tickerInStocks(ticker)){
            return stocks.get(ticker);
        }
        return null;
    }

    /**
     * Allows the purchase of a stock.
     *
     * @param ticker Ticker that is being bought.
     * @param num_shares  Number of shares they are buying.
     * @param cost Cost of the share.
     */
    public void addStock(String ticker, double num_shares, double cost){
        if ((balance - (num_shares * cost) < 0)){
            System.out.println("You do not have enough money in this Investment Account to make this transaction");
        }
        else {
            balance -= (num_shares * cost);
            int checkIfOwnStock = 0;
            for (String tickersymbol : stocks.keySet()) {
                if (tickersymbol.equals(ticker)) {
                    checkIfOwnStock++;
                    UserStock us = stocks.get(tickersymbol);
                    us.update_stock(num_shares, cost);
                    break;
                }
            }
            if (checkIfOwnStock == 0) {
                UserStock newStock = new UserStock(ticker, num_shares, cost);
                stocks.put(ticker, newStock);
            }
            System.out.println("Purchase Successful");
        }
    }

    /**
     * Allows for the sale of shares that you currently own.
     *
     * @param userStock The stock that the user is trying to sell.
     * @param num_shares_sold Number of shares sold.
     * @param price_sold_at Price sold at.
     */

    public void sellStock(UserStock userStock, double num_shares_sold, double price_sold_at){
        double moneyFromSale = num_shares_sold * price_sold_at;
        userStock.num_shares -= num_shares_sold;
        balance += moneyFromSale;
        System.out.println("You have sold " + num_shares_sold + " of "+ userStock.ticker + " \n" +
                "Your Investment chash balance has increased by: " + moneyFromSale + "\n");
        if (userStock.num_shares == 0){
            stocks.remove(userStock.ticker);
            System.out.println("You no longer have any shares of this stock.");
        }
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

    /**
     * Gets the user total stock balance for a user's portfolio.
     * @return Total value of stocks that the user has.
     */
    public double getStockBalance(){
        double total = 0;
        Collection<UserStock> stocksCollection = stocks.values();
        for (UserStock us : stocksCollection){
            double current_value = Double.parseDouble(us.checkCurrentPrice());
            total += ((us.num_shares) * (current_value));
        }
        return total;
    }

    /**
     * Returns total balance of a user's investment account
     * @return Total balnce. 
     */
    public double getTotalBalance(){
        return (getStockBalance() + balance);
    }

}
