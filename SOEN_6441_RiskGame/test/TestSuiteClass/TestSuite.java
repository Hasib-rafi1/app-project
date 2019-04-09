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
 * This is the Test Suite class
 * @author naren
 * @version 1.0.0
 */
public class TestSuite {




}
