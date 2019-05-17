package main.java.ATM;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is a WebScraper using the Jsoup external library downloaded online and added to this project structure.
 * This class is used to get stock information such as its Title, price, and rate of change.
 * This information will be used in a separate class.
 */
public class WebScraper {


    /**
     * This method takes a string which represents a Ticker Symbol for a stock as an argument, converts it to the proper
     * all uppercase form, and then uses the finance.yahoo web-page for that stock to get and return a URL object.
     *
     * @param tickerSymbol - string representation of the ticker symobol the user would like to examin
     * @return URL
     * @throws MalformedURLException - In case the URL does not work
     */
    private URL getURL(String tickerSymbol) throws MalformedURLException {
        String formattedTickerSymbol = tickerSymbol.toUpperCase();
        String requestedURL = "https://finance.yahoo.com/quote/" + formattedTickerSymbol;
        URL website = new URL(requestedURL);
        return website;
    }

    /**
     * This method uses the URL object created from the getURL method to create and return an HTML Document object.
     * This HTML Document holds all the necessary information about the Stock. Other methods will be used to properly
     * navigate this Document object to acquire necessary information.
     * @param website - takes a website URL object as an argument
     * @return Document
     * @throws IOException - in case the input website URL object does not process properly
     */
    private Document getHTMLDocument(URL website) throws IOException {
        Document HTMLDoc = Jsoup.parse(website, 25000);
        return HTMLDoc;
    }


    /**
     * This method returns a String whose value is the formal title of the Stock associated with the provided TickerSymbol as the argument.
     * @param tickersymbol String representation of the tickersymbol the user would like to examine
     * @return String of the formal title associated with the Stock provided Ticker Symbol
     */
    String getTitle(String tickersymbol){
        try{
            URL websiteForStock = getURL(tickersymbol);
            try{
                Document HTMLDocForStock = getHTMLDocument(websiteForStock);
                return HTMLDocForStock.title();
            }
            catch (IOException e){
                System.out.println("Failed to get title of Stock with Provided Ticker Symbol" + e);
            }
        }
        catch (MalformedURLException MURLE){
            System.out.println("Failed to get title of Stock with Provided Ticker Symbol" + MURLE);
        }
        return "Failed to acquire Stock title";
    }


    /**
     * This method returns a String whose value is the current price of the Stock associated with the provided Ticker Symbol as the argument.
     * @param tickersymbol String representation of the tickersymbol the user would like to examine
     * @return String of the current price of the Stock associated with the provided Ticker Symbol
     */
    public String getPrice(String tickersymbol){
        String result =  "";
        try{
            URL websiteForStock = getURL(tickersymbol);
            try{
                Document HTMLDocForStock = getHTMLDocument(websiteForStock);
                Element body = HTMLDocForStock.body();
                Elements info = body.select("span[data-reactid = 34]");
                Element elementContainingPrice = info.last();
                String price = elementContainingPrice.text();
                result = price;
            }
            catch (IOException e){
                System.out.println("Failed to get price of Stock with Provided Ticker Symbol" + e);
            }
        }
        catch (MalformedURLException MURLE){
            //System.out.println("Failed to get price of Stock with Provided Ticker Symbol" + MURLE);
        }
        if ((result.contains("(")) || (result.equals(""))) {
            return "Failed to acquire Stock price";
        }
        return result;

    }


    /**
     * This method returns a String whose value is the current change in the stock associated with the provided Ticker Symbol as the argument.
     * @param tickersymbol String representation of the tickersymbol the user would like to examine
     * @return String of the Change in the Stock, its +/-
     */
    String getChangeInStock(String tickersymbol){
        try{
            URL websiteForStock = getURL(tickersymbol);
            try{
                Document HTMLDocForStock = getHTMLDocument(websiteForStock);
                Element body = HTMLDocForStock.body();
                Elements info = body.select("span[data-reactid = 35]");
                String dataofchangeovertime = info.text();
                return dataofchangeovertime;
            }
            catch (IOException e){
                System.out.println("Failed to get change of Stock with Provided Ticker Symbol" + e);
            }
        }
        catch (MalformedURLException MURLE){
            System.out.println("Failed to get change of Stock with Provided Ticker Symbol" + MURLE);
        }
        return "Failed to acquire change in stock";
    }

    public boolean isInMarket(String ticker){
        String message = getPrice(ticker);
        return !(message.equals("Symbol Lookup from Yahoo Finance") ||
                message.equals("Failed to acquire Stock price") ||
                message.equals("Please check your spelling. Try our suggested matches or see results in other tabs."));
    }


//    public static void main(String[] args){
//        WebScraper ws = new WebScraper();
//        String titleOfStock = ws.getTitle("aapl");
//        String stockPrice = ws.getPrice("aapl");
//        String changeInStock = ws.getChangeInStock("aapl");
//        System.out.println(titleOfStock);
//        System.out.println(stockPrice);
//        System.out.println(changeInStock);
//    }
}
