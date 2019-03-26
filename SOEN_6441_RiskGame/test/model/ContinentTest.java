package model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * Test class for Continent class that checks the values for the continent.
 *
 * @author naren
 */

public class ContinentTest {
	
	/** Declaring objects. */
	private ArrayList<Country> countriesOfTheContinent= new ArrayList<>();
	
	/** Continent class object. */
	Continent obj_continent;
	
	/** Country class object. */
	Country obj_country;


	/**
	 * Initializing objects and values for the test cases.
	 */
	@Before
	public void setUp()  {
		obj_continent = new Continent(1,"Asia",3);
		obj_country = new Country(1, "India");
		countriesOfTheContinent.add(obj_country);
	}

	/**
	 * test method to check if it returns correct continent id.
	 */

	@Test
	public void testGetContinentId() {
		assertEquals(1,obj_continent.getContinentID());

	}

	/**
	 * test method to check if it returns correct continent Name.
	 */
	@Test
	public void testGetContinentName() {
		assertEquals("Asia",obj_continent.getContinentName());

	}

	/**
	 * test method to check if it returns correct control value for the continent.
	 */
	@Test
	public void testGetControlValue() {
		assertEquals(3,obj_continent.getControlValue());

	}

	/**
	 * test method to check whether the country is being added to the continent.
	 */
	@Test
	public void testGetCountryList() {

		obj_continent.addCountriesToTheContinentList(obj_country);
		countriesOfTheContinent = obj_continent.getCountryList();
		assertEquals(countriesOfTheContinent,obj_continent.getCountryList());
	}

}
