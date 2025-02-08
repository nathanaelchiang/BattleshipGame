package cs3500.model;

import java.util.List;
import java.util.Map;

/**
 * Abstract class for a player base
 */
public abstract class PlayerBase implements Player {
  /**
   * Player name
   */
  protected String name;
  /**
   * My board
   */
  public Board myBoard;
  /**
   * The opponent's board
   */
  protected Board opponentBoard;
  /**
   * A list of coordinates for shots
   */
  protected List<Coord> shots;

  /**
   * Get the player's name.
   * NOTE: This may not be important to your implementation for PA03, but it will be later
   *
   * @return the player's name
   */
  public String name() {
    return name;
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
  public abstract List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications);


  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public abstract List<Coord> takeShots();

  /**
   * Gets the boat count
   *
   * @return boat count
   */
  public int getBoatCount() {
    return myBoard.getBoatCount();
  }

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    return myBoard.reportDamage(opponentShotsOnBoard);
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public abstract void successfulHits(List<Coord> shotsThatHitOpponentShips);

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public void endGame(GameResult result, String reason) {
  }

  /**
   * Gets the game result
   *
   * @return game result
   */
  public GameResult getGameResult() {
    return myBoard.getGameResult();
  }

}


