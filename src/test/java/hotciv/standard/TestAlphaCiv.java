package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static hotciv.framework.GameConstants.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;
import java.util.*;

/**
 * Skeleton class for AlphaCiv test cases
 * <p>
 * Updated Oct 2015 for using Hamcrest matchers
 * <p>
 * This source code is from the book
 * "Flexible, Reliable Software:
 * Using Patterns and Agile Development"
 * published 2010 by CRC Press.
 * Author:
 * Henrik B Christensen
 * Department of Computer Science
 * Aarhus University
 * <p>
 * Please visit http://www.baerbak.com/ for further information.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class TestAlphaCiv {
    private Game game;

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl();
    }

    @Test
    public void gameAgeStartsAt4000BC() {
        // check that starting age is 4000 BC
        assertThat(game.getAge(), is(-4000));
    }

    @Test
    public void checkWinnerAt3000BC() {
        assertThat(game.getWinner(), is(nullValue())); //No winner before 3000BC
        ((GameImpl) game).setAge(-3000);
        assertThat(game.getAge(), is(-3000));
        // check that at year 3000 BC, red player wins
        assertThat(game.getWinner(), is(Player.RED));
    }

    @Test
    public void checkStartingUnits() {
        game = new GameImpl();
        Unit unit1 = game.getUnitAt(new Position(2, 0));
        Unit unit2 = game.getUnitAt(new Position(3, 2));
        Unit unit3 = game.getUnitAt(new Position(4, 3));

        assertThat(unit1.getOwner(), is(Player.RED));
        assertThat(unit1.getTypeString(), is(ARCHER));
        assertThat(unit2.getOwner(), is(Player.BLUE));
        assertThat(unit2.getTypeString(), is(GameConstants.LEGION));
        assertThat(unit3.getOwner(), is(Player.RED));
        assertThat(unit3.getTypeString(), is(GameConstants.SETTLER));
    }

    @Test
    public void checkUnitMove() {
        game = new GameImpl();
        Position start = new Position(0, 0);
        ((GameImpl) game).createUnit(ARCHER, start, Player.RED);
        assertThat(game.moveUnit(start, new Position(13, 13)), is(false));
        assertThat(game.moveUnit(start, new Position(1, 0)), is(false)); //Ocean tile
        assertThat(game.moveUnit(start, new Position(0, 1)), is(true));
    }

    @Test
    public void checkValidOwner() {
        game = new GameImpl();

        assertThat(game.getPlayerInTurn(), is(Player.RED)); // Red player's turn
        Position start = new Position(0, 0);
        // archer unit is produced
        ((GameImpl) game).createUnit(ARCHER, start, Player.RED);

        game.endOfTurn(); // now Blue's turn

        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
        // Blue tries to move red's archer
        assertThat(game.moveUnit(start, new Position(1, 0)), is(false));

        // Blue produces own archer and moves unit
        Position blueStart = new Position(0, 0);
        Position blueTo = new Position(0, 1);
        ((GameImpl) game).createUnit(ARCHER, blueStart, Player.BLUE);
        assertThat(game.moveUnit(start, blueTo), is(true));

        game.endOfTurn(); // now Red's turn
        // Red tries to move Blue's archer
        assertThat(game.moveUnit(blueTo, new Position(2, 0)), is(false));
    }

    @Test
    public void checkUnitAttack() {
        game = new GameImpl();
        Position red = new Position(1, 0);
        Position blue = new Position(2, 0);
        ((GameImpl) game).createUnit(GameConstants.LEGION, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, red), is(false));
        assertThat(game.moveUnit(red, blue), is(true));
        assertThat(game.getUnitAt(blue).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(blue).getTypeString(), is(GameConstants.LEGION));
    }

    @Test
    public void checkWorldLayout() {
        game = new GameImpl();
        Position x = new Position(1, 0);
        assertThat(game.getTileAt(x).getTypeString(), is(GameConstants.OCEANS));
    }

    @Test
    public void checkStartingCities() {
        game = new GameImpl();
        assertThat(game.getCityAt(new Position(1, 1)).getOwner(), is(Player.RED));

        game.getCityAt(new Position(4,1));
        game.changeProductionInCityAt(new Position(4,1), LEGION);
        assertThat(game.getCityAt(new Position(4,1)).getProduction(),is(LEGION));
    }

    @Test
    public void checkPlayersInTurn() {
        game = new GameImpl();
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.BLUE));
        game.endOfTurn();
        assertThat(game.getPlayerInTurn(), is(Player.RED));
        assertThat(game.getCityAt(new Position(1, 1)).getTreasury(), is(6));
        assertThat(game.getCityAt(new Position(4, 1)).getTreasury(), is(6));
    }

    @Test
    public void checkUnitProduction() {
        // unit spawning requirement testing was done with </tests/java/hotciv/utility/TestIterators.java>
        game = new GameImpl();
        Position cityPos = new Position(1, 1);
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
        assertThat(game.getUnitAt(new Position(0,1)).getTypeString(), is(LEGION));
    }
}
