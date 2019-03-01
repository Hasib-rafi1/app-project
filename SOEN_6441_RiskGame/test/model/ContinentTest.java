package model;

import static org.junit.Assert.*;
/**
 * @author naren
 */

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ContinentTest {
	private ArrayList<Country> countriesOfTheContinent= new ArrayList<>();
	Continent obj_continent;
	Country obj_country;
	
	@Before
	public void setUp() throws Exception  {
		obj_continent = new Continent(1,"Asia",3);
		obj_country = new Country(1, "India");
		countriesOfTheContinent.add(obj_country);
		
	}


	@Test
	public void testGetContinentId() {
		assertEquals(1,obj_continent.getContinentID());
		
	}
	
	@Test
	public void testGetContinentName() {
		assertEquals("Asia",obj_continent.getContinentName());
		
	}
	
	@Test
	public void testGetControlValue() {
	
		
		assertEquals(3,obj_continent.getControlValue());
		
	}
	
	
	 @Test public void testGetCountryList() {
	   
		 obj_continent.addCountriesToTheContinentList(obj_country);
			countriesOfTheContinent = obj_continent.getCountryList();
			assertEquals(countriesOfTheContinent,obj_continent.getCountryList());
	 }
	 
	
	
	
}
