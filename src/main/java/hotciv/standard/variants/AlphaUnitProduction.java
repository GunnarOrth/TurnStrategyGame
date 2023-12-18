package hotciv.standard.variants;

import hotciv.framework.City;
import hotciv.framework.strategies.UnitProduction;

import static hotciv.framework.GameConstants.*;

public class AlphaUnitProduction implements UnitProduction {
    public void changeProduction(String unitType, City c){
        if (c == null) return;
        if(unitType.equals(SETTLER) || unitType.equals(ARCHER) || unitType.equals(LEGION) ){
            c.setProduction(unitType);
        }
    }
}
