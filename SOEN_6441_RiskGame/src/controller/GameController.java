package controller;

import views.MapView;

/**
 * This class is used to handle the operations to generate, edit the map.
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class GameController {
	/**
	 * This function is used to create a map.
	 * 
	 */
	public static void generateMap() {		
		MapView showMapView = new MapView();   // calling the view
		showMapView.createJFrame();
		
	}


	public static void editMap() {
		// TODO Auto-generated method stub
		System.out.println("case2 ");

	}

	public static void startGame() {
		// TODO Auto-generated method stub
		System.out.println("case3 ");		
	}

	public static void loadGame() {
		// TODO Auto-generated method stub
		System.out.println("case4 ");		
	}



}
