package main.java.ATM.enums;

public enum UserStatus{
    // Home screens
    NULL,
    USER_HOME,

    // Types of Accounts
    SELECT_ACCOUNT_TYPE,
    CREDIT_CARD,
    LINE_OF_CREDIT,
    CHEQUING,
    SAVINGS,
    INVESTMENT,

    // Transaction Screen
    WITHDRAW,
    DEPOSIT,
    TRANS_MY_ACCOUNT,
    TRANS_OTHER_USER,
    TRANS_NONUSER_ACCOUNT,
    SEE_TIME,
    MAKE_PRIMARY,
    REQUEST_MAKE_JOINT,
    RESPOND_TO_JOINT_REQUEST,
    VIEW_JOINT_REQUEST_UPDATES,

    // Investment Actions
    INVEST_BUY,
    INVEST_SELL,
    INVEST_HOME,
}
