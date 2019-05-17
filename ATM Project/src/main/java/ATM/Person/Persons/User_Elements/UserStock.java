package main.java.ATM.Person.Persons.User_Elements;



import  main.java.ATM.*;

/**
 * This class exists to hold information about stocks which are bought via the investment functionality of the program.
 */
public class UserStock {
    public WebScraper ws = new WebScraper();
    public String ticker;
    public double num_shares;
    public double weighted_cost;

    public UserStock(String ticker, double num_shares, double purchase_price){
        this.ticker = ticker;
        this.num_shares = num_shares;
        this.weighted_cost = purchase_price;
    }

    /**
     * Fixes the UserStock parameters to account for the purchase of some new shares of this stock.
     * @param new_num_shares - the new shares bought
     * @param new_purchase_price - the new price of the stocks being purchased
     */
    public void update_stock(double new_num_shares, double new_purchase_price){
        this.num_shares = num_shares + new_num_shares;
        this.weighted_cost = ((num_shares * weighted_cost) + (new_num_shares * new_purchase_price)/(num_shares + new_num_shares));
    }


    /**
     * Checks the current price of the stock.
     * @return String which represents the current price of the stock.
     */
    public String checkCurrentPrice(){
        String price = ws.getPrice(ticker);
        return price;
    }

    @Override
    public String toString() {
        return("Ticker:" + ticker + "Quantity:" + num_shares + "Cost:" + weighted_cost);
    }


    /**
     * Getter of the price which this stock was purchased at.
     * @return double which represents the purchased price of this stock.
     */
    public double getPurchase_price() {
        return weighted_cost;
    }


    /**
     * Checks the current profit or loss on this stock.
     * @return String which represents the current profit or loss on this stock
     */
    public String profitOnStock(){
        String price = checkCurrentPrice();
        double differenceInPrice = Double.parseDouble(price) - weighted_cost;
        return Double.toString(differenceInPrice);
    }
}
