package hotciv.standard.factories;

import hotciv.framework.CivFactory;
import hotciv.framework.strategies.*;
import hotciv.standard.variants.*;

public class ZetaCivFactory implements CivFactory {
    private WorldLayout layout;
    private WorldAging aging;
    private Winning winning;
    public ZetaCivFactory() {
        layout = new AlphaWorldLayout();
        aging = new IncrementalWorldAging();
        winning = new ZetaWinner();
    }

    public UnitAction getUnitActionStrategy() {
        return new NoUnitActions();
    }

    public Winning getWinningStrategy() {
        return winning;
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
