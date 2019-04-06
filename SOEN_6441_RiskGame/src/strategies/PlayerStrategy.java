package strategies;

import model.Player;

/**
 * This class is used for the declaration of the reinforce, attack and fortify methods.
 * 
 * @author Jaiganesh
 * @version 1.0.0
 *
 */
public interface PlayerStrategy {

	/** Gets the name of the strategy*/
    public String getStrategyName();

    /** Gets if the player is a human or non-human*/
    public boolean isHuman();

    /** Gets a boolean value for if to perform reinforcement operation of a particular strategy */
    public boolean reinforce(Player player);

    /** Gets a boolean value for if to perform attack operation of a particular strategy */
    public boolean attack(Player player);

    /** Gets a boolean value for if to perform fortification operation of a particular strategy */
    public boolean fortify(Player player);

}