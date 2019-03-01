package TestSuiteClass;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controller.GameControllerTest;
import model.GameTest;

@RunWith(Suite.class)
@SuiteClasses({GameControllerTest.class,GameTest.class,})

/**
 * 
 * @author naren
 * This is the Test Suite class
 */
public class TestSuite {

	
	

}
