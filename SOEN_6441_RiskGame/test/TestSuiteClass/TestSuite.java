package TestSuiteClass;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controller.GameControllerTest;
import controller.MainControllerTest;
import controller.MainMenuTest;
import controller.MapController;
import controller.MapControllerTest;
import model.ContinentTest;
import model.CountryTest;
import model.CountryViewModelTest;
import model.GameTest;
import model.PlayerTest;

@RunWith(Suite.class)
@SuiteClasses({GameControllerTest.class,MainControllerTest.class,MainMenuTest.class,ContinentTest.class,
	CountryTest.class,CountryViewModelTest.class,GameTest.class,PlayerTest.class, MapControllerTest.class})

/**
 * 
 * @author naren
 * This is the Test Suite class
 */
public class TestSuite {

	
	

}
