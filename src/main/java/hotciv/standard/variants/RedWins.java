package hotciv.standard.variants;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.strategies.Winning;

import static hotciv.framework.GameConstants.age_end;

public class RedWins implements Winning {
    public Player getWinner(Game game) {
        // iteration 1 requires red player wins in 3000 BC
        return game.getAge() == age_end ? Player.RED : null;
    }
}
