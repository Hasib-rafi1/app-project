package TestSuiteClass;

import model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import controller.GameControllerTest;
import controller.MapControllerTest;

/**
 * The Class TestSuite.
 */
@RunWith(Suite.class)
@SuiteClasses({GameControllerTest.class,MapControllerTest.class,ContinentTest.class,
	CountryTest.class,CountryViewModelTest.class,GameTest.class,PlayerTest.class, MapModelTest.class})

/**
 * 
 * @author naren
 * 
 * This is the Test Suite class
 */
public class TestSuite {




}
