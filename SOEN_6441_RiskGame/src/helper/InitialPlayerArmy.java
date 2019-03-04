package helper;
/**
 * This class is containing the values that for how many armies are going to get by 
 * Specific number of players
 * @author Jaiganesh 
 *
 */
public class InitialPlayerArmy{	
	public static int getInitialArmyCount(int playerCount) {
		switch (playerCount) {
		case 3:
			return 35;
		case 4:
			return 30;
		case 5:
			return 25;
		default:
			return 20;
		}
	}
}
