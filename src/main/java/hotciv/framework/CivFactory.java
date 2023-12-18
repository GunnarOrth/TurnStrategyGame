package hotciv.framework;

import hotciv.framework.strategies.*;

public interface CivFactory {
    public UnitAction getUnitActionStrategy();
    public Winning getWinningStrategy();
    public WorldAging getWorldAgingStrategy();
    public WorldLayout getWorldLayoutStrategy();
    public Attack getAttackStrategy();
    public UnitProduction getUnitProductionStrategy();
}
