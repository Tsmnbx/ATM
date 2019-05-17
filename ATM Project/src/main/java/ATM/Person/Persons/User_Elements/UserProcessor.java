package main.java.ATM.Person.Persons.User_Elements;


import main.java.ATM.Person.Persons.BankWorkers.*;

import main.java.ATM.Screens.ScreenChildren.*;
import main.java.ATM.Accounts.AssetAccount.AssetAccountsChildren.*;
import main.java.ATM.Person.Persons.User_Elements.*;
import main.java.ATM.Accounts.*;
import main.java.ATM.Accounts.DebtAccount.DebtAccountsChildren.*;
import main.java.ATM.Accounts.DebtAccount.*;
import main.java.ATM.Transaction.*;
import main.java.ATM.*;

import main.java.ATM.enums.TransactionType;
import main.java.ATM.enums.UserStatus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static main.java.ATM.enums.UserStatus.*;

public class UserProcessor {
    /*
    This class contains all the methods User, UserActChooser and UserScreen needs to process its various functions
     */

    /**
     * Allows you to withdraw money from the ATM and displays on the screen is its a valid transaction
     * @param source An Account your withdrawing from
     * @param amount The amount you are withdrawing from the Account as a String
     */
    public void cashWithdraw(Account source, String amount){
        boolean validTransaction = source.withdraw(amount);
        if (validTransaction) {
            Transaction t = new Transaction(TransactionType.SW, null, null, amount);
            source.addTransaction(t);
        }
        PeopleScreens.transactionValid(validTransaction);
    }

    /**
     *Allows you to send money from one account to another in the Same User's accounts.
     * @param accSending The Account your are sending money from
     * @param accGetting The Account you are receiving money from
     * @param amount The String which is the amount your transferring
     */
    void selfTransfer(Account accSending, Account accGetting, String amount){
        boolean validTransaction = accSending.withdraw(amount);
        if (validTransaction) {
            accGetting.deposit(amount);
            TransactionType typeForSender = (accSending instanceof DebtAccounts) ? TransactionType.DATO : TransactionType.AATO;
            TransactionType typeForGetter = (accGetting instanceof DebtAccounts) ? TransactionType.DATI : TransactionType.AATI;
            Transaction transactionForSender = new Transaction(typeForSender, null, accGetting, amount);
            Transaction transactionForGetter = new Transaction(typeForGetter, accSending, null, amount);
            accSending.addTransaction(transactionForSender);
            accGetting.addTransaction(transactionForGetter);
        }
        PeopleScreens.transactionValid(validTransaction);
    }

    /**
     * Allows for the transferring of money from
     * @param source The Account you are going to take the money from
     * @param otherUserTargetAcc The other User's Account you are giving the money too
     * @param amount The String Amount of money you are moving
     */
    void transferMoneyToUser(Account source, Account otherUserTargetAcc, String amount) {
        boolean validTransaction = source.withdraw(amount);
        if (validTransaction) {
            otherUserTargetAcc.deposit(amount);
            TransactionType typeOfSource = (source instanceof DebtAccounts) ? TransactionType.DATO : TransactionType.AATO;
            TransactionType typeOfOtherUser = (otherUserTargetAcc instanceof DebtAccounts) ? TransactionType.DATI : TransactionType.AATI;
            Transaction transactionForSource = new Transaction(typeOfSource, null, otherUserTargetAcc, amount);
            Transaction transactionForOtherUser = new Transaction(typeOfOtherUser,source, null, amount);
            source.addTransaction(transactionForSource);
            source.addTransaction(transactionForOtherUser);

        }
        PeopleScreens.transactionValid(validTransaction);
    }

    /**
     * Allows for the payment of a bill which in the specifications is defined as non-user account
     * @param source The Account you are going to take the money from
     * @param address The Name of the person/organization/etc that the money is going to
     * @param amount The String amount of what is being payed
     * @throws IOException
     */
    void payBill(Account source, String address, String amount) throws IOException {
        boolean validTransaction = source.withdraw(amount);

        if (validTransaction) {
            String billStatement = "Bill for:" + address + "\n" + "Amount:" + amount + "\n" + "From Account:" + source.type;
            System.out.println(billStatement);
            BufferedWriter writer = new BufferedWriter(new FileWriter("outgoing.txt"));
            writer.write(billStatement);
            writer.close();
        }
        PeopleScreens.transactionValid(validTransaction);
    }

