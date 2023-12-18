package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.strategies.UnitAction;
import hotciv.standard.factories.GammaCivFactory;
import hotciv.standard.variants.UniqueUnitActions;
import org.hamcrest.Factory;
import org.junit.Before;
import org.junit.Test;
import static hotciv.framework.GameConstants.ARCHER;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestGammaCiv {
    private Game game;
    @Before
    public void setUp() {
        game = new GameImpl(new GammaCivFactory());
    }

    //Unit tests
    @Test
    public void testSettlerAction(){
        CivFactory f = new GammaCivFactory();
        game = new GameImpl(f);
        UnitAction actions = new UniqueUnitActions();
        Position p = new Position(12,12);
        ((GameImpl) game).createUnit(GameConstants.SETTLER, p, Player.BLUE);
        actions.performUnitActionAt(p, game, f.getWorldLayoutStrategy());
        assertThat(game.getUnitAt(p), is(nullValue()));
        assertNotSame(game.getCityAt(p), nullValue());
        assertThat(game.getCityAt(p).getOwner(), is(Player.BLUE));
    }

    @Test
    public void testArcherAction(){
        CivFactory f = new GammaCivFactory();
        game = new GameImpl(f);
        UnitAction actions = new UniqueUnitActions();
        Position p = new Position(9,9);
        ((GameImpl) game).createUnit(ARCHER, p, Player.RED);
        actions.performUnitActionAt(p, game, f.getWorldLayoutStrategy());
        assertEquals(game.getUnitAt(p).getDefensiveStrength(), 2*GameConstants.unitInfo.get(ARCHER)[1]);
        assertEquals(game.getUnitAt(p).getMoveCount(), 0);
    }

    //Integration tests
    @Test
    public void testPreplacedSettler(){
        Position p = new Position(4, 3); //Preplaced Red Settler
        game.performUnitActionAt(p);
        assertThat(game.getUnitAt(p), is(nullValue()));
        assertNotSame(game.getCityAt(p), nullValue());
        assertThat(game.getCityAt(p).getOwner(), is(Player.RED));
    }

    @Test
    public void testPreplacedArcher(){
        Position p = new Position(2,0); //Preplaced Archer
        game.performUnitActionAt(p);
        assertEquals(game.getUnitAt(p).getDefensiveStrength(), 2*GameConstants.unitInfo.get(ARCHER)[1]);
        assertEquals(game.getUnitAt(p).getMoveCount(), 0);
        game.endOfTurn();
        assertEquals(game.getUnitAt(p).getMoveCount(), 0);
        game.performUnitActionAt(p);
        assertEquals(game.getUnitAt(p).getDefensiveStrength(), GameConstants.unitInfo.get(ARCHER)[1]);
        assertEquals(game.getUnitAt(p).getMoveCount(), 1);
    }
}
