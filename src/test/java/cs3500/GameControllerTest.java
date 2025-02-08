package cs3500;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.controller.GameController;
import cs3500.model.Board;
import cs3500.model.Coord;
import cs3500.model.GameResult;
import cs3500.model.Opponent;
import cs3500.model.PlayerImpl;
import cs3500.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test for the GameController class
 */
public class GameControllerTest {
  private GameController gameController;
  private final ByteArrayOutputStream output = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  /**
   * tests the setup method
   */
  @BeforeEach
  public void setup() {
    gameController = new GameController();
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.SUBMARINE, 1);
    gameController.player = new PlayerImpl("Name");
    gameController.opponent = new Opponent();
    gameController.player.myBoard = new Board(10, 10);
    gameController.opponent.myBoard = new Board(10, 10);
    gameController.player.setup(10, 10, specifications);
    gameController.opponent.setup(10, 10, specifications);
  }

  /**
   * Test the setupGame method
   */
  @Test
  public void testSetupGame() {
    Map<ShipType, Integer> shipSpecifications = new HashMap<>();
    shipSpecifications.put(ShipType.SUBMARINE, 1);

    // Define the expected coordinates
    List<Coord> expectedCoords = new ArrayList<>();
    expectedCoords.add(new Coord(0, 5));
    expectedCoords.add(new Coord(0, 6));
    expectedCoords.add(new Coord(0, 7));

    String playerName = "Player 1";
    int height = 10;
    int width = 10;
    gameController.setupGame(playerName, height, width, shipSpecifications);


    // Verify the setup behavior
    // assertEquals(1, gameController.size()); // Verify the number of ships created


    List<Coord> actualCoords = gameController.player.myBoard.getBoats().get(0).getCoord();
    assertEquals(3, actualCoords.size());
    assertEquals(expectedCoords, actualCoords);

  }

  /**
   * Test the takeShots method
   */
  @Test
  public void testTakeShots() {
    // Set up test data
    List<Coord> playerShots = new ArrayList<>();
    playerShots.add(new Coord(0, 0));
    playerShots.add(new Coord(1, 1));

    gameController.setShots(playerShots);

    // Call the method

    System.setOut(new PrintStream(output));

    // Call the method that prints to the console
    gameController.takeShots(playerShots);

    // Capture the printed output
    String printedOutput = output.toString();

    // Define the expected output
    String expectedOutput = "\n"
        + "Opponent Board Data: \n"
        + "M 0 0 0 0 0 0 0 0 0 \n"
        + "0 M 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "\n"
        + "\n"
        + "Your Board: \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "H 0 0 0 0 0 0 0 0 0 \n"
        + "S 0 0 0 0 0 0 0 0 0 \n"
        + "S 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "0 0 0 0 0 0 0 0 0 0 \n"
        + "\n"
        + "Player Shots That Hit\n"
        + "Player Shots That Missed\n"
        + "Coordinate: x = 0, y = 0\n"
        + "Coordinate: x = 1, y = 1\n"
        + "AI Shots That Hit\n"
        + "Coordinate: x = 0, y = 5\n";

    // Assert that the captured output matches the expected output
    //    assertEquals(expectedOutput, printedOutput);

    System.setOut(originalOut);

  }

  /**
   * test the getWinner method
   */
  @Test
  public void testGetWinner() {

    // Case 1: Draw
    gameController.player.myBoard.setGameResult(GameResult.LOSE);
    gameController.opponent.myBoard.setGameResult(GameResult.LOSE);
    int expectedWinner1 = 0;

    int winner1 = gameController.getWinner();
    assertEquals(expectedWinner1, winner1);

    // Case 2: Player loses
    gameController.player.myBoard.setGameResult(GameResult.LOSE);
    gameController.opponent.myBoard.setGameResult(GameResult.WIN);
    int expectedWinner2 = -1;

    int winner2 = gameController.getWinner();
    assertEquals(expectedWinner2, winner2);

    // Case 3: Player wins
    gameController.player.myBoard.setGameResult(GameResult.WIN);
    gameController.opponent.myBoard.setGameResult(GameResult.LOSE);
    int expectedWinner3 = 1;

    // Call the method
    int winner3 = gameController.getWinner();
    assertEquals(expectedWinner3, winner3);
    // Case 4: Game still ongoing
    gameController.player.myBoard.setGameResult(GameResult.CONTINUE);
    gameController.opponent.myBoard.setGameResult(GameResult.CONTINUE);
    int expectedWinner4 = 2;
    int winner4 = gameController.getWinner();
    assertEquals(expectedWinner4, winner4);
  }
}
