package hotciv.standard;
import hotciv.framework.*;

import hotciv.standard.factories.SemiCivFactory;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;

import static hotciv.framework.GameConstants.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestSemiCiv {
    private Game game;

    @Before
    public void setup() { game = new GameImpl(new SemiCivFactory()); }

    @Test
    public void testUnitActionStrategy() {
        // test UniqueUnitActions strategy is used
        Position p = new Position(5, 5); //Pre-placed Red Settler
        game.performUnitActionAt(p);
        assertThat(game.getUnitAt(p), is(nullValue()));
        assertNotSame(game.getCityAt(p), nullValue());
        assertThat(game.getCityAt(p).getOwner(), is(Player.RED));

        // pre-placed archer
        Position p2 = new Position(3,8);
        game.performUnitActionAt(p2);
        assertEquals(game.getUnitAt(p2).getDefensiveStrength(), 2*GameConstants.unitInfo.get(ARCHER)[1]);
        assertEquals(game.getUnitAt(p2).getMoveCount(), 0);
        game.endOfTurn();
        assertEquals(game.getUnitAt(p2).getMoveCount(), 0);
        game.performUnitActionAt(p2);
        assertEquals(game.getUnitAt(p2).getDefensiveStrength(), GameConstants.unitInfo.get(ARCHER)[1]);
        assertEquals(game.getUnitAt(p2).getMoveCount(), 1);
    }

    @Test
    public void testWinningStrategy() {
        // test that EpsilonWinner is used
        Position red = new Position(5, 3);
        Position blue = new Position(5, 4);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, blue), is(true));

        Position red2 = new Position(14, 3);
        Position blue2 = new Position(14, 4);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red2, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue2, Player.BLUE);
        assertThat(game.moveUnit(red2, blue2), is(true));

        Position red3 = new Position(13, 7);
        Position blue3 = new Position(13, 8);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red3, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue3, Player.BLUE);
        assertThat(game.moveUnit(red3, blue3), is(true));

        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void testAgingStrategy() {
        // test that BetaWorldAging is used
        assertThat(game.getAge(), is(age_start));
        for(int i = 1; i <= 39; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }

        // 39 rounds in first aging interval, ends at age -100
        assertThat(game.getAge(), is(-100));

        // 3 rounds around birth of christ to follow required sequence
        for(int i = 1; i <= 3; i++) {
            game.endOfTurn();
            game.endOfTurn();
            if(i == 1)
                assertThat(game.getAge(), is(-1));
            else if(i == 2)
                assertThat(game.getAge(), is(1));
            else if(i == 3)
                assertThat(game.getAge(), is(50));
        }

        for(int i = 1; i <= 34; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }
        // 34 rounds in next aging interval, ends at age 1750
        assertThat(game.getAge(), is(1750));

        for(int i = 1; i <= 6; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }

        // 6 rounds in next aging interval, ends at age 1900
        assertThat(game.getAge(), is(1900));

        for(int i = 1; i <= 14; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }

        // 14 rounds in next aging interval, ends at age 1970
        assertThat(game.getAge(), is(1970));

        for(int i = 1; i <= 30; i++) {
            game.endOfTurn();
            game.endOfTurn();
        }

        // game ages 1 year per round after 1970
        assertThat(game.getAge(), is(2000));
    }

    @Test
    public void testWorldLayoutStrategy() {
        // test that DeltaWorldLayout is used
        assertThat(game.getTileAt(new Position(15,0)).getTypeString(), is(OCEANS));
        assertThat(game.getTileAt(new Position(0,5)).getTypeString(), is(MOUNTAINS));
        assertThat(game.getTileAt(new Position(1,9)).getTypeString(), is(FOREST));
        assertThat(game.getTileAt(new Position(1,4)).getTypeString(), is(HILLS));
    }

    @Test
    public void testAttackStrategy() {
        // test EpsilonAttack is used
        Position red = new Position(4, 5);
        Position blue = new Position(4, 6);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, blue), is(true));

        Position red2 = new Position(5, 5);
        Position blue2 = new Position(5, 6);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red2, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue2, Player.BLUE);
        assertThat(game.moveUnit(red2, blue2), is(true));

        Position red3 = new Position(5, 7);
        Position blue3 = new Position(5, 8);
        ((GameImpl) game).createUnit(GameConstants.SUPERLEGION, red3, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue3, Player.BLUE);
        assertThat(game.moveUnit(red3, blue3), is(true));
    }

    @Test
    public void testUnitProductionStrategy() {
        // test that AlphaUnitProduction is used
        game = new GameImpl(new SemiCivFactory());
        Position cityPos = new Position(8, 12);
        City city = game.getCityAt(cityPos);
        city.addMoney();
        city.addMoney();

        assertThat(city.getTreasury(), is(12));
        game.changeProductionInCityAt(cityPos, SETTLER);
        game.produceUnit(cityPos);
        assertThat(city.getTreasury(), is(12)); // assert that it did not allow player to produce unit due to lack of funds

        game.changeProductionInCityAt(cityPos, ARCHER);
        game.produceUnit(cityPos);
        assertThat(city.getTreasury(), is(2)); // allowed player to produce archer
        assertThat(game.getUnitAt(cityPos).getTypeString(), is(ARCHER)); // archer is on city tile

        city.addMoney();
        city.addMoney();
        city.addMoney();
        game.changeProductionInCityAt(cityPos, LEGION);
        game.produceUnit(cityPos);
        assertThat(game.getUnitAt(new Position(7,12)).getTypeString(), is(LEGION));
    }
}
