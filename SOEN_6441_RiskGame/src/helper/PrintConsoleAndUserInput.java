package helper;
import java.util.Scanner;



/**
 * This class is used to take the input from from the console.
 * @author Zakiya Jafrin
 * @version 1.0.0
 */
public class PrintConsoleAndUserInput {
	
    static Scanner input = new Scanner(System.in);
    
    // Try with these directory path if code is not running
    //private String mapDir = "SOEN_6441_RiskGame/src/mapFiles/";
    private String mapDir = ".\\src\\mapFiles\\";

    /**
     *  This method is used to print in console.
     * @param s , to print in console
     */
    public void consoleOut(String s){
        System.out.println(s);
    }
    
    /**
     *  This method is used to show the error in console.
     * @param error, prints the error in console
     */
    public void consoleErr(String error){
        System.err.println(error);
    }

    /**
     * This method is used to get the string input.
     * @return string nextLine
     */
    public static String userStrInput(){
        return input.nextLine();
    }
    
    /**
     * This method is used to get the integer input.
     * @return Integer nextInt
     */
    public static Integer userIntInput(){
        return input.nextInt();
    }
    
    /**
     * This method is used to get path of map directory.
     * @return String mapDir
     */
    public String getMapDir() {
        return mapDir;
    }
    
 
    
    /**
	 * This method is used to print and handle Exceptions.
	 * 
	 * @param exception,object of exception class
	 *            
	 */
	public static void printException(Exception exception) {
		System.out.println("Exception: " + exception.getMessage());
		System.out.println(exception.getCause());
		exception.printStackTrace();
	}

	
	public static int getNextInteger() {
		int s = input.nextInt();
		return s;
	}

}

	
