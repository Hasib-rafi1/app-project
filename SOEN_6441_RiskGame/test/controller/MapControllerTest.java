package controller;

import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import helper.PrintConsoleAndUserInput;
/**
 * @author Gargi Sharma
 * @version 1.0.0
 *
 */
public class MapControllerTest {
	
	MapController mapcontroller = new MapController();
	ArrayList<String> expectedMapFileListTest = new ArrayList<String>();
	ArrayList<String> actualMapFileList = new ArrayList<String>();
	

	/**
	 * This is test Method which runs before each test case
	 */
	@Before
	public void setUp() {
			System.out.println("Start running the test cases \n");
	}	
	

	/**
	 *  This function is going to test the map files from the directory.
	 */
	@Test
	public void testListofMapsinDirectory() {
		File folder = new File(PrintConsoleAndUserInput.getMapDir());
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
		actualMapFileList= mapcontroller.listofMapsinDirectory();
		assertEquals(expectedMapFileListTest, actualMapFileList);
		//	assertEquals(expected, actual);
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