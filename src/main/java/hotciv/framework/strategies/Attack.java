package hotciv.framework.strategies;

import hotciv.framework.Game;
import hotciv.framework.Position;

public interface Attack {
    /**
     * performs a unit attack assuming the unit at from is moving to unit at to, and there is an enemy unit on to
     * @param from start position
     * @param to end position
     * @param game instance of game
     */
    public boolean performUnitAttack(Position from, Position to, Game game);
}
