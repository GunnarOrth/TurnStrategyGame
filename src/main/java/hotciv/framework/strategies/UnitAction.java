package hotciv.framework.strategies;

import hotciv.framework.Game;
import hotciv.framework.Position;

public interface UnitAction {
    /**
     * perform the action associated with the unit at position p.
     * Example: a settler unit may create a new city at its location.
     * Precondition: there is a unit at location 'p'.
     *
     * @param p the position of a unit that must perform its action.
     *          Nothing happens in case the unit has no associated action.
     * @param game instance of game that unit is in.
     */
    public void performUnitActionAt(Position p, Game game, WorldLayout layout);
}
