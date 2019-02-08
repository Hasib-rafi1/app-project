import java.io.IOException;
import java.util.Scanner;
import controller.GameController;
import helper.Colors;
import model.Map;

/**
 * This is a main class to run the game
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class Main {
	/**
	 * This is a main method to run the game.
	 * This function is used to enter the user input and call the functions to create or edit the map, start, load the game
	 * and user can exit if he wants to exit the game.
	 * This function also displays the error message to select valid user input.
	 * @param 
	 */

	public static void main(String[] args) {
		Map map = new Map();
		map.readMapFile();


		//Colors c1 = Colors.RED; 
	   //  System.out.println(c1); 
		GameController gameController = new GameController();
		gameController.listofMapsinDirectory();
		int selectOption = 0;
		do {
			selectOption = displayMainMenu();
			switch (selectOption) {			

			case 1:
				GameController.generateMap();
				break;
			case 2:
				GameController.editMap();
				break;
			case 3:
				GameController.startGame();

				break;
			case 4:
				GameController.loadGame();

				break;
			case 5:
				System.exit(0);
			default :
				System.err.println("\n\t Error! Please select valid option from the above Options(1 to 5):");				
				break;
			}

		} while (selectOption != 5);
		System.exit(0);
	}

	
	
	/**
	 * This is the method for Displaying main menu for game. 
	 * This function is used to show the user input to create or edit the map, start, load the game
	 * and user can exit if he wants to exit the game.
	 * @return 
	 */
	public static int displayMainMenu() {
		String space = "   "; 
		Scanner scanner = new Scanner(System.in);		
		System.out.println("------------------------------------------------------------");
		System.out.println(space+"Risk Board Game");
		System.out.println("------------------------------------------------------------");
		//System.out.println("==================================");
		System.out.println("\n 1. Generate Map");
		System.out.println("\n 2. Edit Map");
		System.out.println("\n 3. Start Game");
		System.out.println("\n 4. Load Game");
		System.out.println("\n 5. Exit ");
		System.out.println("\n Enter your Choice from the above Options(1 to 5): ");
		return scanner.nextInt();
	}








}
