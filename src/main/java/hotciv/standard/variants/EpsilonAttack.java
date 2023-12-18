package hotciv.standard.variants;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.strategies.Attack;
import hotciv.framework.Unit;
import hotciv.framework.strategies.UnitAction;
import hotciv.utility.Utility2;

import static hotciv.framework.GameConstants.ARCHER;
import static hotciv.framework.GameConstants.SETTLER;
import static hotciv.framework.GameConstants.LEGION;

public class EpsilonAttack implements Attack {
    public boolean performUnitAttack(Position from, Position to, Game game) {
        Unit attacker = game.getUnitAt(from);
        Unit defender = game.getUnitAt(to);

        int A = (attacker.getAttackingStrength()+ Utility2.getFriendlySupport(game, from, attacker.getOwner())) * Utility2.getTerrainFactor(game, from);
        int D = (defender.getDefensiveStrength()+Utility2.getFriendlySupport(game, to, defender.getOwner())) * Utility2.getTerrainFactor(game, to);

        int d = (int)Math.floor(Math.random()*(6-1+1)+1);
        int d2 = (int)Math.floor(Math.random()*(6-1+1)+1);

        if((A*d) > (D*d2))
        {
            return true;
        }
        return false;
    }
}
