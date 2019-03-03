package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class MapModelTest {
    private StringBuffer stringForValid;
    private StringBuffer stringForInValid;
    private ArrayList<String> continentsListTest = new ArrayList<>();
    private ArrayList<String> territoriesListTest = new ArrayList<>();
    private MapModel mapModel;
    private String inValidMap;

    @Before
    public void setUp() throws Exception {
        stringForValid = new StringBuffer();
        stringForInValid = new StringBuffer();
        mapModel = new MapModel();

        String validMapString = "[Map]\n"
                + "author=Zakiya-Test\n"
                + "[Continents]\n"
                + "Continent1=10\n"
                + "Continent2=20\n"
                + "[Territories]\n"
                + "Country1,127,46,Continent1,Country2,Country3\n"
                + "Country2,193,85,Continent1,Country1,Country3,Country4\n"
                + "Country3,252,33,Continent1,Country5,Country1,Country2\n"
                + "Country4,314,94,Continent2,Country2,Country5\n"
                + "Country5,415,99,Continent2,Country4,Country3";
        stringForValid.append(validMapString);

        String inValidMapString = "[Map]\n"
                + "author=Zakiya-Test\n"
                + "[Continents]\n"
                + "Continent1=10\n"
                + "Continent2=20\n"
                + "[Territories]\n"
                + "Country1,127,46,Continent1,Country2,Country3\n"
                + "Country2,193,85,Continent1,Country1,Country3,Country4\n"
                + "Country3,252,33,Continent1,Country5,Country1,Country2\n"
                + "Country4,314,94,Continent2,Country2,Country5\n";
        stringForInValid.append(inValidMapString);

        continentsListTest.add("Continent1");
        continentsListTest.add("Continent2");
        territoriesListTest.add("Country1");
        territoriesListTest.add("Country2");
        territoriesListTest.add("Country3");
        territoriesListTest.add("Country4");
        territoriesListTest.add("Country5");

        Collections.sort(continentsListTest);
        Collections.sort(territoriesListTest);

        inValidMap = inValidMapString;

    }


    @After
    public void tearDown() throws Exception {
        stringForValid = null;
        stringForInValid = null;
        mapModel = null;
    }

    @Test
    public void readMapFileTest() {
        ArrayList<String> continentsListFromFile = new ArrayList<String>();
        ArrayList<String> territoriesListFromFile = new ArrayList<String>();
        mapModel.readMapFile("src/mapFiles/validMapTest.map");
        for (Continent conObj : mapModel.getContinentList()) {
            continentsListFromFile.add(conObj.getContinentName());
            for (Country terObj : conObj.getCountryList()) {
                if (!(territoriesListFromFile.contains(terObj.getCountryName()))) {
                    territoriesListFromFile.add(terObj.getCountryName());
                }
            }
        }
        Collections.sort(continentsListFromFile);
        Collections.sort(territoriesListFromFile);
        assertEquals(continentsListFromFile, continentsListTest);
        assertEquals(territoriesListFromFile, territoriesListTest);
    }


    @Test
    public void checkIfValidMapCreated() throws Exception {
        String mapName = "validMapTestCreated";
        boolean isMapCreated = mapModel.createValidateAndSaveMap(stringForValid, mapName);
        assertTrue(isMapCreated);
    }

    @Test
    public void checkIfInValidMapCreated() throws Exception {
        String mapName = "inValidMapNoTCreated";
        boolean isMapCreated = mapModel.createValidateAndSaveMap(stringForInValid, mapName);
        assertFalse(isMapCreated);
    }

    @Test
    public void checkMapIsValidTest() throws Exception {
        mapModel.readMapFile(inValidMap);
        assertFalse(mapModel.checkMapIsValid());
    }

}