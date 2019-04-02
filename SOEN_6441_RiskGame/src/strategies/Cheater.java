package strategies;

/**
 * This class is used for cheater computer player strategy whose reinforce() method doubles the number of armies on all its countries,
 * whose attack() method automatically conquers all the neighbors of all its countries, and whose fortify() method 
 * doubles the number of armies on its countries that have neighbors that belong to other players.
 * @author 
 * @version 1.0.0
 *
 */
public class Cheater {
	public String strategyName = "Cheater";

    public String getStrategyName(){
        return strategyName;
    }
	public boolean isHuman() {
		return false;
	}
}
