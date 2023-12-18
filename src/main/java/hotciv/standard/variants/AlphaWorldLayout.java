package hotciv.standard.variants;

import hotciv.framework.*;
import hotciv.framework.strategies.WorldLayout;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import static hotciv.framework.GameConstants.WORLDSIZE;

public class AlphaWorldLayout implements WorldLayout {
    private HashMap<Position, Tile> layout = new HashMap<Position, Tile>();
    protected HashMap<Position, City> cities = new HashMap<Position, City>();
    private HashMap<Position, Unit> units = new HashMap<Position, Unit>();
    public AlphaWorldLayout() {
        for (int i = 0; i < WORLDSIZE; i++) {
            for (int j = 0; j < WORLDSIZE; j++) {
                createTile(GameConstants.PLAINS, new Position(i, j));
            }
        }
        createTile(GameConstants.OCEANS, new Position(1, 0));
        createTile(GameConstants.HILLS, new Position(0, 1));
        createTile(GameConstants.MOUNTAINS, new Position(2, 2));
        createCity(new Position(1, 1), Player.RED);
        createCity(new Position(4, 1), Player.BLUE);
        createUnit(GameConstants.ARCHER, new Position(2, 0), Player.RED);
        createUnit(GameConstants.LEGION, new Position(3, 2), Player.BLUE);
        createUnit(GameConstants.SETTLER, new Position(4, 3), Player.RED);
    }

    public Tile getTileAt(Position p) {
        return layout.get(p);
    }

    public void createTile(String tileType, Position tilePosition) {
        layout.put(tilePosition, new TileImpl(tileType));
    }

    public void createCity(Position cityPosition, Player player) {
        cities.put(cityPosition, new CityImpl(player));
    }
    public void deleteCity(Position p){cities.remove(p);}
    public City getCityAt(Position p) {
        return cities.get(p);
    }

    public Collection<City> getCities() {
        return cities.values();
    }

    public Set<Position> getCityPositions() {
        return cities.keySet();
    }

    public void setTile(Position p, String tileType){layout.get(p).setTypeString(tileType);}

    public HashMap<Position, Unit> getUnits() {
        return units;
    }

    public void createUnit(String unitType, Position unitPosition, Player player) {
        units.put(unitPosition, new UnitImpl(unitType, player));
    }


}
