package cs3500.viewer;

import cs3500.controller.GameController;
import cs3500.model.Coord;
import cs3500.model.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Displays the game to the user
 */
public class ViewerConsole implements ViewerInterface {
  private String name;
  private int height;
  private int width;
  private int carrierCount;
  private int battleshipCount;
  private int destroyerCount;
  private int submarineCount;
  private GameController controller;
  List<Coord> list;

  Map<ShipType, Integer> specifications = new HashMap<>();

  public ViewerConsole() {
    controller = new GameController();
  }

  /**
   * Gets the dimension of the board from the user
   */
  public void getDimension() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Hello! Welcome to the OOD BattleSalvo Game!");
    System.out.println("What is your name?");
    name = scanner.nextLine();
    System.out.println("Please enter a valid height and width below:");
    boolean validDimensions = false;

    while (!validDimensions) {

      try {
        height = scanner.nextInt();
        width = scanner.nextInt();

        if (height < 6 || width < 6 || height > 15 || width > 15) {
          throw new InputMismatchException("Invalid input");
        }

        validDimensions = true;
      } catch (InputMismatchException e) {
        System.out.println("Uh Oh! You've entered invalid dimensions. "
            + "Please remember that the height and width of the game must be in the range (6, 15), "
            + "inclusive. Try again!");
        scanner.nextLine();
      }
    }
  }

  /**
   * Gets the fleet selected by the user
   */
  public void fleetSelection() {
    Scanner scanner = new Scanner(System.in);
    boolean validFleet = false;
    int smallerDimension = Math.min(height, width);
    System.out.println("Please enter your fleet in the order "
        + "[Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size " + smallerDimension
        + " and you must have one of each boat type.");

    while (!validFleet) {
      try {
        carrierCount = scanner.nextInt();
        battleshipCount = scanner.nextInt();
        destroyerCount = scanner.nextInt();
        submarineCount = scanner.nextInt();

        if (carrierCount + battleshipCount + destroyerCount + submarineCount > smallerDimension
            || carrierCount == 0 || battleshipCount == 0
            || destroyerCount == 0 || submarineCount == 0) {
          throw new InputMismatchException("Invalid input");
        }
        assignSpecifications();
        // controller setup game
        controller.setupGame(name, height, width, specifications);
        validFleet = true;
      } catch (InputMismatchException e) {
        System.out.println("Uh Oh! You've entered invalid fleet sizes.\n"
            + "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].\n"
            + "Remember, your fleet may not exceed size " + smallerDimension
            + " and you must have one of each boat type.");
        scanner.nextLine();
      }
    }
  }

  /**
   * Assigns specifications based on ship counts
   */
  private void assignSpecifications() {
    specifications.put(ShipType.CARRIER, carrierCount);
    specifications.put(ShipType.BATTLESHIP, battleshipCount);
    specifications.put(ShipType.DESTROYER, destroyerCount);
    specifications.put(ShipType.SUBMARINE, submarineCount);
  }

  /**
   * Gets the coordinates from the user
   */
  public void enterShots() {
    list = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Please take " + controller.getBoatCount()
        + " shots. The first number you enter will represent "
        + "the x coordinate and the second number will represent the y coordinate."
        + "Coordinates start at 0.");
    for (int i = 0; i < controller.getBoatCount(); i++) {
      boolean isValidShot = false;
      int x;
      int y;
      while (!isValidShot) {
        try {
          x = scanner.nextInt();
          y = scanner.nextInt();

          // Check if the coordinates are valid
          if (isValidCoordinates(x, y)) {
            list.add(new Coord(x, y));
            isValidShot = true;
          } else {
            throw new InputMismatchException("Invalid input");
          }
        } catch (InputMismatchException e) {
          System.out.println("Invalid coordinates. Please enter valid coordinates.");
          scanner.nextLine();
        }
      }
    }
    controller.setShots(list);
  }

  /**
   * Takes the shots from the list from enterShots
   */
  public void takeShots() {
    controller.takeShots(list);
  }

  /**
   * Checks if the coordinates are valid
   *
   * @param x x-value
   * @param y y-value
   * @return true if coordinates are valid
   */
  private boolean isValidCoordinates(int x, int y) {
    // Check if the coordinates are within the grid bounds
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  /**
   * displays a winner of the game has ended
   *
   * @return the winner
   */
  public int displayWinner() {
    return controller.getWinner();
  }

}


