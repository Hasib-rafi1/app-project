package model;

import helper.Colors;
/**
 * this is a player class which contains the players attributes and basic setter getter functions 
 * to get and set the value out of it. Some business logic is also added in this class
 * @author Md Hasibul Huq
 * @version 1.0.0
 */
public class Player {
	private int playerId;
	private int numberOfInitialArmies;
	private int numberOfReinforcedArmies;
	private String playerName;
	private Colors color;


	/**
	 * This is a constructor of Player Class which sets playerId, name, and
	 * color.
	 * 
	 * @param playerId,id of the player
	 * @param name,name of the player
	 */
	public Player(int playerId, String name) {
		super();
		this.playerId = playerId;
		this.playerName = name;
		this.color = getPlayerColor(playerId);
	}

	/**
	 * This method is going to provide the players id
	 * @return integer value of playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * This method is setting the value of playerId of a player object
	 * @param playerId
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * This function is providing the assigned army number of a player
	 * @return integer value of assigned armies
	 */
	public int getNumberOfInitialArmies() {
		return numberOfInitialArmies;
	}

	/**
	 * This is function is going to set the assigned army numbers in the specific object
	 * @param numberOfInitialArmies
	 */
	public void setNumberOfInitialArmies(int numberOfInitialArmies) {
		this.numberOfInitialArmies = numberOfInitialArmies;
	}

	/**
	 * This function is going to return the reinforced army number
	 * @return integer value number of reinforced army 
	 */
	public int getNumberOfReinforcedArmies() {
		return numberOfReinforcedArmies;
	}

	/**
	 * This function is setting the reinforced army
	 * @param noOfReinforcedArmies
	 */
	public void setNumberOfReinforcedArmies(int noOfReinforcedArmies) {
		this.numberOfReinforcedArmies = noOfReinforcedArmies;
	}

	/**
	 * This function is going to return name of the player
	 * @return string value.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * This function sets the name of the player
	 * @param playerName
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * This function returns the color
	 * @return ENUM value of color
	 */
	public Colors getColor() {
		return color;
	}

	/**
	 * Setting the color
	 * @param color
	 */
	public void setColor(Colors color) {
		this.color = color;
	}
	/**
	 * This method is going to decrease the number of initial armies after each assigning 
	 * in initial assigning step 
	 */
	public void decreasenumberOfInitialArmies() {
		if(numberOfInitialArmies>0) {
			numberOfInitialArmies = numberOfInitialArmies -1;
		}
	}

	/**
	 * This method is going to decrease the unassigned reinforcement armies count. when the reinforcement 
	 * armies are distributed.  
	 */
	public void decreaseReinforcementArmy() {
		if(numberOfReinforcedArmies>0) {
			numberOfReinforcedArmies= numberOfReinforcedArmies -1;
		}
	}

	/**
	 * This returns the player color.
	 * @param playerID the id of the player
	 * @return EnumColor,color of the player
	 */
	public static Colors getPlayerColor(int playerID) {
		switch (playerID) {
		case 0:
			return Colors.BLACK;
		case 1:
			return Colors.BLUE;
		case 2:
			return Colors.GREEN;
		case 3:
			return Colors.RED;
		case 4:
			return Colors.ORANGE ;
		case 5:
			return Colors.MAGENTA;
		default:
			return Colors.BLACK;
		}
	}
}
