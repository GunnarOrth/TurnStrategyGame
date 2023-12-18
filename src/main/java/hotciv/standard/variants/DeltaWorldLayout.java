package hotciv.standard.variants;

import hotciv.framework.*;
import hotciv.framework.strategies.WorldLayout;
import hotciv.standard.CityImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static hotciv.framework.GameConstants.WORLDSIZE;

public class DeltaWorldLayout implements WorldLayout {
    private HashMap<Position, Tile> layout = new HashMap<Position, Tile>();
    protected HashMap<Position, City> cities = new HashMap<Position, City>();
    private HashMap<Position, Unit> units = new HashMap<Position, Unit>();
    public DeltaWorldLayout() {
        layout.putAll(defineWorld());
        createCity(new Position(8, 12), Player.RED);
        createCity(new Position(4, 5), Player.BLUE);
        createUnit(GameConstants.ARCHER, new Position(3, 8), Player.RED);
        createUnit(GameConstants.LEGION, new Position(4, 4), Player.BLUE);
        createUnit(GameConstants.SETTLER, new Position(5, 5), Player.RED);
    }

    private HashMap<Position,Tile> defineWorld() {
        // Basically we use a 'data driven' approach - code the
        // layout in a simple semi-visual representation, and
        // convert it to the actual Game representation.
        String[] layout =
                new String[] {
                        "...ooMooooo.....",
                        "..ohhoooofffoo..",
                        ".oooooMooo...oo.",
                        ".ooMMMoooo..oooo",
                        "...ofooohhoooo..",
                        ".ofoofooooohhoo.",
                        "...ooo..........",
                        ".ooooo.ooohooM..",
                        ".ooooo.oohooof..",
                        "offfoooo.offoooo",
                        "oooooooo...ooooo",
                        ".ooMMMoooo......",
                        "..ooooooffoooo..",
                        "....ooooooooo...",
                        "..ooohhoo.......",
                        ".....ooooooooo..",
                };
        // Conversion...
        HashMap<Position,Tile> theWorld = new HashMap<Position,Tile>();
        String line;
        for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
            line = layout[r];
            for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
                char tileChar = line.charAt(c);
                String type = "error";
                if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
                if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
                if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
                if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
                if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
                Position p = new Position(r,c);
                createTile(type, p);
            }
        }
        return theWorld;
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
