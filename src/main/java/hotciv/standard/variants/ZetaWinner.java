package hotciv.standard.variants;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.strategies.Winning;

// The winner is the player that first conquers all cities in the world.
// If the game lasts more than 20 rounds then the winner is the first player to win three attacks.
// Counting of attacks won does not start until the 20th round has ended.
public class ZetaWinner implements Winning {
    Winning winningStrategy;
    boolean switched = false;
    public ZetaWinner() {
        // uses BetaCiv winning strategy by default
        winningStrategy = new BetaWinner();
    }

    public Player getWinner(Game game) {
        // after 20th round then use Epsilon winner strategy
        if(game.getCurrentRound() == 21 && !switched){
            winningStrategy = new EpsilonWinner(game);
            switched = true;
        }


        return winningStrategy.getWinner(game);
    }
}
