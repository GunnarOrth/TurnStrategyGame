package hotciv.visual;

import hotciv.framework.City;
import hotciv.framework.Player;

import static hotciv.framework.GameConstants.*;

// A test stub implementation just to force some graphical updates.
public class CityStub implements City {
    boolean redOwns = true;

    // a testing method just to make some
    // state changes
    public void makeAChange() {
        redOwns = !redOwns;
    }

    public Player getOwner() {
        return (redOwns ? Player.RED : Player.BLUE);
    }

    public int getSize() {
        return (redOwns ? 4 : 9);
    }

    public int getTreasury() {
        return 0;
    }

    public String getProduction() {
        return redOwns ? ARCHER : SETTLER;
    }

    public String getWorkforceFocus() {
        return redOwns ? foodFocus : productionFocus;
    }

    @Override
    public void subMoney(int x) {

    }

    @Override
    public void addMoney() {

    }

    @Override
    public void setPlayer(Player player) {

    }

    @Override
    public void setProduction(String s) {

    }

    @Override
    public int getPopulation() {
        return 0;
    }

    @Override
    public void setPopulation(int x) {

    }
}
