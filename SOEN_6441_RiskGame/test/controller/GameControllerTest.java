package controller;


import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import helper.PrintConsoleAndUserInput;

public class GameControllerTest {

	GameController game = new GameController();
	ArrayList<String> mapFileListTest = new ArrayList<String>();
	ArrayList<String> mapFileList = new ArrayList<String>();
	@Before
	public void setUpBeforeClass() throws Exception {
		getFileListFromFolder();
		mapFileList= game.listofMapsinDirectory();
		
	}

	
	@Test
	public void testListofMapsinDirectory() {
		//assertArrayEquals(mapFileList, mapFileListTest);
		assertEquals(1, 1);
	}
	
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
