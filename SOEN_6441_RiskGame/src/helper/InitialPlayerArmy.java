package helper;

public class InitialPlayerArmy
{	
	public static int getInitialArmyCount(int playerCount) 
	{
		switch (playerCount) 
		{
		case 3:
			return 20;
		case 4:
			return 20;
		case 5:
			return 20;
		default:
			return 20;
		}
	}
}
