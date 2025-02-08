package cs3500.model;

import java.util.List;
import java.util.Map;

/**
 * Opponent player base
 */
public class Opponent extends PlayerBase {

  /**
   * Initializes an opponent
   */
  public Opponent() {
    name = "AI";
  }

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    // setup board
    myBoard = new Board(height, width);
    opponentBoard = new Board(height, width);
    myBoard.placeShips(specifications);
    return myBoard.getBoats();
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public List<Coord> takeShots() {
    shots = opponentBoard.takeShots(myBoard.getBoatCount());
    return shots;
  }


  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    opponentBoard.successfulHits(shots, shotsThatHitOpponentShips);
  }
}