package hotciv.standard.variants;

import hotciv.framework.*;
import hotciv.framework.strategies.UnitAction;
import hotciv.framework.strategies.WorldLayout;

import static hotciv.framework.GameConstants.*;

public class UniqueUnitActions implements UnitAction {
    public void performUnitActionAt(Position p, Game game, WorldLayout layout) {
        Unit unit = game.getUnitAt(p);
        if (unit == null) { //If unit does not exist at position
            return;
        }
        if (unit.getTypeString() == ARCHER) {
            if (unit.getDefensiveStrength() == 2 * GameConstants.unitInfo.get(ARCHER)[1]) { //Already fortified
                unit.setMaxMoves(GameConstants.unitMaxMoves);
                unit.setDefense(unit.getDefensiveStrength() / 2);
            } else {
                unit.setMaxMoves(0);
                unit.setDefense(unit.getDefensiveStrength() * 2);
            }
        } else if (unit.getTypeString() == SETTLER) {
            game.deleteUnit(p);
            game.createCity(p, unit.getOwner());
        }
        else if(unit.getTypeString() == UFO){
            City c = game.getCityAt(p);
            if (c == null && layout.getTileAt(p).equals(FOREST)) { //Plains tile under UFO
                layout.setTile(p, PLAINS);
            }
            else if(c != null){
                c.setPopulation(c.getPopulation() - 1);
                if(c.getPopulation() == 0){
                    layout.deleteCity(p);
                }
            }
        }
    }
}
