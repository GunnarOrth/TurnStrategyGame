package hotciv.standard.factories;

import hotciv.framework.CivFactory;
import hotciv.framework.strategies.*;
import hotciv.standard.variants.*;

public class SemiCivFactory implements CivFactory {
    private WorldLayout layout;
    private WorldAging aging;

    public SemiCivFactory() {
        layout = new DeltaWorldLayout();
        aging = new BetaWorldAging();
    }

    public UnitAction getUnitActionStrategy() {
        return new UniqueUnitActions();
    }

    public Winning getWinningStrategy() {
        return new EpsilonWinner();
    }

    public WorldAging getWorldAgingStrategy() {
        return aging;
    }

    public WorldLayout getWorldLayoutStrategy() {
        return layout;
    }

    public Attack getAttackStrategy() {
        return new EpsilonAttack();
    }

    public UnitProduction getUnitProductionStrategy() {
        return new AlphaUnitProduction();
    }
}
