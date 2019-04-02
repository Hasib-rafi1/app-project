package strategies;

/**
 * This class is used for benevolent computer player strategy that focuses on protecting its weak countries 
 * (reinforces its weakest countries, never attacks, then fortifies in order to move armies to weaker countries).
 * @author 
 * @version 1.0.0
 *
 */
public class Benevolent {
		
	public String strategyName = "Benevolent";

    public String getStrategyName(){
        return strategyName;
    }
	public boolean isHuman() {
		return false;
	}

}
