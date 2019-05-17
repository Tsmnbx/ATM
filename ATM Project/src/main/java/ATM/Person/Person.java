package main.java.ATM.Person;


import java.io.IOException;

/**
 * The Person class is an abstract Parent Class for User and BankWorker
 * has a username, password, time, login attempts and relevant methods
 */
public abstract class Person {
    public String username;
    public String password;
    public int loginAttempts = 0;
    /**
     * This is static because time needs to remain constant throughout the program
     */
    public static int time;

    public abstract void act()throws IOException;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Helper method for login attempts for child classes
     * @return boolean
     */
    public boolean checkLoginAttemptsValid(){
        return (loginAttempts <= 2);
    }
}
