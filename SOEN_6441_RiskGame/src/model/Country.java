package model;

import java.util.ArrayList;

/**
 * This is a main class to read and store different elements of the country
 * @author Zakiya Jafrin
 * @version 1.0.0
 */

public class Country {
    private int countryId;
    private String countryName;
    private int xCoordinate;
    private int yCoordinate;

    Country (int countryId, String countryName, int xCoordinate, int yCoordinate)
    {
        this.countryId = countryId;
        this.countryName = countryName;
        this.xCoordinate =xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public int getCountryId()
    {
        return countryId;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public int getxCoordinate()
    {
        return xCoordinate;
    }

    public int getyCoordinate()
    {
        return yCoordinate;
    }


}
