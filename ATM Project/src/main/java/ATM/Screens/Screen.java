package main.java.ATM.Screens;


import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Screen{
    // The Parent of All Screens
    public static String ans;
    public static final Scanner sc = new Scanner(System.in);
    public static final List<String> commandList = Arrays.asList("ATM.past", "ATM.pres");

    /**
     *
     * @param message
     * @return
     */
    public String commandATM(String message){
        System.out.println(message);
        return sc.nextLine();
    }

    public static void printWelcome(){
        System.out.println(" _    _      _                             _           _   _              ___ ________  ___ \n" +
                "| |  | |    | |                           | |         | | | |            / _ \\_   _|  \\/  | \n" +
                "| |  | | ___| | ___ ___  _ __ ___   ___   | |_ ___    | |_| |__   ___   / /_\\ \\| | | .  . | \n" +
                "| |/\\| |/ _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\  | __/ _ \\   | __| '_ \\ / _ \\  |  _  || | | |\\/| | \n" +
                "\\  /\\  /  __/ | (_| (_) | | | | | |  __/  | || (_) |  | |_| | | |  __/  | | | || | | |  | | \n" +
                " \\/  \\/ \\___|_|\\___\\___/|_| |_| |_|\\___|   \\__\\___/    \\__|_| |_|\\___|  \\_| |_/\\_/ \\_|  |_/ \n\n");
    }
}
