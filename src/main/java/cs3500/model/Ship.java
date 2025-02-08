package cs3500.model;

import java.util.List;

/**
 * Generates a ship
 */
public class Ship {
  private ShipType type;
  private List<Coord> coord;

  /**
   * Constructor for a ship
   *
   * @param type shipType
   * @param coord list of coordinates
   */
  public Ship(ShipType type, List<Coord> coord) {
    this.type = type;
    this.coord = coord;
  }

  /**
   * Gets a list of coordinates of a ship
   *
   * @return list of coordinates of the ship
   */
  public List<Coord> getCoord() {
    return coord;
  }
}
