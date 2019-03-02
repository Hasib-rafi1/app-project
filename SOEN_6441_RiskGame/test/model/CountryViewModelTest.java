package model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import helper.Colors;
/**
 * Test class that tests the methods in CountryViewModel class in model package.
 * @author naren
 */
public class CountryViewModelTest {
	/**
	 * Declaring objects
	 */
	CountryViewModel obj_CVM = new CountryViewModel();
	private ArrayList<String> neighbours = new ArrayList<>();
	
	/**
	 * Initializing objects and values for the test cases
	 * @throws Exception
	 */
	
	@Before
	public void setUp() throws Exception{
		obj_CVM.setCountryId(1);
		obj_CVM.setCountryName("Canada");
		obj_CVM.setxCoordinate(120);
		obj_CVM.setyCoordinate(100);
		obj_CVM.setNumberOfArmies(4);
		obj_CVM.setPlayerID(1);
		obj_CVM.setColorOfCountry(Colors.GREEN);
		neighbours.add("USA");
		neighbours.add("mexico");
		obj_CVM.setNeighbours(neighbours);
		
	}
	
	/**
	 * test method to check if it returns correct country id
	 */
	
	@Test
	public void testGetCountryId() {
		assertEquals(1,obj_CVM.getCountryId());
	}
	
	/**
	 * test method to check if it returns correct country name
	 */
	
	@Test
	public void testGetCountryName() {
		assertEquals("Canada",obj_CVM.getCountryName());
	}
	
	/**
	 * test method to check if it returns correct xCoordinate value  
	 */
	
	@Test
	public void testGetxCoordinate() {
		assertEquals(120,obj_CVM.getxCoordinate());
	}
	
	/**
	 * test method to check if it returns correct yCoordinate value
	 */
	
	@Test
	public void testGetyCoordinate() {
		assertEquals(100,obj_CVM.getyCoordinate());
	}
	
	/**
	 * test method to check if it returns correct number of armies
	 */
	
	@Test
	public void testGetnumberOfArmies() {
		assertEquals(4,obj_CVM.getNumberOfArmies());
	}
	
	/**
	 * method that tests if it returns the correct value for the color of the country
	 */
	
	@Test
	public void testGetColorOfCountry() {
		assertEquals(Colors.GREEN,obj_CVM.getColorOfCountry());
	}
	
	/**
	 * method that tests if it returns the correct value for the player id
	 */
	
	@Test
	public void testGetPlayerId() {
		assertEquals(1,obj_CVM.getPlayerID());
	}
	
	/**
	 * method that tests if it fetches the neighbours from the list
	 */
	
	
	@Test
	public void testGetNeighbours() {
		for(String s:neighbours) 
			System.out.println(s);
		
	}
}
