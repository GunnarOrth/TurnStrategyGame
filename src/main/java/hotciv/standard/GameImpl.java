package hotciv.standard;

import hotciv.framework.*;
import hotciv.standard.factories.AlphaCivFactory;
import hotciv.utility.Utility;
import hotciv.utility.Utility2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static hotciv.framework.GameConstants.*;

/**
 * Skeleton implementation of HotCiv.
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

public class GameImpl implements Game {
    private final Player[] players = {Player.RED, Player.BLUE};
    private int currentPlayerIndex = 0;
    private int currentRound = 1;
    private CivFactory factory;
    private GameObserver observer;
    private int REDSA = 0;

    private int BLUESA = 0;

    public GameImpl() {
        factory = new AlphaCivFactory();
        observer = new GameObserverSpy();
    }

    public GameImpl(CivFactory factory) {
        this(); //Call default constructor
        this.factory = factory;
    }


    public Tile getTileAt(Position p) {
        return factory.getWorldLayoutStrategy().getTileAt(p);
    }

    public Unit getUnitAt(Position p) {
        return factory.getWorldLayoutStrategy().getUnits().get(p);
    }

    public City getCityAt(Position p) {
        return factory.getWorldLayoutStrategy().getCityAt(p);
    }

    public Player getPlayerInTurn() {
        return players[currentPlayerIndex];
    }

    public Player getWinner() {
        return factory.getWinningStrategy().getWinner(this);
    }

    public void setAge(int age) {
        factory.getWorldAgingStrategy().setAge(age);
    }

    public int getAge() {
        return factory.getWorldAgingStrategy().getAge();
    }

    public int tileDist(Position p1, Position p2) {
        return Math.max(Math.abs(p1.getRow() - p2.getRow()), Math.abs(p1.getColumn() - p2.getColumn()));
    }

    public boolean moveUnit(Position from, Position to) {

        boolean checkUnitFromExists = factory.getWorldLayoutStrategy().getUnits().get(from) != null;
        if(!checkUnitFromExists)
            return false;

        int distance = tileDist(from, to);
        boolean checkMoveCount = distance <= factory.getWorldLayoutStrategy().getUnits().get(from).getMoveCount();
        boolean checkCorrectOwner = factory.getWorldLayoutStrategy().getUnits().get(from).getOwner() == players[currentPlayerIndex];
        String tileType = factory.getWorldLayoutStrategy().getTileAt(to).getTypeString();
        boolean mountainOrOcean = tileType.equals(OCEANS) || tileType.equals(MOUNTAINS);

        if (!checkMoveCount || !checkCorrectOwner || (mountainOrOcean && !factory.getWorldLayoutStrategy().getUnits().get(from).getTypeString().equals(UFO))){
            observer.worldChangedAt(from);
            observer.worldChangedAt(to);
            return false;
        }

        boolean checkUnitExists = factory.getWorldLayoutStrategy().getUnits().get(to) != null;
        if (checkUnitExists) {
            boolean checkDifferentUnitOwner = factory.getWorldLayoutStrategy().getUnits().get(to).getOwner() != factory.getWorldLayoutStrategy().getUnits().get(from).getOwner();
            if (!checkDifferentUnitOwner) return false;
            if (factory.getAttackStrategy().performUnitAttack(from, to, this)) {
                if (factory.getWorldLayoutStrategy().getUnits().get(from).getOwner() == Player.RED) {
                    REDSA++;
                } else {
                    BLUESA++;
                }
                deleteUnit(to);
                factory.getWorldLayoutStrategy().getUnits().put(to, factory.getWorldLayoutStrategy().getUnits().get(from));
                factory.getWorldLayoutStrategy().getUnits().remove(from);
                observer.worldChangedAt(from);
                observer.worldChangedAt(to);
                factory.getWorldLayoutStrategy().getUnits().get(to).makeMoves(distance);
                return true;
            } else {
                observer.worldChangedAt(from);
                observer.worldChangedAt(to);
                factory.getWorldLayoutStrategy().getUnits().get(from).makeMoves(distance);
                return false;
            }
            //deleteUnit(to); // Unit defeated and removed
        } else {
            factory.getWorldLayoutStrategy().getUnits().get(from).makeMoves(distance);
            factory.getWorldLayoutStrategy().getUnits().put(to, factory.getWorldLayoutStrategy().getUnits().get(from)); // Current unit moves to new tile
            factory.getWorldLayoutStrategy().getUnits().remove(from); // Remove unit from previous tile
            observer.worldChangedAt(from);
            observer.worldChangedAt(to);
            return true;
        }
    }

    public void createUnit(String unitType, Position unitPosition, Player player) {
        factory.getWorldLayoutStrategy().createUnit(unitType, unitPosition, player);
    }

    public void deleteUnit(Position p) {
        factory.getWorldLayoutStrategy().getUnits().remove(p);
    }

    public void produceUnit(Position cityPosition) {
        City currentCity = getCityAt(cityPosition);
        String unitType = currentCity.getProduction();
        List<Position> adjTiles = new ArrayList<>(Arrays.asList(cityPosition));

        if (currentCity.getProduction() == null || currentCity.getTreasury() < unitInfo.get(unitType)[0])
            return;

        for (Position p : Utility.get8neighborhoodOf(cityPosition)) {
            adjTiles.add(p);
        }

        for (Position p : adjTiles) {
            if (getUnitAt(p) == null) {
                observer.worldChangedAt(p);
                createUnit(unitType, p, currentCity.getOwner());
                currentCity.subMoney(unitInfo.get(unitType)[0]);
                return;
            }
        }
    }

    public void createCity(Position p, Player player) {
        factory.getWorldLayoutStrategy().createCity(p, player);
        observer.worldChangedAt(p);
    }

    public void endOfTurn() {
        currentPlayerIndex += 1;
            if (currentPlayerIndex == players.length) { // if end of round
            currentPlayerIndex = 0;
            for (Unit unit : factory.getWorldLayoutStrategy().getUnits().values())
                ((UnitImpl) unit).resetMoveCount();

            for (Position cityPos : factory.getWorldLayoutStrategy().getCityPositions()) {
                City c = getCityAt(cityPos);
                c.addMoney();
                produceUnit(cityPos);
            }

            //Advance world age
            factory.getWorldAgingStrategy().advanceAge();
            currentRound += 1;
            getWinner();
        }
        observer.turnEnds(getPlayerInTurn(), factory.getWorldAgingStrategy().getAge());
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {

    }

    public void changeProductionInCityAt(Position p, String unitType) {
        factory.getUnitProductionStrategy().changeProduction(unitType, getCityAt(p));
    }

    public void performUnitActionAt(Position p) {
        factory.getUnitActionStrategy().performUnitActionAt(p, this, factory.getWorldLayoutStrategy());
        observer.worldChangedAt(p);
    }

    public Collection<City> getCities() {
        return factory.getWorldLayoutStrategy().getCities();
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getBLUESuccessfulAttacks() {
        return BLUESA;
    }

    public int getREDSuccessfulAttacks() {
        return REDSA;
    }

    public void setBlueAttacks(int x){
        BLUESA=x;
    }

    public void setRedAttacks(int x){
        REDSA=x;
    }

    @Override
    public void addObserver(GameObserver observer) {
        this.observer = observer;
    }

    @Override
    public void setTileFocus(Position position) {
        observer.tileFocusChangedAt(position);
    }

}
