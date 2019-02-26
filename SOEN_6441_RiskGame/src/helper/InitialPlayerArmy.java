package helper;

public class InitialPlayerArmy
{	
	public static Colors assingPlayerColor(int playerID) 
	{
		switch (playerID) 
		{
		case 1:
			return Colors.BLACK;
		case 2:
			return Colors.BLUE;
		case 3:
			return Colors.GREEN;
		case 4:
			return Colors.RED;
		case 5:
			return Colors.ORANGE;
		default:
			return Colors.ORANGE;
		}
	}
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
