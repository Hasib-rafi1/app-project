package helper;

public class InitialPlayerArmy 
{
	public static int getInitialArmyCount(int players_total) 
	{
		switch (players_total)
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
