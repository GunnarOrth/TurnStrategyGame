package hotciv.framework.strategies;

import hotciv.framework.Game;
import hotciv.framework.Player;

public interface Winning {
    /**
     * return the player that has won the game.
     * @param game instance of the game.
     * @return the player that has won. If the game is still
     * not finished then return null.
     */
    public Player getWinner(Game game);
}
