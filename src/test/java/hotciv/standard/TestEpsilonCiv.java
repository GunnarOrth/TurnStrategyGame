package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.standard.factories.EpsilonCivFactory;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestEpsilonCiv {

    private Game game;

    /**
     * Fixture for epsilonciv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl(new EpsilonCivFactory());
    }

    @Test
    public void checkUnitAttackFails() {
        game = new GameImpl(new EpsilonCivFactory());
        Position red = new Position(1, 0);
        Position blue = new Position(2, 0);
        ((GameImpl) game).createUnit(GameConstants.SETTLER, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, red), is(false));
        assertThat(game.moveUnit(red, blue), is(false));
        //assertThat(game.getUnitAt(blue).getOwner(), is(Player.RED));
        //assertThat(game.getUnitAt(blue).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void checkUnitAttackSucceeds() {
        game = new GameImpl(new EpsilonCivFactory());
        Position red = new Position(1, 0);
        Position blue = new Position(2, 0);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, blue), is(true));
    }

    @Test
    public void checkREDWinsAt3SuccessfulAttacks() {
        game = new GameImpl(new EpsilonCivFactory());
        Position red = new Position(1, 0);
        Position blue = new Position(2, 0);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, blue), is(true));

        Position red2 = new Position(1, 2);
        Position blue2 = new Position(0, 2);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red2, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue2, Player.BLUE);
        assertThat(game.moveUnit(red2, blue2), is(true));

        Position red3 = new Position(1, 3);
        Position blue3 = new Position(2, 3);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red3, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue3, Player.BLUE);
        assertThat(game.moveUnit(red3, blue3), is(true));

        assertThat(game.getWinner(), is(Player.RED));
    }
}
