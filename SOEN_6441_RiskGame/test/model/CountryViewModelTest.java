package model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import helper.Colors;
// TODO: Auto-generated Javadoc
/**
 * Test class that tests the methods in CountryViewModel class in model package.
 * 
 * @author naren
 */
public class CountryViewModelTest {
	
	/** Declaring objects. */
	CountryViewModel objCVM = new CountryViewModel();
	
	/** The neighbours. */
	private ArrayList<String> neighbours = new ArrayList<>();

	/**
	 * Initializing objects and values for the test cases.
	 * @throws Exception if it is not setting the values at the starting
	 */
	@Before
	public void setUp() throws Exception{
		objCVM.setCountryId(1);
		objCVM.setCountryName("Canada");
		objCVM.setxCoordinate(120);
		objCVM.setyCoordinate(100);
		objCVM.setNumberOfArmies(4);
		objCVM.setPlayerID(1);
		objCVM.setColorOfCountry(Colors.GREEN);
		neighbours.add("USA");
		neighbours.add("mexico");
		objCVM.setNeighbours(neighbours);

	}

	/**
	 * test method to check if it returns correct country id.
	 */
	@Test
	public void testGetCountryId() {
		assertEquals(1,objCVM.getCountryId());
	}

	/**
	 * test method to check if it returns correct country name.
	 */
	@Test
	public void testGetCountryName() {
		assertEquals("Canada",objCVM.getCountryName());
	}

	/**
	 * test method to check if it returns correct xCoordinate value.
	 */

	@Test
	public void testGetxCoordinate() {
		assertEquals(120,objCVM.getxCoordinate());
	}

	/**
	 * test method to check if it returns correct yCoordinate value.
	 */

	@Test
	public void testGetyCoordinate() {
		assertEquals(100,objCVM.getyCoordinate());
	}

	/**
	 * test method to check if it returns correct number of armies.
	 */

	@Test
	public void testGetnumberOfArmies() {
		assertEquals(4,objCVM.getNumberOfArmies());
	}

	/**
	 * method that tests if it returns the correct value for the color of the country.
	 */
	@Test
	public void testGetColorOfCountry() {
		assertEquals(Colors.GREEN,objCVM.getColorOfCountry());
	}

	/**
	 * method that tests if it returns the correct value for the player id.
	 */

	@Test
	public void testGetPlayerId() {
		assertEquals(1,objCVM.getPlayerID());
	}

	/**
	 * method that tests if it fetches the neighbours from the list.
	 */
	@Test
	public void testGetNeighbours() {
		for(String s:neighbours) 
			System.out.println(s);

	}
}
