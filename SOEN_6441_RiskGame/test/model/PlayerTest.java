package model;

import static org.junit.Assert.*;
import org.junit.Before;
import helper.Colors;
import org.junit.Test;
// TODO: Auto-generated Javadoc

/**
 * Test class that tests the correctness in values in Player class that'll be used in further operations .
 *
 * @author naren
 */
public class PlayerTest {
	
	/** Declaring objects. */

	Player obj_playertest;

	/**
	 * Initializing objects and values for the test cases.
	 *
	 * @throws Exception if it is not setting the values at the starting
	 */

	@Before
	public void setUp() throws Exception{
		obj_playertest=new Player(1,"Justin");
		obj_playertest.setColor(Colors.RED);
		obj_playertest.setNumberOfInitialArmies(5);
		obj_playertest.setNumberOfReinforcedArmies(3);

	}


	/**
	 * test method to check if it returns correct player id.
	 */

	@Test
	public void testGetPlayerid() {
		assertEquals(1,obj_playertest.getPlayerId());
	}


	/**
	 * test method to check the initial number of armies.
	 */

	@Test
	public void testGetNumberOfInitialArmies() {
		assertEquals(5, obj_playertest.getNumberOfInitialArmies());
	}


	/**
	 * test method to check the reinforced number of armies.
	 */

	@Test 
	public void testGetNumberOfReinforcedArmies() {
		assertEquals(3, obj_playertest.getNumberOfReinforcedArmies());

	}


	/**
	 * test method to check if it returns correct player name.
	 */

	@Test
	public void testGetPlayername() {
		assertEquals("Justin",obj_playertest.getPlayerName());
	}


	/**
	 * test method to check if the function getColor() fetches the correct colors.
	 */

	@Test 
	public void testGetColor() {
		assertEquals(Colors.RED, obj_playertest.getColor());
	}


	/**
	 * test method to check if it returns correct color for every player.
	 */

	@Test
	public void testGetPlayerColor() {
		assertEquals(Colors.GREEN,Player.getPlayerColor(2));
		assertEquals(Colors.MAGENTA,Player.getPlayerColor(5));
	}


	/**
	 * test method to check if the method implements the decrease the initial army operation correctly.
	 */

	@Test
	public void testDecreaseNumberOfInitialArmies() {
		obj_playertest.decreasenumberOfInitialArmies();
		System.out.println("Decrease Initial Army:"+obj_playertest.getNumberOfInitialArmies());
	}


	/**
	 * test method to check if the method implements the decrease the reinforced army operation correctly.
	 */

	@Test
	public void testDecreaseNumberOfReinforcedArmies() {
		obj_playertest.decreaseReinforcementArmy();
		System.out.println("Decrease Reinforced Army:"+obj_playertest.getNumberOfReinforcedArmies());
	}


}
