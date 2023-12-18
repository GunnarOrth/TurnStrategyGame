package hotciv.standard;

import hotciv.framework.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Transcript implements Game {
    Game game;
    boolean transcribe;
    ArrayList<String> transcript;

    public Transcript(Game game) {
        this.game = game;
        transcript = new ArrayList<String>();
        transcribe = false;
    }

    public Tile getTileAt(Position p) {
        return game.getTileAt(p);
    }

    public Unit getUnitAt(Position p) {
        return game.getUnitAt(p);
    }

    public City getCityAt(Position p) {
        return game.getCityAt(p);
    }

    public Player getPlayerInTurn() {
        return game.getPlayerInTurn();
    }

    public Player getWinner() {
        return game.getWinner();
    }

    public int getAge() {
        return game.getAge();
    }

    public boolean moveUnit(Position from, Position to) {
        boolean moved = game.moveUnit(from, to);
        if (transcribe && moved) {
            transcript.add(game.getPlayerInTurn().name() + " moves " + game.getUnitAt(to).getTypeString() + " from " + from + " to " + to + ".");
        }
        return moved;
    }

    public void deleteUnit(Position p) {
        game.deleteUnit(p);
    }

    public void createUnit(String unitType, Position unitPosition, Player player) {
        if (transcribe) {
            transcript.add(game.getPlayerInTurn().name() + " produces " + unitType + " at " + unitPosition + ".");
        }
        game.createUnit(unitType, unitPosition, player);
    }

    public void produceUnit(Position cityPosition) {
        game.produceUnit(cityPosition);
    }

    public void createCity(Position p, Player player) {
        game.createCity(p, player);
    }

    public void endOfTurn() {
        if (transcribe) {
            transcript.add(game.getPlayerInTurn().name() + " ends turn.");
        }
        game.endOfTurn();
    }

    public void changeWorkForceFocusInCityAt(Position p, String balance) {
        if (transcribe) {
            transcript.add(game.getPlayerInTurn().name() + " changes work force focus in city at " + p + " to " + balance + ".");
        }
        game.changeWorkForceFocusInCityAt(p, balance);
    }

    public void changeProductionInCityAt(Position p, String unitType) {
        if (transcribe) {
            transcript.add(game.getPlayerInTurn().name() + " changes production in city at " + p + " to " + unitType + ".");
        }
        game.changeProductionInCityAt(p, unitType);
    }

    public void performUnitActionAt(Position p) {
        if (transcribe) {
            transcript.add(game.getPlayerInTurn().name() + " has " + game.getUnitAt(p).getTypeString() + " perform action at " + p + ".");
        }
        game.performUnitActionAt(p);
    }

    public Collection<City> getCities() {
        return game.getCities();
    }

    public int getCurrentRound() {
        return game.getCurrentRound();
    }

    public int getBLUESuccessfulAttacks() {
        return game.getBLUESuccessfulAttacks();
    }

    public int getREDSuccessfulAttacks() {
        return game.getREDSuccessfulAttacks();
    }

    public void setBlueAttacks(int x) {
        game.setBlueAttacks(x);
    }

    public void setRedAttacks(int x) {
        game.setRedAttacks(x);
    }

    @Override
    public void addObserver(GameObserver observer) {

    }

    @Override
    public void setTileFocus(Position position) {

    }

    public void transcribe(boolean transcribe) {
        this.transcribe = transcribe;
    }

    public ArrayList<String> getTranscript() {
        return transcript;
    }
}
