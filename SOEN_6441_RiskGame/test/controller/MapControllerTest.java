package controller;

import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import helper.PrintConsoleAndUserInput;
/**
 * @author Gargi Sharma
 * @version 1.0.0
 *
 */
public class MapControllerTest {

	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	ArrayList<String> expectedMapFileListTest = new ArrayList<String>();
	ArrayList<String> actualMapFileList = new ArrayList<String>();



	@BeforeClass
	public static void setUpBeforeClass()  {
		System.out.println("These test cases are for map controller class.");
	}

	@AfterClass
	public static void tearDownAfterClass()  {
		System.out.println("These test cases are for map controller class.");
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


	/**
	 *  This function is going to test the map files from the directory.
	 */
	@Test
	public void testListofMapsinDirectory() {
		File folder = new File(print.getMapDir());
//		File folder = new File("src/mapFiles/");
		File[] listOfFiles = folder.listFiles();
		int i = 0, j = 1;
		for(File file : listOfFiles){		    
			if(file.isFile()){
				if (file.getName().toLowerCase().contains(".map")){
					expectedMapFileListTest.add(listOfFiles[i].getName());
				}
			}
			i++;
		}
		actualMapFileList= print.listofMapsinDirectory();
		assertEquals(expectedMapFileListTest, actualMapFileList);
		//	assertEquals(expected, actual);
	}

	/**
	 * This function is going to test that the world map files exists.
	 */
	@Test
	public void testMapFileExists() {
		File file = new File(PrintConsoleAndUserInput.getMapDir()+"World.map");
		assertTrue(file.exists());
	}

	/**
	 * This function is going to test that the world map file does not exist.
	 */
	@Test
	public void testMapFileDoesNotExists() {
		File file = new File(PrintConsoleAndUserInput.getMapDir()+"notExist.map");
		assertFalse(file.exists());
	}













	/* @Test
    public void generateMap() throws Exception {
    }

    @Test
    public void createAndSaveUserMap() throws Exception {
    }

    @Test
    public void checkMapFileExists() throws Exception {
    }

    @Test
    public void editMap() throws Exception {
    }

    @Test
    public void getContinentList() throws Exception {
    }*/



}