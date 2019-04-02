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

        Country country = player.getReinforceCountry();

        if(player.getNumberOfReinforcedArmies() == 0){
            System.out.println("Player "+player.getPlayerName()+": Doesn't have any Armies.");
            return false;
        }

        if(player == null){
            System.out.println("Player ID"+player.getPlayerId()+"does not exist.");
            return false;
        }

        if (country == null) {
            System.out.println("Country Name: " + country.getCountryName() + " does not exist!");
            return false;
        }

        assignReinforcement(player,country);

        return true;
    }

    public void assignReinforcement(Player player, Country country){
        player.decreaseReinforcementArmy();
        country.increaseArmyCount();
    }

    public void attack(Player player){

    }

    public boolean fortify(Player thisPlayer){

        Player player = thisPlayer;
        Country sourceCountry = player.getFortifySourceCountry();
        Country destinationCountry = player.getFortifyDestinationCountry();
        int armies = player.getFortifyArmies();

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
