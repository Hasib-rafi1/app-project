package model;

/**
 * This is a main class to read and store different elements of the continent
 * @author Zakiya Jafrin
 * @version 1.0.0
 */
public class Continent {

    private int continentID;
    private String continentName;
    private int controlValue;

    Continent(int continentID, String continentName, int controlValue){

        this.continentID = continentID;
        this.continentName = continentName;
        this.controlValue = controlValue;
    }

    public int getContinentID() {
        return continentID;
    }

    public String getContinentName() {
        return continentName;
    }

    public int getControlValue() {
        return controlValue;
    }
}
