package helper;

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
