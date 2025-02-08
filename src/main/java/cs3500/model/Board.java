package cs3500.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Class that simulates a board for battleship
 */
public class Board {
  private int height;
  private int width;
  private String[][] grid;
  private int boatCount;
  private List<Ship> boats;
  private GameResult result;
  private List<Coord> boatCoords;
  Random random = new Random(24);

  /**
   * Initializes a board with given height and width
   *
   * @param height given height
   * @param width given width
   */
  public Board(int height, int width) {
    this.height = height;
    this.width = width;
    result = GameResult.CONTINUE;
    boats = new ArrayList<>();
    boatCoords = new ArrayList<>();
    setup();
  }

  /**
   * Setups the board with given height and width
   *
   * @return The board using a String 2D array
   */
  private String[][] setup() {
    grid = new String[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        grid[i][j] = "0";
      }
    }
    return grid;
  }

  /**
   * Places the ships on the board with the given specifications
   *
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  public List<Ship> placeShips(Map<ShipType, Integer> specifications) {
    boatCount = 0;
    for (Map.Entry<ShipType, Integer> entry : specifications.entrySet()) {
      ShipType shipType = entry.getKey();
      int shipCount = entry.getValue();
      boatCount = boatCount + entry.getValue();

      while (shipCount > 0) {
        if (placeOneShip(shipType)) {
          shipCount--;
        }
      }
    }
    getBoatCount();
    return boats;
  }

  /**
   * Places one ship on the board. Returns true if one is placed.
   *
   * @param shipType type of ship
   * @return returns true if a ship is placed
   */
  private boolean placeOneShip(ShipType shipType) {
    int row = random.nextInt(height);
    int column = random.nextInt(width);
    int orientation = random.nextInt(2);

    boolean isAllEmpty;
    if (grid[row][column].equals("0")) {
      if (orientation == 0) {
        if (width - column >= shipType.getValue()) {
          isAllEmpty = checkAllZeros(shipType, row, column, true);
        } else {
          isAllEmpty = false;
        }
      } else {
        if (height - row >= shipType.getValue()) {
          isAllEmpty = checkAllZeros(shipType, row, column, false);
        } else {
          isAllEmpty = false;
        }
      }
    } else {
      isAllEmpty = false;
    }
    if (isAllEmpty) {
      putShip(shipType, row, column, orientation);
    }
    return isAllEmpty;
  }

  /**
   * Checks if a certain amount of space on the board is empty
   *
   * @param shipType type of ship
   * @param row given row
   * @param column given column
   * @param orientation given orientation, 0 for horizontal and 1 for vertical
   * @return true if empty
   */
  private boolean checkAllZeros(ShipType shipType, int row, int column, boolean orientation) {
    boolean allEmpty = true;
    if (orientation) {
      for (int i = 0; i < shipType.getValue(); i++) {
        if (!grid[row][column + i].equals("0")) {
          allEmpty = false;
        }
      }
    } else {
      for (int i = 0; i < shipType.getValue(); i++) {
        if (!grid[row + i][column].equals("0")) {
          allEmpty = false;
        }
      }
    }
    return allEmpty;
  }

  /**
   * Actually places the ship
   *
   * @param shipType type of ship
   * @param row given row
   * @param column given column
   * @param orientation given orientation, 0 for horizontal and 1 for vertical
   */
  private void putShip(ShipType shipType, int row, int column, int orientation) {
    List<Coord> coords = new ArrayList<>();
    if (orientation == 0) {
      for (int i = 0; i < shipType.getValue(); i++) {
        grid[row][column + i] = shipType.name().substring(0, 1);
        coords.add(new Coord(column + i, row));
      }
    } else {
      for (int i = 0; i < shipType.getValue(); i++) {
        grid[row + i][column] = shipType.name().substring(0, 1);
        coords.add(new Coord(column, row + i));
      }
    }
    boats.add(new Ship(shipType, coords));
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
    List<Coord> hit = new ArrayList<>();
    for (Coord hitCoord : opponentShotsOnBoard) {
      if (!(grid[hitCoord.getY()][hitCoord.getX()].equals("0")
          || grid[hitCoord.getY()][hitCoord.getX()].equals("M"))) {
        grid[hitCoord.getY()][hitCoord.getX()] = "H";
        hit.add(hitCoord);
        for (Ship ship : boats) {
          for (Coord shipCoord : ship.getCoord()) {
            if (shipCoord.equals(hitCoord)) {
              boolean sunk = true;
              for (int i = 0; i < ship.getCoord().size(); i++) {
                if (!(grid[ship.getCoord().get(i).getY()][ship.getCoord().get(i).getX()].equals(
                    "H"))) {
                  sunk = false;
                  break;
                }
              }
              if (sunk) {
                boatCount--;
                if (boatCount == 0) {
                  result = GameResult.LOSE;
                }
                break;
              }
            }
          }
        }
      } else {
        grid[hitCoord.getY()][hitCoord.getX()] = "M";
      }
    }
    return hit;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsTook the list of shots that the user took
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsTook, List<Coord> shotsThatHitOpponentShips) {
    for (Coord shotCoord : shotsTook) {
      if (shotsThatHitOpponentShips.contains(shotCoord)) {
        grid[shotCoord.getY()][shotCoord.getX()] = "H";
      } else {
        grid[shotCoord.getY()][shotCoord.getX()] = "M";
      }
    }
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk. Uses an algorithm to
   * figure out which shots to take.
   *
   * @param boatCount boat count
   * @return the locations of shots on the opponent's board
   */
  public List<Coord> takeShots(int boatCount) {
    List<Coord> takeShotsCoords = new ArrayList<>();
    int size = Math.min(boatCount, getCellCount());

    outerLoop:
    for (int i = 0; i < height * width; i++) {
      for (int x = 0; x < height; x++) {
        for (int y = 0; y < width; y++) {
          if (grid[x][y].equals("H")) {
            if (isValidCoordinates(y + 1, x, takeShotsCoords)) {
              takeShotsCoords.add(new Coord(y + 1, x));
              if (takeShotsCoords.size() == size) {
                break outerLoop;
              }
            }
            if (isValidCoordinates(y - 1, x, takeShotsCoords)) {
              takeShotsCoords.add(new Coord(y - 1, x));
              if (takeShotsCoords.size() == size) {
                break outerLoop;
              }
            }
            if (isValidCoordinates(y, x + 1, takeShotsCoords)) {
              takeShotsCoords.add(new Coord(y, x + 1));
              if (takeShotsCoords.size() == size) {
                break outerLoop;
              }
            }
            if (isValidCoordinates(y, x - 1, takeShotsCoords)) {
              takeShotsCoords.add(new Coord(y, x - 1));
              if (takeShotsCoords.size() == size) {
                break outerLoop;
              }
            }
          }
        }
      }
      int x = random.nextInt(height);
      int y = random.nextInt(width);
      if (grid[x][y].equals("0")) {
        Coord newCoord = new Coord(y, x);
        if (!takeShotsCoords.contains(newCoord)) {
          takeShotsCoords.add(new Coord(y, x));
          if (takeShotsCoords.size() == boatCount) {
            break;
          }
        }
      }
    }


    return takeShotsCoords;
  }


  /**
   * Checks if the given coordinates are valid
   *
   * @param x x-value
   * @param y y-value
   * @param list list of coordinates
   * @return true if coordinates are valid
   */
  private boolean isValidCoordinates(int x, int y, List<Coord> list) {
    Coord newCoord = new Coord(x, y);
    return x >= 0 && x < width && y >= 0 && y < height
        && grid[y][x].equals("0") && (!list.contains(newCoord));
  }

  /**
   * Gets the total cell count of the board
   *
   * @return total cell count
   */
  public int getCellCount() {
    int emptyCellCount = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (grid[i][j].equals("0")) {
          emptyCellCount++;
        }
      }
    }
    return emptyCellCount;
  }

  /**
   * Prints the board
   */
  public void printBoard() {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        System.out.print(grid[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  /**
   * Gets the list of ships
   *
   * @return list of ships
   */
  public List<Ship> getBoats() {
    return boats;
  }


  /**
   * Gets the current boat count
   *
   * @return boat count
   */
  public int getBoatCount() {
    return boatCount;
  }

  /**
   * Gets the current GameResult
   *
   * @return GameResult
   */
  public GameResult getGameResult() {
    return result;
  }

  /**
   * Sets the GameResult
   *
   * @param gameResult GameResult
   * @return the set GameResult
   */
  public GameResult setGameResult(GameResult gameResult) {
    result = gameResult;
    return result;
  }

  /**
   * Returns the grid itself
   *
   * @return the grid
   */
  public String[][] returnBoard() {
    return grid;
  }
}
