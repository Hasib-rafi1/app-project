package strategies;

import model.Country;
import model.Player;

import java.io.Serializable;

/**
 * This class is used for human player that requires user interaction to make decisions.
 * @author 
 * @version 1.0.0
 *
 */
public class Human implements PlayerStrategy, Serializable {

    public String strategyName = "Human";


    public String getStrategyName(){
        return strategyName;
    }

    public boolean isHuman(){
        return true;
    }

    public boolean reinforce(Player player){
        return true;
    }

    public void attack(Player player){

    }

    public boolean fortify(Player thisPlayer){

        Player player = thisPlayer;
        Country sourceCountry = player.getSourceCountry();
        Country destinationCountry = player.getDestinationCountry();
        int armies = player.getArmies();

        if(!checkFortificationCondition(sourceCountry, destinationCountry,armies)) {
            return false;
        }

        sourceCountry.decreaseArmyCount(armies);
        destinationCountry.increaseArmyCount(armies);
        return true;
    }

    public boolean checkFortificationCondition(Country sourceCountry, Country destinationCountry, int armies) {
        if (sourceCountry == null || destinationCountry == null) {
            System.out.println("Source or destination country is invalid!");
            return false;
        }
        if (armies == 0) {
            System.out.println("No armies to move");
            return false;
        }
        return true;
    }
}
