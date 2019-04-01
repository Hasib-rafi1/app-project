package strategies;

import model.Player;

/**
 * This class is used for the declaration of the reinforce, attack and fortify methods.
 * @author 
 * @version 1.0.0
 *
 */
public interface PlayerStrategy {

    public String getStrategyName();

    public boolean isHuman();

    public boolean reinforce(Player player);

    public void attack(Player player);

    public void fortify(Player player);

}
