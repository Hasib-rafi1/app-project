package strategies;

/**
 * This class is used for A random computer player strategy that reinforces random a random country,
 * attacks a random number of times a random country, 
 * and fortifies a random country, all following the standard rules for each phase.
 * @author 
 * @version 1.0.0
 *
 */
public class Random {
	public String strategyName = "Random";

    public String getStrategyName(){
        return strategyName;
    }
	public boolean isHuman() {
		return false;
	}
}