    /**
     *Allow you to deposit money into a specific account
     * @param acc The Account you are depositing money into
     * @param amount The String amount of what is being put into the account
     * @throws IOException
     */
    public void deposit(Account acc, String amount)throws IOException{
        acc.deposit(amount);
        Transaction t = new Transaction(TransactionType.SD, null, null, amount);
        acc.addTransaction(t);
        if (!amount.equals("e")) {
            String depositStatement = "Depositing:" + amount + "\n" + "To Account:" + acc.type;
            System.out.println(depositStatement);
            BufferedWriter writer = new BufferedWriter(new FileWriter("deposit.txt"));
            writer.write(depositStatement);
            writer.close();
        }
    }

    /**
     * Allows for the depositing of money which automatically goes into your only Chequing account if there is only
     * one Chequing account or the primary Chequing account if there is more than one.
     * @param amount The String amount of what is being put into the account
     * @param user The User which is currently Using the ATM
     * @throws IOException
     */
    public void quickDeposit(String amount, User user) throws IOException {
        for (Account cheqeuacc : user.accounts.get(UserStatus.CHEQUING)) {
            if (((Chequing) cheqeuacc).getIs_primary()) {
                deposit(cheqeuacc, amount);
                return;
            }
        }
        deposit(user.accounts.get(UserStatus.CHEQUING).get(0), amount);
    }

    /**
     * Allows the User to Request the Creation of a new Account of any tye to add to its Accounts
     * @param user The User which is currently Using the ATM
     * @param accountType The ENUM which is one of the five various account Types and the type of Account you are
     *                   creating
     */
    public void requestAccountCreation(User user, UserStatus accountType){
        Requests accountRequest = new Requests();
        accountRequest.requestAccountCreation(accountType, user);
        BankWorker.accountRequests.add(accountRequest);
        if(accountType == UserStatus.CHEQUING){
            Chequing newCheckingAccount = (Chequing)user.getAccount(UserStatus.CHEQUING, 0);
            newCheckingAccount.set_is_primary(true);                                                                    // Worried this may cause problems in the future**
        }
    }

    /**
     * Allows a User to pick which Chequing Account is the Primary Account, the automatic location for a quick deposit
     * @param user The User which is currently Using the ATM
     * @param account The Account in which the User would like to make Primary
     */
    void makePrimary(User user, Chequing account){
        for(Account c : user.accounts.get(CHEQUING)) {
            ((Chequing) c).set_is_primary(false);
        }
        account.set_is_primary(true);
    }

    /**
     * Allows to request to make an existing Account Joint with another User, this adds a Request into their
     * jointRequest field(see User for details on the jointRequest field)
     * @param acc The Account to make Joint
     * @param targetUser The User that is being sent the Request
     * @param sourceUser The User that is sending the Request
     */
    public void makeAccountJoint(Account acc, User targetUser, User sourceUser){
		Requests makeJointRequest = new Requests();
        makeJointRequest.makeAJointRequest(acc, targetUser, sourceUser);
       targetUser.jointRequest.add(makeJointRequest);
       System.out.println("Joint account request set to" + " "+ makeJointRequest.userB.username);
    }

    /**
     * Sends a Request to the BankWorkers accountJointRequests field(see BankWorker for details on
     * accountJointRequests field) this request contains the account and the users involved
     * @param accountType The ENUM that contains what type of Account is being request create and join
     * @param sourceUser The User that is sending the Request
     * @param targetUser The User that is being sent the Request
     */
    public void requestJointAccountCreation(UserStatus accountType, User sourceUser, User targetUser){ // Accoutn TYpe ENUM, USER A(Requesting), USER B(One it Request to), USER B Accounts
		Requests newAccountJointRequest = new Requests();
        newAccountJointRequest.makeNewJointAccountRequest(accountType, sourceUser, targetUser);
        BankWorker.accountJointRequests.add(newAccountJointRequest);
        if(accountType == UserStatus.CHEQUING){
            Chequing newCheckingAccount = (Chequing)sourceUser.getAccount(UserStatus.CHEQUING, 0);
            newCheckingAccount.set_is_primary(true);                                                                    // Worried this may cause problems in the future**
        }
    }

