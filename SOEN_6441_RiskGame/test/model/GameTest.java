package model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import helper.GamePhase;
import helper.PrintConsoleAndUserInput;
// TODO: Auto-generated Javadoc
/**
 * This test Class is dealing with the game model class. It will check the game play by executing the game automatically . 
 * @author Hasibul Huq
 *
 */
public class GameTest {
	
	/** The map model. */
	MapModel mapModel;
	
	/** The game object. */
	Game gameObject;
	
	/** The player 1. */
	Player player1;
	
	/** The player 2. */
	Player player2;
	
	/** The player 3. */
	Player player3;
	
	/** The player 4. */
	Player player4;
	
	/** The player 5. */
	Player player5;
	
	/** The id. */
	int id =0;

	/**
	 * Initializing the values and object to start a game .
	 */
	@Before
	public void setUp(){
		mapModel = new MapModel();
//		mapModel.readMapFile("src/mapFiles/World.map");
		mapModel.readMapFile(PrintConsoleAndUserInput.getMapDir()+"World.map");
		gameObject = new Game(mapModel);
		player1 = new Player(0,"Jai");
		player2 = new Player(1,"Gargi");
		player3 = new Player(2,"Zakia");
		player4 = new Player(3,"Narendra");
		player5 = new Player(4,"Rafi");
		gameObject.addPlayer(player1);
		gameObject.addPlayer(player2);
		gameObject.addPlayer(player3);
		gameObject.addPlayer(player4);
		gameObject.addPlayer(player5);
		gameObject.startGame();

		while (gameObject.getGamePhase() == GamePhase.Startup) {
			// Randomly increase army for the country of player
			ArrayList<Country> playerCountries = gameObject.getCurrentPlayerCountries();

			gameObject.addingCountryArmy(playerCountries.get(id).getCountryName());

			id++;
			if(id==8) {
				id = 0;
			}
		}
	}

	/**
	 * Test method for checking current phase.
	 */
	@Test
	public void testCurrentPhaseIsReinforcement() {
		assertEquals(GamePhase.Reinforcement, gameObject.getGamePhase());
	}

	/**
	 * Test the calculation of the number of armies during the reinforcement phase.
	 */
	@Test
	public void testCalculationOfReinforcementArmies() {
		Player player = gameObject.getCurrentPlayer();
		int a = gameObject.getCurrentPlayer().calculationForNumberOfArmiesInReinforcement(gameObject.playerandCountries(),mapModel.getContinentList());
		assertEquals(3, a);
	}

	/**
	 * Test the current player object is same or not.
	 */
	@Test
	public void testIsItPlayerObjectSame() {
		Player player = gameObject.getCurrentPlayer();
		assertEquals(player1, player);
	}

	/**
	 * Test the current map file is same as get imported.
	 */
	@Test
	public void testIsItMapObjectSame() {
		MapModel mapTest = gameObject.getMap();
		assertEquals(mapModel, mapTest);
	}
	
	/**
	 * Test the map is concured or not.
	 */
	@Test
	public void isMapConcured() {
		if(mapModel.getCountryList().size() == gameObject.playerCountry.get(gameObject.getCurrentPlayer()).size()) {
			assertTrue(gameObject.isMapConcured());
		}else {
			assertFalse(gameObject.isMapConcured());
		}
	}
	
	/**
	 * Test If attack is possible from the current player.
	 */
	@Test
	public void isAttackPossible() {
		if (gameObject.getAttackPossibleCountries().size() == 0) {
			assertFalse(gameObject.checkAttackPossible());
		}else {
			assertFalse(gameObject.checkAttackPossible());
		}
	}
	
	/**
	 * Test the fortification phase.
	 */
	@Test
	public void checkFortification() {
		Country selectedCountry;
		Country destinationCountry;
		GamePhase A = gameObject.getGamePhase();
		gameObject.setGamePhase(GamePhase.Fortification);
		if(gameObject.getCurrentPlayer().getAssignedListOfCountries().size()>1) {
			selectedCountry = gameObject.getCurrentPlayer().getAssignedListOfCountries().get(0);
			destinationCountry = gameObject.getCurrentPlayer().getAssignedListOfCountries().get(1);
			if (selectedCountry.getnoOfArmies()<=1) {
				selectedCountry.increaseArmyCount();
			}
			int a = selectedCountry.getnoOfArmies()-1;
			int b = destinationCountry.getnoOfArmies()+1;
			gameObject.fortificationPhase(selectedCountry.getCountryName(), destinationCountry.getCountryName(), 1);
			assertEquals(selectedCountry.getnoOfArmies(), a);
			assertEquals(destinationCountry.getnoOfArmies(), b);
		}

		gameObject.setGamePhase(A);
		
	}
}
