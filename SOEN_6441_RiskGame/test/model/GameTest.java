package model;

import static org.junit.Assert.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import helper.GamePhase;
import helper.PrintConsoleAndUserInput;
import strategies.Human;

// TODO: Auto-generated Javadoc
/**
 * This test Class deals with the game model class. It will check the game play by executing the game automatically .
 *  
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
		player1.setPlayerStrategy(new Human());
		player2.setPlayerStrategy(new Human());
		player3.setPlayerStrategy(new Human());
		player4.setPlayerStrategy(new Human());
		player5.setPlayerStrategy(new Human());
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
		int a = player.calculationForNumberOfArmiesInReinforcement(gameObject.playerandCountries(),mapModel.getContinentList());
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
	
	@Test
	public void checkAttackerDefender() {
		Country attCountry = gameObject.playerCountry.get(player1).get(0);
		Country defCountry = gameObject.playerCountry.get(player2).get(0);
		int defendergDiceCount =1;
		defCountry.setnoOfArmies(3);
		boolean result =gameObject.isAttackerDefenderValid(attCountry, defCountry, defendergDiceCount);
		assertTrue(result);
	}
	
	/**
	 * Test If attack is possible from the current player.
	 */
	@Test
	public void isAttackPossible() {
		if (gameObject.getAttackPossibleCountries().size() == 0) {
			assertFalse(gameObject.checkAttackPossible());
		}else {
			for (String countryName : gameObject.getAttackPossibleCountries()) {
				ArrayList<String> neighborCountries = gameObject.getOthersNeighbouringCountriesOnly(countryName);
				if (neighborCountries.size() > 0) {
					assertTrue(gameObject.checkAttackPossible());
				}
			}
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
	
	/**
	 * This test case is used to test if the game is saved or not with the currrent time.
	 */
	@Test
	public void testGameIsSavedOrNot(){
		try {
			gameObject.writeObjectToSaveMyGame();			
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy_hhmm");
			String saveGameFileWithTime = dateFormat.format(cal.getTime());
			
			String filePath = ".\\src\\savedGames\\" + saveGameFileWithTime+ ".txt";
			FileInputStream fileInput = new FileInputStream(filePath);
			ObjectInputStream objectInput =new ObjectInputStream(fileInput);
			Game testGameObject=(Game) objectInput.readObject();
			objectInput.close();
			fileInput.close();
			
			
			ArrayList<Player> playerList1 = testGameObject.getAllPlayers(); // expected
			int getSizeOfExpectedList = playerList1.size();
			ArrayList<Player> playerList2 = gameObject.getAllPlayers(); // actual
			int getSizeOfActualList = playerList2.size();
			
			assertEquals(getSizeOfExpectedList,getSizeOfActualList);
			
			
			int armySize1[] = new int[getSizeOfExpectedList];
			int armySize2[] = new int[getSizeOfActualList];
			int i = 0;
			for(Player expectedList : playerList1) {
				armySize1[i] = expectedList.getNumberOfReinforcedArmies();
				i++;
			}
			i = 0;
			for(Player actualList : playerList2) {
				armySize2[i] = actualList.getNumberOfReinforcedArmies();
				i++;
			}
			
			for (i = 0; i < armySize2.length; i++) {
				assertEquals(armySize1[i], armySize2[i]);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
