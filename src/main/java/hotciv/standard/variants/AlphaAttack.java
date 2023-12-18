package hotciv.standard.variants;

import hotciv.framework.Game;
import hotciv.framework.Position;
import hotciv.framework.strategies.Attack;

public class AlphaAttack implements Attack {
    public boolean performUnitAttack(Position from, Position to, Game game) {
        return true;
    }
}
