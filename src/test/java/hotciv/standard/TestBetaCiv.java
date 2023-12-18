package hotciv.standard;

import hotciv.framework.*;
import hotciv.framework.strategies.WorldAging;
import hotciv.standard.factories.BetaCivFactory;
import hotciv.standard.variants.*;
import org.junit.Before;
import org.junit.Test;

import static hotciv.framework.GameConstants.ARCHER;
import static hotciv.framework.GameConstants.age_start;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TestBetaCiv {
    private Game game;

    @Before
    public void setUp() {
        game = new GameImpl(new BetaCivFactory());
    }

    // Unit tests
    @Test
    public void checkWorldAge_increments_100() {
        WorldAging aging = new BetaWorldAging();
        aging.setAge(age_start);
        assertThat(aging.getAge(), is(age_start));

        aging.advanceAge();
        assertThat(aging.getAge(), is(-3900));
    }

    @Test
    public void checkWorldAge_increments_50() {
        // test when world age is 50AD
        WorldAging aging = new BetaWorldAging();
        aging.setAge(50);
        // age increments by 50 years
        aging.advanceAge();
        assertThat(aging.getAge(), is(100));
    }
    @Test
    public void checkWorldAge_increments_25() {
        // test when world age is 1750
        WorldAging aging = new BetaWorldAging();
        aging.setAge(1750);
        aging.advanceAge();
        // age increments by 25 years
        assertThat(aging.getAge(), is(1775));
    }

    @Test
    public void checkWorldAge_increments_5() {
        // test when world age is after 1900
        WorldAging aging = new BetaWorldAging();
        aging.setAge(1900);
        aging.advanceAge();
        // age increments by 5
        assertThat(aging.getAge(), is(1905));
    }

    @Test
    public void checkWorldAge_increments_1() {
        // test when world age is after 1970
        WorldAging aging = new BetaWorldAging();
        aging.setAge(1970);
        aging.advanceAge();
        // age increments by 1
        assertThat(aging.getAge(), is(1971));
    }

    @Test
    public void checkWorldAge_around_BC() {
        // test when world age approaches birth of christ
        // follows sequence (100BC, 1BC, 1AD, 50AD)
        ((GameImpl) game).setAge(-200);
        game.endOfTurn();
        game.endOfTurn();

        assertThat(game.getAge(), is(-100));

        game.endOfTurn();
        game.endOfTurn();
        // next round, age is 1BC
        assertThat(game.getAge(), is(-1));

        game.endOfTurn();
        game.endOfTurn();
        // next round, age is 1AD
        assertThat(game.getAge(), is(1));

        game.endOfTurn();
        game.endOfTurn();
        // next round, age is 50AD
        assertThat(game.getAge(), is(50));

    }

    // integration tests
    @Test
    public void test_worldAge_everyRound() {
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
    // check that winner is declared when a single player owns all cities
    public void CheckWinner() {

        for(City city: game.getCities())
            city.setPlayer(Player.RED);

        assertThat(game.getWinner(), is(Player.RED));

    }
}
