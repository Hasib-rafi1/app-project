package model;

import static org.junit.Assert.*;
/**
 * @author naren
 */

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ContinentTest {

	Continent obj_continent;
	
	
	@Before
	public void setUp() throws Exception  {
		obj_continent = new Continent(1,"Asia",3);
		
		
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
	
	
	
	
	
	
}
