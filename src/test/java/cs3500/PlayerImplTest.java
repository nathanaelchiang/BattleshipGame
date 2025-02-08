package cs3500;



import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.Coord;
import cs3500.model.PlayerImpl;
import cs3500.model.Ship;
import cs3500.model.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests the PlayerImpl class
 */
public class PlayerImplTest {

  /**
   * Test the setup method
   */
  @Test
  public void testSetup() {
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
    PlayerImpl player = new PlayerImpl(playerName);
    List<Ship> ships = player.setup(height, width, shipSpecifications);

    // Verify the setup behavior
    assertEquals(1, ships.size()); // Verify the number of ships created


    List<Coord> actualCoords = player.myBoard.getBoats().get(0).getCoord();
    assertEquals(3, actualCoords.size());
    assertEquals(expectedCoords, actualCoords);

  }

  /**
   * Tests the successfulHits method
   */
  @Test
  public void testSuccessfulHits() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.SUBMARINE, 1);
    PlayerImpl player = new PlayerImpl("Player 1");
    int height = 10;
    int width = 10;
    Map<ShipType, Integer> shipSpecifications = new HashMap<>();
    shipSpecifications.put(ShipType.SUBMARINE, 1);
    List<Ship> ships = player.setup(height, width, shipSpecifications);

    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(0, 5));
    opponentShots.add(new Coord(7, 4));

    player.myBoard.reportDamage(opponentShots);

    List<Coord> opponentShotsHit = new ArrayList<>();
    opponentShotsHit.add(new Coord(0, 5));

    player.setShots(opponentShots);
    player.successfulHits(opponentShotsHit);

    assertEquals("H", player.myBoard.returnBoard()[5][0]);
    assertEquals("M", player.myBoard.returnBoard()[4][7]);
    assertEquals(opponentShots, player.takeShots());
  }
}
