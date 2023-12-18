package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.ThetaCivFactory;
import org.junit.Before;
import org.junit.Test;

import static hotciv.framework.GameConstants.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

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
public class TestThetaCiv {
    private Game game;

    /**
     * Fixture for alphaciv testing.
     */
    @Before
    public void setUp() {
        game = new GameImpl(new ThetaCivFactory());
    }

    //TODO UFO Tests
    @Test
    public void checkUFOMove() {
        game = new GameImpl(new ThetaCivFactory());
        Position start = new Position(0, 0);
        ((GameImpl) game).createUnit(UFO, start, Player.RED);
        assertThat(game.moveUnit(start, new Position(13, 13)), is(false));
        assertThat(game.moveUnit(start, new Position(0, 2)), is(true)); //Double Move
    }

    @Test
    public void checkUFOAttack() {
        game = new GameImpl(new ThetaCivFactory());
        Position red = new Position(1, 0);
        Position blue = new Position(2, 0);
        ((GameImpl) game).createUnit(GameConstants.UFO, red, Player.RED);
        ((GameImpl) game).createUnit(GameConstants.LEGION, blue, Player.BLUE);
        assertThat(game.moveUnit(red, red), is(false));
        assertThat(game.moveUnit(red, blue), is(true));
        assertThat(game.getUnitAt(blue).getOwner(), is(Player.RED));
        assertThat(game.getUnitAt(blue).getTypeString(), is(GameConstants.UFO));
    }
    @Test
    public void checkUFOUnitProduction() {
        // unit spawning requirement testing was done with </tests/java/hotciv/utility/TestIterators.java>
        game = new GameImpl(new ThetaCivFactory());
        Position cityPos = new Position(1, 1);
        City city = game.getCityAt(cityPos);

        for(int i = 0; i < 10; i++) //10 Rounds
            city.addMoney();

        assertThat(city.getTreasury(), is(60));
        game.changeProductionInCityAt(cityPos, UFO);
        assertThat(city.getProduction(), is(UFO));
        game.produceUnit(cityPos);
        assertThat(city.getTreasury(), is(0)); //Check unit produced

        assertThat(game.getUnitAt(cityPos).getTypeString(), is(UFO)); // ufo is on city tile

    }

    @Test
    public void checkUFOAction(){
        game = new GameImpl(new ThetaCivFactory());
        Position cityPos = new Position(1, 1);
        ((GameImpl) game).createUnit(UFO, cityPos, Player.RED);
        City city = game.getCityAt(cityPos);
        assertThat(city.getPopulation(), is(1));
        game.performUnitActionAt(cityPos);
        assertThat(game.getCityAt(cityPos), is(nullValue()));
    }
}
