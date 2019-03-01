package controller;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import helper.PrintConsoleAndUserInput;

public class MainMenuTest {
	
	MainMenu menu = new MainMenu();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("These test cases are for main menu controller class.");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("These test cases are for main menu controller class.");
	}

	/**
	 * This is test Method which runs before each test case
	 */
	@Before
	public void setUp() {
			System.out.println("Starting the test case");
	}
	
	
	/**
	 * This is test Method which runs after each test case
	 */
	@After
	public void tearDown() {
			System.out.println("\nEnding the test case");
	}
	
	


	
	@Test
	public void testDisplaymainMenu() {
		//fail("Not yet implemented");
	}

	




}
