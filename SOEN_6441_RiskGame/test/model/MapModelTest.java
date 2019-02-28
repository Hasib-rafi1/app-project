package model;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class MapModelTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void readMapFile() throws Exception {
    }

    @Test
    public void createValidateAndSaveMap() throws Exception {
    }

    @Test
    public void checkMapIsValid() throws Exception {
    }

    @Test
    public void depthFirstSearch() throws Exception {
    }

    @Test
    public void visitedAndAllCountryListCheck() throws Exception {
    }

    @Test
    public void getCountryList() throws Exception {
    }

    @Test
    public void countryListString() throws Exception {
    }

    @Test
    public void addContinent() throws Exception {
    }

    @Test
    public void addContinentNameToMapFile() throws Exception {
    }

    @Test
    public void addCountryToContinentInMap() throws Exception {
    }

    @Test
    public void deleteContinentFromMap() throws Exception {
    }

    @Test
    public void deleteCountryFromMap() throws Exception {
    }

    @Test
    public void saveUserMapIntoDirectory() throws Exception {
    }

    @Test
    public void getMapNameByUserInput() throws Exception {
    }

    @Test
    public void getMapNameFromUser() throws Exception {
    }

    @Test
    public void getContinentList() throws Exception {
    }

    @Test
    public void saveEditedMap() throws Exception {
    }

    @Test
    public void printingContinents() throws Exception {
    }

    @Test
    public void printingTerritoriesAndNeighborCountries() throws Exception {
    }

    @Test
    public void printNeighboursGivenContry() throws Exception {
    }

    @Test
    public void printCountriesFromMap() throws Exception {
    }

    @Test
    public void getMapDir() throws Exception {
    }

    @Test
    public void getMapName() throws Exception {
    }

    @Test
    public void setMapName() throws Exception {
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(MapModel.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }


}
