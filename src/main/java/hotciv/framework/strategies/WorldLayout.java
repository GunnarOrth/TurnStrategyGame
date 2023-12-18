package hotciv.framework.strategies;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.UnitImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public interface WorldLayout {
    /**
     * return a specific tile.
     * Precondition: Position p is a valid position in the world.
     *
     * @param p the position in the world that must be returned.
     * @return the tile at position p.
     */
    public Tile getTileAt(Position p);

    /**
     * Create a tile in the world.
     * Precondition: Position p is a valid position in the world.
     *
     * @param tileType string that defines type of tile.
     * @param tilePosition the position in the world that must be returned.
     */
    public void createTile(String tileType, Position tilePosition);

    /** return the city at position 'p' in the world.
     * Precondition: Position p is a valid position in the world.
     * @param p the position in the world.
     * @return the city at this position or null if no city here.
     */
    public City getCityAt(Position p );

    /** create city at position cityPosition
     * Precondition: Position cityPosition is a valid position in the world.
     * @param cityPosition the position in the world.
     * @param player is the player that owns city.
     */
    public void createCity(Position cityPosition, Player player);
    public void deleteCity(Position p);
    public Collection<City> getCities();

    public Set<Position> getCityPositions();

    public void setTile(Position p, String tileType);

    public HashMap<Position, Unit> getUnits();
    public void createUnit(String unitType, Position unitPosition, Player player);
}
