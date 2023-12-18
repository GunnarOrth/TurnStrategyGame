package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;


public class CityImpl implements City {
    private int money;
    private int population;
    private String cityProduction;
    private Player player;
    private String workForceFocus;
    public CityImpl(Player player)
    {
        this.player = player;
        cityProduction = null;
        money = 0;
        population = 1;
        workForceFocus = GameConstants.foodFocus;
    }
    public Player getOwner() {
        return player;
    }

    public int getSize() {
        return population;
    }

    public int getTreasury() {
        return money;
    }

    public String getProduction() {
        return cityProduction;
    }

    public String getWorkforceFocus() {
        return workForceFocus;
    }

    public void addMoney()
    {
        money = money + GameConstants.productionGrowth;
    }

    public void subMoney(int x)
    {
        money = money - x;
        //walter;
    }

    public void setPlayer(Player pl) {
        player = pl;
    }

    public void setProduction(String x)
    {
        cityProduction = x;
    }

    public int getPopulation(){
        return population;
    }

    public void setPopulation(int x){
        population = x;
    }
}
