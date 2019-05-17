package main.java.ATM.Screens.ScreenChildren.UserScreens;

import main.java.ATM.Accounts.AssetAccount.AssetAccountsChildren.Investment;
import main.java.ATM.Person.Persons.User_Elements.User;
import main.java.ATM.Person.Persons.User_Elements.UserStock;
import main.java.ATM.WebScraper;
import main.java.ATM.enums.UserStatus;

import static main.java.ATM.Person.Persons.User_Elements.User.userStatus;

/**
 * This class is screen the displays the options for investment based actions
 */
public class UserInvestmentScreen extends UserScreen {

    /**
     * Displays the options for investment and changes the userStatus for the appropriate option
     * @param user The User currently using this ATM
     */
    public void investHome(User user){
        printInvestmentMsg(user);
        boolean ansValid = false;

        while (!ansValid) {
            ans = commandATM(
                    "1. Buy Stocks\n" +
                    "2. Sell Stocks\n" +
                    "3. exit");
            switch (ans) {
                case "1": userStatus = UserStatus.INVEST_BUY;   ansValid = true;    break;
                case "2": userStatus = UserStatus.INVEST_SELL;  ansValid = true;    break;
                case "3": userStatus = UserStatus.USER_HOME;    ansValid = true;    break;
            }
            if (!ansValid) {
                System.out.println("Invalid input try again");
            }
        }
    }

    /**
     * Allows the user to sell stocks
     * @param user
     */
    public void sellStock(User user){
        // creating temporary objects for use in method
        Investment investAc     =   (Investment) user.getAccount(UserStatus.INVESTMENT, 0);
        WebScraper webScraper   =   new WebScraper();

        while (userStatus == UserStatus.INVEST_SELL){
            ans = commandATM("Enter Ticker of Stock you would like to sell, enter \"e\" to exit");

            if (investAc.tickerInStocks(ans)){
                String      price           = webScraper.getPrice(ans);
                UserStock   sellingStock    = investAc.getUsersStock(ans);
                Integer     numStocks       = getAmmount(UserStatus.INVEST_SELL, price);
                double      priceSold       = Double.parseDouble(price);

                investAc.sellStock(sellingStock, numStocks, priceSold);
                userStatus = UserStatus.INVEST_HOME;

            } else if (ans.equals("e")){
                userStatus = UserStatus.INVEST_HOME;
            } else if (userStatus == UserStatus.INVEST_SELL){
                System.out.println("Invalid input try again");
            }
        }
    }

    /**
     * Allows the user to buy stocks
     * @param user
     */
    public void buyStock(User user){
        // creating temporary objects for use in method
        Investment investAc     =   (Investment) user.getAccount(UserStatus.INVESTMENT, 0);
        WebScraper webScraper   =   new WebScraper();

        while (userStatus == UserStatus.INVEST_BUY){
            ans = commandATM("Enter Ticker of Stock you would like to buy, enter \"e\" to exit");

            if (webScraper.isInMarket(ans)){
                String ticker = ans;
                String price = webScraper.getPrice(ans);
                Integer numStocks = getAmmount(UserStatus.INVEST_BUY, price);
                double stockPrice = Double.parseDouble(price);
                investAc.addStock(ticker, numStocks, stockPrice);
                userStatus = UserStatus.INVEST_HOME;
            } else if (ans.equals("e")){
                userStatus = UserStatus.INVEST_HOME;
            } else if (userStatus == UserStatus.INVEST_BUY){
                System.out.println("Invalid input try again");
            }
        }
    }

    /**
     * Allows the User to choose the amount of a certain type of stock
     * @param exchange The ENUM for what you want to do(Buy or Sell)
     * @param price The String price of the Stock
     * @return
     */
    private Integer getAmmount(UserStatus exchange, String price){
        boolean ansValid = false;

        while (!ansValid) {
            if (exchange == UserStatus.INVEST_BUY) {
                ans = commandATM("How many stock would you like to buy for price: " + price + " (may enter 0):");
            } else if (exchange == UserStatus.INVEST_SELL) {
                ans = commandATM("How many stocks would you like to sell for price: " + price + " (may enter 0):");
            }
            if (isNumeric(ans)){
                ansValid = true;
            }
        }
        return Integer.parseInt(ans);
    }

    /**
     * Displays the summary of the various balances and your investment accounts current state
     * @param user The User using this ATM
     */
    private void printInvestmentMsg(User user){
        // Assuming we only have 1 investment account
        Investment investAc = (Investment) user.getAccount(UserStatus.INVESTMENT, 0);

        System.out.println("------------------ INVESTMENTS ---------------------\n" +
                "Your Cash balance is:      " + investAc.getBalance() +      "\n" +
                "Your stock balance is:     " + investAc.getStockBalance() + "\n" +
                "Your overall balance is:   " + investAc.getTotalBalance() + "\n" +
                "\n" +
                          "----------------- Your stocks are ------------------");
        System.out.printf("%-10s %-10s %-18s %-18s\n", "Ticker", "Quantity", "Weighted Price", "Total Value");

        for (String ticker : investAc.stocks.keySet()){
            UserStock stock = investAc.stocks.get(ticker);
            System.out.printf("%-10s %-10s %-18s %-18s\n", ticker, stock.num_shares, stock.weighted_cost, stock.weighted_cost*stock.num_shares);
        }
        System.out.println();
    }
}
