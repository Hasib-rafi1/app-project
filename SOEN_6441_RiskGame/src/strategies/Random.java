package strategies;

import model.Country;
import model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


/**
 * This class is used for A random computer player strategy that reinforces random a random country,
 * attacks a random number of times a random country, 
 * and fortifies a random country, all following the standard rules for each phase.
 * @author 
 * @version 1.0.0
 *
 */
/*public class Random implements PlayerStrategy, Serializable {

    private String strategyName = "Random";

    public String getStrategyName(){
        return strategyName;
    }

    public boolean isHuman(){
        return false;
    }

    public boolean reinforce(Player player){

       ArrayList<Country> countryList = player.getAssignedListOfCountries();
        int random = 0;
        if (countryList.isEmpty())
            return true;
        else if (countryList.size() > 1)
            random = getRandomNumberInRange(0, countryList.size() - 1);
        int armies = player.getNumberOfReinforcedArmies();
        Country country = countryList.get(random);

        player.setNumberOfReinforcedArmies(0);
        country.increaseArmyCount(armies);
        return true;
    }

    public boolean attack(Player player){

    }

    public boolean fortify(Player player){

        ArrayList<Country> countryList = player
    }

    public static int getRandomNumberInRange(int min, int max) {
        if(min == max)
            return min;

        if (min > max) {
            throw new IllegalArgumentException("Max value must be greater than Min value!");
        }
        java.util.Random r = new java.util.Random();
        return r.nextInt((max - min) + 1) + min;
    }

}

 */
