
/**
 * @author Gargi Sharma
 * @version 1.0.0
 */
import java.util.Scanner;
import controller.GameController;


/*
 * main class to start Game
 */
public class main {
	
	/*
	 * Main method
	 */
	public static void main(String[] args) {
		
		
		GameController gameController = new GameController();

		int selectOption = 0;
		boolean ready_state = false;
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
	 * method for Displaying main menu for game 
	 * @return 
	 */
	public static int displayMainMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n==================================");
		System.out.println("\t Risk Board Game");
		System.out.println("==================================");
		System.out.println("\n 1. Generate Map");
		System.out.println("\n 2. Edit Map");
		System.out.println("\n 3. Start Game");
		System.out.println("\n 4. Load Game");
		System.out.println("\n 5. Exit ");
		System.out.println("\n Enter your Choice from the above Options(1 to 5): ");
		return scanner.nextInt();
	}








}
