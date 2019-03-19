package helper;
// TODO: Auto-generated Javadoc

/**
 * This class is containing the values that for how many armies are going to get by 
 * Specific number of players.
 *
 * @author Jaiganesh
 */
public class InitialPlayerArmy{	
	
	/**
	 * Gets the initial army count.
	 *
	 * @param playerCount the player count
	 * @return the initial army count
	 */
	public static int getInitialArmyCount(int playerCount) {
		switch (playerCount) {
		case 3:
			return 15;
		case 4:
			return 15;
		case 5:
			return 15;
		default:
			return 15;
		}
	}
}
