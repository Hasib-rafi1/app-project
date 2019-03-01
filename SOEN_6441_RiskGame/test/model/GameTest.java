package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


import helper.GamePhase;
import helper.PrintConsoleAndUserInput;

public class GameTest {
	MapModel mapModel;
	Game gameObject;
	Player player1;
	Player player2;
	Player player3;
	int id =0;
	@Before
	public void setUp(){
		mapModel = new MapModel();
		mapModel.readMapFile(PrintConsoleAndUserInput.getMapDir()+"World.map");
		gameObject = new Game(mapModel);
		player1 = new Player(0,"Jai");
		player2 = new Player(1,"Rafi");
		player3 = new Player(2,"Jakia");
		gameObject.addPlayer(player1);
		gameObject.addPlayer(player2);
		gameObject.addPlayer(player3);
		gameObject.startGame();
		
		while (gameObject.getGamePhase() == GamePhase.Startup) {
			// Randomly increase army for the country of player
			ArrayList<Country> playerCountries = gameObject.getCurrentPlayerCountries();

			gameObject.addingCountryArmy(playerCountries.get(id).getCountryName());

			id++;
		}
	}

	/**
	 * Test method for checking current phase
	 */
	@Test
	public void testCurrentPhaseIsReinforcement() {
		assertEquals(GamePhase.Reinforcement, gameObject.getGamePhase());
	}

	/**
	 * 
	 */
	@Test
	public void testCalculationOfReinforcementArmies() {
		Player player = gameObject.getCurrentPlayer();
		int a = gameObject.calculationForNumberOfArmiesInReinforcement(player);
		assertEquals(4, a);
	}

}