    /**
     * Allows a User to see Request of pre-existing Accounts from other Users
     * @param user The user using this ATM
     */
    public void viewJointRequest(User user){
        System.out.println("These are your request:");
        String allRequest = "";
        ArrayList<Requests> request = user.jointRequest;
        for(int i = 0; i<= request.size()-1;i++){
            Requests info = request.get(i);
            Account a = info.account;
            User friend = info.userA;
            String add = "\n"+ (i+1)+ ")"+ " " + "Joint request from" + " " + friend.username+ " for a"+" "+ a.type + " "+"account"+ "\n";
            allRequest = allRequest + add;
        }

        System.out.println(allRequest);
    }

    /**
     * A helper Method that Allows a User to get the Request in its jointRequest field (see User for detail's on
     * jointRequest field)
     * @param user The User using this ATM
     * @param index The index of the Requests in the jointRequest field of the User
     * @return requests The Request that is at the index of the jointRequest
     */
    public Requests getRequest(User user, int index) {
        if (index <= user.jointRequest.size()-1){
        return user.jointRequest.get(index);
    }
        else{
            System.out.println("Request Does Not Exist, YOU DON'T HAVE THAT MANY FRIENDS!" );
            return null;
        }
    }

    /**
     * Helper method to add a Joint Account to a User's Account HashMap
     * @param jointInfo The Request with the information on the Account being added and the user its from
     * @param user THe User using this ATM
     */
    public void addJointAccount(Requests jointInfo, User user){
        Account a = jointInfo.account;
        if (a instanceof LineofCredit) {
            ArrayList arrayList = user.getAccountTypeList(LINE_OF_CREDIT);
            arrayList.add(a);
            System.out.println("Added to:" + LINE_OF_CREDIT);

        }

        if (a instanceof CreditCard) {
            ArrayList arrayList = user.getAccountTypeList(CREDIT_CARD);
            arrayList.add(a);
            System.out.println("Added to:" + CREDIT_CARD);

        }

        if (a instanceof Savings) {
            ArrayList arrayList = user.getAccountTypeList(SAVINGS);
            arrayList.add(a);
            System.out.println("Added to:" + SAVINGS);

        }

        if (a instanceof Chequing) {
            ArrayList arrayList = user.getAccountTypeList(CHEQUING);
            arrayList.add(a);
            System.out.println("Added to:" + CHEQUING);
            boolean existPrimary = false;
            for(int i = 0; i<= arrayList.size() -1;i++){
                Chequing c = (Chequing)arrayList.get(i);
                if (c.getIs_primary()) {
                    existPrimary = true;
                }
            }
            if(!existPrimary){
                Chequing check = (Chequing)arrayList.get(0);
                check.set_is_primary(true);
            }

            User sourceUser = jointInfo.userA;
            sourceUser.updateToJoint.add("\n"+"Joint account of" +" "+ a.type  + " "+ "with balance of" +" "+ jointInfo.account.getBalance()  +" "+  " to" +" "+ user.username +" "+ " was successful" +"\n");

        }

    }

    /**
     * Helper method removes a request from the  jointRequest field of the User(see User for detail's on the
     * jointRequest field)
     * @param jointInfo The Request it is going to remove
     * @param user The User using this ATM
     */
    public void removeJointAccount(Requests jointInfo, User user){
        user.jointRequest.remove(jointInfo);
        User sourceUser = jointInfo.userA;
        sourceUser.updateToJoint.add("\n" + "Joint account of" +" "+ jointInfo.account.type + " "+ "with balance of" +" "+ jointInfo.account.getBalance()  +" "+ " to" +" "+ user.username +" "+ " was a FAILURE!" +"\n");
    }

    /**
     * Allows the viewing of the Request that have been accepted or rejected in the User field
     * updateToJoint(see User for detail's on the updateToJoint field)
     * @param user The User using this ATM at the moment
     */
    public void viewRequestUpdates(User user){
        String all = " ";
        int number = 1;
        for(int i =0; i<=user.updateToJoint.size()-1;i++){
            String s = user.updateToJoint.get(i);
            s = number + ")" + s;
            number += 1;

            all = all + s;
        }
        System.out.println(all);
    }

    /**
     * Clears the updateToJoint field (see User for detail's on the
     * updateToJoint field)
     * @param user The User using this ATM
     */
    public void clearUpdates(User user){
        user.updateToJoint.clear();
    }




}
