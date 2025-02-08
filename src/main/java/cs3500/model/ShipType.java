package cs3500.model;

/**
 * Ship type with the value being its given length
 */
public enum ShipType {
  /**
    * Carrier with size 6
    */
  CARRIER(6),
  /**
   * Battleship with size 5
   */
  BATTLESHIP(5),

  /**
   * Destroyer with size 4
   */
  DESTROYER(4),

  /**
   * Submarine with size 3
   */
  SUBMARINE(3);

  private int value;

  /**
   * Constructor for shipType
   *
   * @param value value of shipType
   */
  ShipType(int value) {
    this.value = value;
  }

  /**
   * Gets the value of the ship type
   *
   * @return the value of the ship type
   */
  public int getValue() {
    return value;
  }

}
