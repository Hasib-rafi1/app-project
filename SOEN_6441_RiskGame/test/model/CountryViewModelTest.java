package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import helper.Colors;
public class CountryViewModelTest {

	CountryViewModel obj_CVM = new CountryViewModel();
	private ArrayList<String> neighbours = new ArrayList<>();
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
	
	@Test
	public void testGetCountryId() {
		assertEquals(1,obj_CVM.getCountryId());
	}
	
	@Test
	public void testGetCountryName() {
		assertEquals("Canada",obj_CVM.getCountryName());
	}
	
	@Test
	public void testGetxCoordinate() {
		assertEquals(120,obj_CVM.getxCoordinate());
	}
	
	@Test
	public void testGetyCoordinate() {
		assertEquals(100,obj_CVM.getyCoordinate());
	}
	
	@Test
	public void testGetnumberOfArmies() {
		assertEquals(4,obj_CVM.getNumberOfArmies());
	}
	
	@Test
	public void testGetColorOfCountry() {
		assertEquals(Colors.GREEN,obj_CVM.getColorOfCountry());
	}
	
	@Test
	public void testGetPlayerId() {
		assertEquals(1,obj_CVM.getPlayerID());
	}
	
	
	
	@Test
	public void testGetNeighbours() {
		for(String s:neighbours) 
			System.out.println(s);
		
	}
}
