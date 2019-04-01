package strategies;

import model.Player;

public interface PlayerStrategy {

    public String getStrategyName();

    public boolean isHuman();

    public boolean reinforce(Player player);

    public void attack(Player player);

    public void fortify(Player player);

}
