package helper;
/**
 * This class is containgin the values that for how many armies are going to get by 
 * Specific number of players
 * @author Jai 
 *
 */
public class InitialPlayerArmy
{	
	public static int getInitialArmyCount(int playerCount) 
	{
		switch (playerCount) 
		{
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
