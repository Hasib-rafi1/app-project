package helper;

public class InitialPlayerArmy
{	
	public static int getInitialArmyCount(int playerCount) 
	{
		switch (playerCount) 
		{
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
