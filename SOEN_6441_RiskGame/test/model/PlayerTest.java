package model;

import static org.junit.Assert.*;

import org.junit.Before;

import helper.Colors;


import org.junit.Test;

public class PlayerTest {


	Player obj_playertest;
	
	@Before
	public void setUp() throws Exception{
		obj_playertest=new Player(1,"Justin");
		obj_playertest.setColor(Colors.RED);
		obj_playertest.setNumberOfInitialArmies(5);
		obj_playertest.setNumberOfReinforcedArmies(3);
	
	}
	
	@Test
	public void testGetPlayerid() {
		assertEquals(1,obj_playertest.getPlayerId());
	}
	
	@Test
	public void testGetNumberOfInitialArmies() {
		assertEquals(5, obj_playertest.getNumberOfInitialArmies());
	}
	
	@Test 
	public void testGetNumberOfReinforcedArmies() {
		assertEquals(3, obj_playertest.getNumberOfReinforcedArmies());
		
	}
	
	@Test
	public void testGetPlayername() {
		assertEquals("Justin",obj_playertest.getPlayerName());
	}
	
	@Test 
	public void testGetColor() {
		assertEquals(Colors.RED, obj_playertest.getColor());
	}
	
	
	@Test
	public void testGetPlayerColor() {
		assertEquals(Colors.GREEN,Player.getPlayerColor(2));
		assertEquals(Colors.MAGENTA,Player.getPlayerColor(5));
	}
	
	@Test
	public void testDecreaseNumberOfInitialArmies() {
		obj_playertest.decreasenumberOfInitialArmies();
		System.out.println("Decrease Initial Army:"+obj_playertest.getNumberOfInitialArmies());
	}
	
	@Test
	public void testDecreaseNumberOfReinforcedArmies() {
		obj_playertest.decreaseReinforcementArmy();
		System.out.println("Decrease Reinforced Army:"+obj_playertest.getNumberOfReinforcedArmies());
	}
	

}
