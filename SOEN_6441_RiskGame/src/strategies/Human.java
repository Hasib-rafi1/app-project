package strategies;

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

    public void fortify(Player player){

    }

}
