package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.ZetaCivFactory;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestZetaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new ZetaCivFactory());
    }

    @Test
    public void checkRoundIncrements() {
        for(int i = 1; i <= 20; i++) {
            assertThat(game.getCurrentRound(), is(i));
            game.endOfTurn();
            game.endOfTurn();
        }
    }

    @Test
    public void checkCorrectAgingStrategy() {
        for(int i = 1; i <= 10; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }

        // after 10 rounds, age should be 3000BC with alphaCiv aging strategy
        assertThat(game.getAge(), is(-3000));
    }
    @Test
    public void checkBetaWinnerStrategyUse() {
        // game round is at 20
        for(int i = 1; i <= 19; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }

        // red player owns all cities before round 21 and wins game
        for(City city: game.getCities())
            city.setPlayer(Player.RED);

        assertThat(game.getWinner(), is(Player.RED));
    }
    @Test
    public void checkEpsilonWinnerStrategyUse() {
        for(int i = 1; i <= 20; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }

        Position red = new Position(6, 5);
        Position blue = new Position(6, 6);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, blue), is(true));

        Position red2 = new Position(5, 7);
        Position blue2 = new Position(6, 7);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red2, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue2, Player.BLUE);
        assertThat(game.moveUnit(red2, blue2), is(true));

        Position red3 = new Position(7, 3);
        Position blue3 = new Position(7, 4);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red3, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue3, Player.BLUE);
        assertThat(game.moveUnit(red3, blue3), is(true));

        assertThat(game.getWinner(), is(Player.RED));
    }
    @Test
    public void checkStrategyStateUse() {
        // checks that epsilon winning strategy is not used until after round 20

        // before round 20
        Position red = new Position(5, 3);
        Position blue = new Position(5, 4);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, blue), is(true));

        Position red2 = new Position(5, 5);
        Position blue2 = new Position(5, 6);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red2, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue2, Player.BLUE);
        assertThat(game.moveUnit(red2, blue2), is(true));

        Position red3 = new Position(5, 8);
        Position blue3 = new Position(5, 9);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red3, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue3, Player.BLUE);
        assertThat(game.moveUnit(red3, blue3), is(true));

        assertThat(game.getWinner(), is(nullValue()));

        // after round 20
        for(int i = 1; i <= 20; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
        assertThat(game.getWinner(), is(nullValue()));

        Position red4 = new Position(7, 4);
        Position blue4 = new Position(7, 5);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, blue), is(true));

        Position red5 = new Position(6, 5);
        Position blue5 = new Position(6, 6);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red2, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue2, Player.BLUE);
        assertThat(game.moveUnit(red2, blue2), is(true));

        Position red6 = new Position(6, 3);
        Position blue6 = new Position(5, 3);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red3, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue3, Player.BLUE);
        assertThat(game.moveUnit(red3, blue3), is(true));

        assertThat(game.getWinner(), is(Player.RED));
    }
}