package hotciv.standard.factories;

import hotciv.framework.CivFactory;
import hotciv.framework.strategies.*;
import hotciv.standard.variants.*;

public class DeltaCivFactory implements CivFactory {
    private WorldLayout layout;
    private WorldAging aging;

    public DeltaCivFactory() {
        layout = new DeltaWorldLayout();
        aging = new IncrementalWorldAging();
    }

    public UnitAction getUnitActionStrategy() {
        return new NoUnitActions();
    }

    public Winning getWinningStrategy() {
        return new RedWins();
    }

    public WorldAging getWorldAgingStrategy() {
        return aging;
    }

    public WorldLayout getWorldLayoutStrategy() {
        return layout;
    }

    public Attack getAttackStrategy() {
        return new AlphaAttack();
    }

    public UnitProduction getUnitProductionStrategy() {
        return new AlphaUnitProduction();
    }
}
