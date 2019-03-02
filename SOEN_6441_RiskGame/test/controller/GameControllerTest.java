package controller;


import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import helper.PrintConsoleAndUserInput;

/**
 * This is the test class for game controller. methods from game controller is going to tested from here
 * @author Hasibul Huq
 *
 */
public class GameControllerTest {

	GameController game = new GameController();
	ArrayList<String> mapFileListTest = new ArrayList<String>();
	ArrayList<String> mapFileList = new ArrayList<String>();

	/**
	 * THis function is getting the map files and listing in an array list when starting the class
	 * @throws Exception
	 */
	@Before
	public void setUpBeforeClass() throws Exception {
		getFileListFromFolder();
		mapFileList= game.listofMapsinDirectory();

	}

	/**
	 * Checking is the functions list is giving proper files name or not
	 */
	@Test
	public void testListofMapsinDirectory() {
		assertEquals(mapFileList, mapFileListTest);

	}

	/**
	 * This function is going to get the files from the specific folder
	 */
	@Ignore
	public void getFileListFromFolder() {
		File folder = new File(PrintConsoleAndUserInput.getMapDir());
		File[] listOfFiles = folder.listFiles();
		int i = 0, j = 1;
		for(File file : listOfFiles)
		{		    
			if(file.isFile())
			{
				if (file.getName().toLowerCase().contains(".map"))
				{
					mapFileListTest.add(listOfFiles[i].getName());
				}
			}
			i++;
		}
	}
}
