package strategies;

/**
 * This class is used for aggressive computer player strategy that focuses on attack (reinforces its strongest country, 
 * then always attack with it until it cannot attack anymore, then fortifies in order to 
 * maximize aggregation of forces in one country).
 * @author 
 * @version 1.0.0
 *
 */
public class Aggressive {
	public String strategyName = "Aggressive";

    public String getStrategyName(){
        return strategyName;
    }
	public boolean isHuman() {
		return false;
	}

}
