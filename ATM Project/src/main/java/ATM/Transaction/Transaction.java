package main.java.ATM.Transaction;


import main.java.ATM.Accounts.*;

import main.java.ATM.enums.TransactionType;

/**
 * This class holds the information for one instance of a transaction. Transaction Objects are created every time any transaction occurs.
 * It is used in-case the BankManager wants to undo a transaction. It accesses the Transaction object's fields and performs the 'undo'
 * accordingly.
 */
public class Transaction {

    // This class holds an instance of a transaction alongside it's transaction Information
    public TransactionType typeOfRecentTransaction;
    public Account receivedFrom;
    public Account sentTo;
    public String moneymoving;

    public Transaction(TransactionType typeOfRecentTransaction, Account receivedFrom, Account sentTo, String moneymoving){
        this.typeOfRecentTransaction = typeOfRecentTransaction;
        this.receivedFrom = receivedFrom;
        this.sentTo = sentTo;
        this.moneymoving = moneymoving;
    }

    /**
     * This helper method is used to provide necessary information to the toString to properly word the transaction information.
     * @return - an object array which holds information like: amount of money for the transaction, string of type of transaction, a potential concatenating word, and
     * a user who received or sent the money.
     */
    public Object[] removeAbbreviation(){
        String extendedAbbreviation = "";
        String concatenatingWord = "";
        Account accountInQuestion = null;
        switch (typeOfRecentTransaction){
            case SW:
                extendedAbbreviation = "Withdrawal";
                break;
            case SD: {
                extendedAbbreviation = "Deposit";
                break;}
            case DATO: {
                extendedAbbreviation = "Transfer out";
                concatenatingWord = "to";
                accountInQuestion = sentTo;
                break;}
            case DATI: {
                extendedAbbreviation = "Transfer in";
                concatenatingWord = "from";
                accountInQuestion = receivedFrom;
                break;}
            case AATI: {
                extendedAbbreviation = "Transfer in";
                concatenatingWord = "from";
                accountInQuestion = receivedFrom;
                break;}
            case AATO: {
                extendedAbbreviation = "Transfer out";
                concatenatingWord = "to";
                accountInQuestion = sentTo;
                break;}
        }
        Object[] a = {extendedAbbreviation,concatenatingWord,accountInQuestion};
        return a;

    }

    @Override
    public String toString() {
        Object[] wordsForSentence = removeAbbreviation();
        //return wordsForSentence[0] + moneymoving + wordsForSentence[1] + wordsForSentence[2].toString();
        return wordsForSentence[0] +": "+  moneymoving;
    }
}
