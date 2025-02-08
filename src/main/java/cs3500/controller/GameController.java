package cs3500.controller;

import cs3500.model.Coord;
import cs3500.model.GameResult;
import cs3500.model.Opponent;
import cs3500.model.PlayerImpl;
import cs3500.model.ShipType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller for the game
 */
public class GameController {
  public PlayerImpl player;
  public Opponent opponent;

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param playerName     name of the player
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   */
  public void setupGame(String playerName, int height, int width,
                        Map<ShipType, Integer> specifications) {
    // initiate two players
    player = new PlayerImpl(playerName);
    opponent = new Opponent();
    // call setup method to setup board
    player.setup(height, width, specifications);
    opponent.setup(height, width, specifications);

  }

  /**
   * Sets the player shots
   *
   * @param playerShots list of coordinates that the player wants to shoot
   */
  public void setShots(List<Coord> playerShots) {
    player.setShots(playerShots);
  }

  /**
   * Actually taking the shots
   *
   * @param playerShots list of coordinates that the player wants to shoot
   */
  public void takeShots(List<Coord> playerShots) {
    List<Coord> shots = opponent.takeShots();
    List<Coord> opponentHits = player.reportDamage(shots);
    opponent.successfulHits(opponentHits);
    List<Coord> playerHits = opponent.reportDamage(playerShots);
    player.successfulHits(playerHits);
    List<Coord> playerMissed = new ArrayList<>();
    for (Coord coord : playerShots) {
      if (!playerHits.contains(coord)) {
        playerMissed.add(coord);
      }
    }
    printListOfCoord(playerHits, "Player Shots That Hit");
    printListOfCoord(playerMissed, "Player Shots That Missed");
    printListOfCoord(opponentHits, "AI Shots That Hit");
  }

  /**
   * Gets the boat count
   *
   * @return the boat count
   */
  public int getBoatCount() {
    return player.getBoatCount();
  }

  /**
   * Gets the winner of the game (0 for draw, -1 for loss, 1 for win, 2 for game has not ended)
   *
   * @return integer
   */
  public int getWinner() {
    if ((player.getGameResult() == GameResult.LOSE)
        && (opponent.getGameResult() == GameResult.LOSE)) {
      System.out.println("You drew!");
      return 0;
    } else if (player.getGameResult() == GameResult.LOSE) {
      System.out.println("You lost!");
      return -1;
    } else if (opponent.getGameResult() == GameResult.LOSE) {
      System.out.println("You won!");
      return 1;
    } else {
      return 2;
    }
  }

  /**
   * Print out list of coordinates to the console
   *
   * @param list list of coordinates
   * @param name name of the list
   */
  private void printListOfCoord(List<Coord> list, String name) {
    System.out.println(name);
    for (Coord coord : list) {
      System.out.println("Coordinate: " + coord);
    }
  }

}

