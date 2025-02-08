package cs3500.model;

import java.util.List;
import java.util.Map;

/**
 * Class for a human playerbase
 */
public class PlayerImpl extends PlayerBase {

  /**
   * Initializes a playerImpl
   *
   * @param name player name
   */
  public PlayerImpl(String name) {
    this.name = this.name;
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
    System.out.println();
    System.out.println("Opponent Board Data: ");
    // print opponent's board
    opponentBoard.printBoard();
    System.out.println();
    System.out.println("Your Board: ");
    // print my board
    myBoard.printBoard();
    return myBoard.getBoats();
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public List<Coord> takeShots() {
    return shots;
  }

  /**
   * sets the player shots
   *
   * @param shots list of coordinates for shots
   */
  public void setShots(List<Coord> shots) {
    this.shots = shots;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship. Also prints the board.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    opponentBoard.successfulHits(shots, shotsThatHitOpponentShips);
    System.out.println();
    System.out.println("Opponent Board Data: ");
    // print opponent's board
    opponentBoard.printBoard();
    System.out.println();
    System.out.println("Your Board: ");
    // print my board
    myBoard.printBoard();
  }
}