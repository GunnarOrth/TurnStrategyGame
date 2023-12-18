package hotciv.standard.variants;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.strategies.Winning;

public class EpsilonWinner implements Winning {
    public EpsilonWinner(){};
    public EpsilonWinner(Game game){
        game.setBlueAttacks(0);
        game.setRedAttacks(0);
    }
    public Player getWinner(Game game) {
        if(game.getREDSuccessfulAttacks() >= 3)
        {
            return Player.RED;
        }
        else if(game.getBLUESuccessfulAttacks() >= 3)
        {
            return Player.BLUE;
        }
        return null;
    }
}
