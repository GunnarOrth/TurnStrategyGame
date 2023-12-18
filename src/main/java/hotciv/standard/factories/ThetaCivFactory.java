package hotciv.standard.factories;

import hotciv.framework.CivFactory;
import hotciv.framework.strategies.*;
import hotciv.standard.variants.*;

public class ThetaCivFactory implements CivFactory {
    private WorldLayout layout;
    private WorldAging aging;

    public ThetaCivFactory() {
        layout = new AlphaWorldLayout();
        aging = new IncrementalWorldAging();
    }

    public UnitAction getUnitActionStrategy() {
        return new UniqueUnitActions();
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
        return new ThetaUnitProduction();
    }
}
