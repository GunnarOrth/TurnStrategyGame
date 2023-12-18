package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.strategies.WorldLayout;
import hotciv.standard.factories.DeltaCivFactory;
import hotciv.standard.variants.*;
import org.junit.Before;
import org.junit.Test;

import static hotciv.framework.GameConstants.*;
import static hotciv.framework.GameConstants.LEGION;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TestDeltaCiv {
    private Game game;

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl(new DeltaCivFactory());
    }

    @Test
    public void checkCorrectCities()
    {
        assertThat(game.getCityAt(new Position(8, 12)).getOwner(), is(Player.RED));
        assertThat(game.getCityAt(new Position(4, 5)).getOwner(), is(Player.BLUE));
    }

    @Test
    public void checkCorrectTiles()
    {
        assertThat(game.getTileAt(new Position(15,0)).getTypeString(), is(OCEANS));
        assertThat(game.getTileAt(new Position(0,5)).getTypeString(), is(MOUNTAINS));
        assertThat(game.getTileAt(new Position(1,9)).getTypeString(), is(FOREST));
        assertThat(game.getTileAt(new Position(1,4)).getTypeString(), is(HILLS));
    }

    @Test
    public void CheckTileAtDelta()
    {
        WorldLayout delta = new DeltaWorldLayout();

        assertThat(delta.getTileAt(new Position(15,0)).getTypeString(), is(OCEANS));
        assertThat(delta.getTileAt(new Position(0,5)).getTypeString(), is(MOUNTAINS));
        assertThat(delta.getTileAt(new Position(1,9)).getTypeString(), is(FOREST));
        assertThat(delta.getTileAt(new Position(1,4)).getTypeString(), is(HILLS));
    }

    @Test
    public void CheckCityAtDelta()
    {
        WorldLayout delta = new DeltaWorldLayout();

        assertThat(delta.getCityAt(new Position(8, 12)).getOwner(), is(Player.RED));
        assertThat(delta.getCityAt(new Position(4, 5)).getOwner(), is(Player.BLUE));
    }
}
