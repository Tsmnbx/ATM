//package main.java.ATM;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by joshuaefe on 2019-03-06.
/**
// */
//public class BankAccount {
//        public User user;
//        public Map<String, ArrayList<Account>> accounts = new HashMap<String, ArrayList<Account>>();
//
//        public int CreditCard = 1;                                                                                      // Alias to obj name is probs not a good idea
//        public int LineofCredit = 1;
//        public int Chequing = 1;
//        public int Savings = 1;
//        public static String[] accountTypes = {"Credit Cards Accounts", "Line of Credit Accounts","Chequing Accounts","Savings Accounts"};
//
//        public BankAccount() {
//            this.user = user;                                                                                           // We don't need this, It may be a good idea to have this class as a child of user tho
//            CreditCard cCA = new CreditCard(CreditCard);
//            CreditCard +=1; // this puts Credit card at 2
//            ArrayList creditCardsAccounts = new ArrayList();
//            creditCardsAccounts.add(cCA);
//            accounts.put(accountTypes[0], creditCardsAccounts);                                                         // accountTypes could be an Enum/ we could just have it as a String no need to have it as indexes in a list.
//
//            LineofCredit lOC = new LineofCredit(LineofCredit);
//            LineofCredit +=1;
//            ArrayList lineofCreditAccounts = new ArrayList();
//            creditCardsAccounts.add(lOC);
//            accounts.put(accountTypes[1], lineofCreditAccounts);
//
//            Chequing c = new Chequing(Chequing);
//            Chequing +=1;
//            ArrayList chequingAccounts = new ArrayList();
//            creditCardsAccounts.add(c);
//            accounts.put(accountTypes[2], chequingAccounts);
//
//            Savings s = new Savings(Savings);
//            Savings +=1;
//            ArrayList savingsAccounts = new ArrayList();
//            creditCardsAccounts.add(s);
//            accounts.put(accountTypes[3], savingsAccounts);
//        }
//
//        //Todo get account method, display balances method,
//
//    public Account getAccount(String type, int index1){
//        ArrayList accountType = this.accounts.get(type);
//        int index = index1 - 1;
//        Account a = (Account)accountType.get(index);
//        return a;
//    }
//
//    public ArrayList getAccountType(int index){                                                                         // Why not just use the hashmap, accounts.get("CreditCard")
//        String type = accountTypes[index];
//        ArrayList accountType = this.accounts.get(type);
//        return accountType;
//    }
//
//    //Todo Not sure if different for different account types
//    public double getBalance(String type, int index1){                                                                  // accounts.get("CreditCard")[i].ballance
//        Account a = getAccount(type, index1);
//        double balance = a.balance;
//        return balance;
//    }
//    public double getBalanceofType(String type){                                                                        // for (i : accounts.get("CreditCard")) {sum += i.ballance} return sum
//        double balance = 0;
//        ArrayList accountType = this.accounts.get(type);
//        for(int i = 0; i<= accountType.size() - 1; i++){
//            Account a = (Account)accountType.get(i);
//            if (a instanceof DebtAccounts){
//            double b = a.balance;
//            balance = balance - b;
//            }
//            if (a instanceof AssetAccounts){
//                double b = a.balance;
//                balance = balance + b;
//            }
//        }
//        return balance;
//    }
//
//    public double getTotalBalance(){                                                                                    // for (i : accountTypes) {sum += getBalanceofType(i) }
//        double balance = 0;
//        for(int i = 0; i<= accountTypes.length - 1; i++){
//            String kind = accountTypes[i];
//            double b = getBalanceofType(kind);
//            balance = balance + b;
//        }
//        return balance;
//    }
//
//    public String displayBalance(String type, int index1){
//        String statement = " ";
//        double balance = getBalance(type, index1);
//        statement = statement + balance + "/n";
//        return statement;
//
//        }
//
//    public String displayBalanceofType(String type){
//        String statement = " ";
//        ArrayList accountType = this.accounts.get(type);
//        for(int i = 0; i<= accountType.size() - 1; i++){
//            String s = displayBalance(type,i);
//            statement = statement + s;
//        }
//        return statement;
//    }
//
//
//
//
//    public String displayAllBalances(){
//        String statement = " ";
//        for(int i = 0; i<= accountTypes.length - 1; i++){
//            String kind = accountTypes[i];
//            String s = displayBalanceofType(kind);
//            statement = statement + s;
//        }
//        return statement;
//    }
//
//    //Todo overload if its checking so you can choose which chequing account is primary
//    public void addAccount(Account a){
//        if(a instanceof CreditCard){
//            ArrayList accountList = getAccountType(0);
//            CreditCard += 1;
//            a.number = CreditCard;
//            accountList.add(a);
//        }
//
//        if(a instanceof LineofCredit){
//            ArrayList accountList = getAccountType(1);
//            LineofCredit += 1;
//            a.number = LineofCredit;
//            accountList.add(a);
//        }
//
//        if(a instanceof Chequing){
//            ArrayList accountList = getAccountType(2);
//            Chequing += 1;
//            a.number = Chequing;
//            accountList.add(a);
//        }
//
//        if(a instanceof Savings){
//            ArrayList accountList = getAccountType(3);
//            Savings += 1;
//            a.number = Savings;
//            accountList.add(a);
//        }
//    }
//
//
//
//
//
//
//}
