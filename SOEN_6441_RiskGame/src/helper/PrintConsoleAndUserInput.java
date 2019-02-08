package helper;


import java.util.Scanner;

public class PrintConsoleAndUserInput {
    static Scanner input = new Scanner(System.in);

    public void consoleOut(String s){
        System.out.println(s);
    }

    public static String userStrInput(){
        return input.nextLine();
    }

    public static Integer userIntInput(){
        return input.nextInt();
    }

}
