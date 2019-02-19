package helper;


import java.util.Scanner;

public class PrintConsoleAndUserInput {
    static Scanner input = new Scanner(System.in);
    private String mapDir = "SOEN_6441_RiskGame/src/mapFiles/";
//    private String mapDir = ".\\src\\mapFiles\\";

    public void consoleOut(String s){
        System.out.println(s);
    }

    public static String userStrInput(){
        return input.nextLine();
    }

    public static Integer userIntInput(){
        return input.nextInt();
    }

    public String getMapDir() {
        return mapDir;
    }
}
