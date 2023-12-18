package hotciv.standard.variants;

import hotciv.framework.City;
import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.strategies.Winning;

public class BetaWinner implements Winning {
    public Player getWinner(Game game) {
        Player winner = game.getCities().iterator().next().getOwner();
        for(City c : game.getCities()) {
            if(c.getOwner() != winner)
                return null;
        }
        return winner;
    }
}
