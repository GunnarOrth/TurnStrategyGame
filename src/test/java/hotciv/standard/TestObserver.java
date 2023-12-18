package hotciv.standard;

import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static hotciv.framework.GameConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TestObserver {
    private Game game;

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl();
    }

    @Test
    public void testObserver() {
        GameObserver observer = new GameObserverSpy();
        game.addObserver(observer);
        game.moveUnit(new Position(2,0), new Position(3,0));
        game.endOfTurn();
        game.setTileFocus(new Position(4, 2));
        game.endOfTurn();
        List<List<String>> l = ((GameObserverSpy) observer).getChanges();
        assertThat(l.get(0), is(Arrays.asList("worldChange", "[2,0]")));
        assertThat(l.get(1), is(Arrays.asList("worldChange", "[3,0]")));
        assertThat(l.get(2), is(Arrays.asList("turnEnds", "BLUE", "-4000")));
        assertThat(l.get(3), is(Arrays.asList("tileFocusChanged", "[4,2]")));
        assertThat(l.get(4), is(Arrays.asList("turnEnds", "RED", "-3900")));
    }
}
