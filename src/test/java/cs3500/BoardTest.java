package cs3500;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.Board;
import cs3500.model.Coord;
import cs3500.model.GameResult;
import cs3500.model.ShipType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class BoardTest {
  private Board board;

  @BeforeEach
  public void setUp() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.SUBMARINE, 2);
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.BATTLESHIP, 1);
    specifications.put(ShipType.CARRIER, 1);
    board = new Board(10, 10);
  }

  @Test
  public void testPlaceShips() {

    List<Coord> expectedCoords = new ArrayList<>();
    expectedCoords.add(new Coord(0, 5));
    expectedCoords.add(new Coord(0, 6));
    expectedCoords.add(new Coord(0, 7));

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.SUBMARINE, 1);
    board = new Board(10, 10);
    board.placeShips(specifications);

    List<Coord> actualCoords = board.getBoats().get(0).getCoord();
    assertEquals(3, actualCoords.size());
    assertEquals(expectedCoords, actualCoords);

  }

  @Test
  public void testPlaceShips2() {

    List<Coord> expectedCoords = new ArrayList<>();
    expectedCoords.add(new Coord(0, 4));
    expectedCoords.add(new Coord(1, 4));
    expectedCoords.add(new Coord(2, 4));
    expectedCoords.add(new Coord(3, 4));
    expectedCoords.add(new Coord(4, 4));
    expectedCoords.add(new Coord(5, 4));


    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 1);
    board = new Board(10, 10);
    board.placeShips(specifications);

    List<Coord> actualCoords = board.getBoats().get(0).getCoord();

    assertEquals(6, actualCoords.size());
    assertEquals(expectedCoords, actualCoords);
  }

  @Test
  public void testPlaceShips3() {

    List<Coord> expectedCoords = new ArrayList<>();
    expectedCoords.add(new Coord(0, 4));
    expectedCoords.add(new Coord(1, 4));
    expectedCoords.add(new Coord(2, 4));
    expectedCoords.add(new Coord(3, 4));
    expectedCoords.add(new Coord(4, 4));
    expectedCoords.add(new Coord(5, 4));


    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.CARRIER, 5);
    board = new Board(10, 10);
    board.placeShips(specifications);

    List<Coord> actualCoords = board.getBoats().get(0).getCoord();

    assertEquals(6, actualCoords.size());
    assertEquals(expectedCoords, actualCoords);
  }

  @Test
  public void testReportDamage() {
    // Arrange
    List<Coord> opponentShotsOnBoard = new ArrayList<>();
    opponentShotsOnBoard.add(new Coord(0, 5));
    opponentShotsOnBoard.add(new Coord(0, 6));
    opponentShotsOnBoard.add(new Coord(0, 7));
    opponentShotsOnBoard.add(new Coord(1, 6));

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.SUBMARINE, 1);
    board = new Board(10, 10);
    board.placeShips(specifications);

    List<Coord> hit = board.reportDamage(opponentShotsOnBoard);

    Coord coord = new Coord(0, 5);
    Coord coord1 = new Coord(0, 6);
    assertEquals(3, hit.size());
    assertEquals(coord, hit.get(0));
    assertEquals(coord1, hit.get(1));

    Coord coord2 = new Coord(1, 6);
    assertEquals(false, hit.contains(coord2));
    assertEquals(0, board.getBoatCount());
    assertEquals(GameResult.LOSE, board.getGameResult());
  }

  @Test
  public void testReportDamageContinue() {
    List<Coord> opponentShotsOnBoard = new ArrayList<>();
    opponentShotsOnBoard.add(new Coord(0, 5));
    opponentShotsOnBoard.add(new Coord(0, 6));
    opponentShotsOnBoard.add(new Coord(0, 7));
    opponentShotsOnBoard.add(new Coord(1, 6));

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.SUBMARINE, 2);
    board = new Board(10, 10);

    board.placeShips(specifications);

    List<Coord> hit = board.reportDamage(opponentShotsOnBoard);

    Coord coord = new Coord(0, 5);
    Coord coord1 = new Coord(0, 6);


    assertEquals(3, hit.size());
    assertEquals(coord, hit.get(0));
    assertEquals(coord1, hit.get(1));

    Coord coord2 = new Coord(1, 6);
    assertEquals(false, hit.contains(coord2));
    assertEquals(1, board.getBoatCount());
    assertEquals(GameResult.CONTINUE, board.getGameResult());

  }

  @Test
  public void testTakeShots() {

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 2);
    board.placeShips(specifications);


    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(2, 4));
    opponentShots.add(new Coord(7, 4));

    board.reportDamage(opponentShots);


    int boatCount = 5;
    List<Coord> result = board.takeShots(boatCount);

    Coord expectedCoord = new Coord(3, 4);

    assertEquals(boatCount, result.size());
    assertEquals(expectedCoord, result.get(0));

    Coord expectedCoord1 = new Coord(2, 5);
    Coord expectedCoord2 = new Coord(2, 3);
    assertEquals(expectedCoord1, result.get(1));
    assertEquals(expectedCoord2, result.get(2));

    Coord expectedCoord3 = new Coord(8, 4);
    Coord expectedCoord4 = new Coord(6, 4);
    assertEquals(expectedCoord3, result.get(3));
    assertEquals(expectedCoord4, result.get(4));

  }

  @Test
  public void testTakeShots2() {

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 2);
    board.placeShips(specifications);


    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(2, 4));
    opponentShots.add(new Coord(7, 4));

    board.reportDamage(opponentShots);


    int boatCount = 4;
    List<Coord> result = board.takeShots(boatCount);

    Coord expectedCoord = new Coord(3, 4);
    Coord expectedCoord1 = new Coord(2, 5);

    assertEquals(boatCount, result.size());
    assertEquals(expectedCoord, result.get(0));
    assertEquals(expectedCoord1, result.get(1));

    Coord expectedCoord2 = new Coord(2, 3);
    Coord expectedCoord3 = new Coord(8, 4);
    assertEquals(expectedCoord2, result.get(2));
    assertEquals(expectedCoord3, result.get(3));

  }

  @Test
  public void testTakeShots3() {

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 2);
    board.placeShips(specifications);


    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(2, 4));
    opponentShots.add(new Coord(7, 4));

    board.reportDamage(opponentShots);

    int boatCount = 3;
    List<Coord> result = board.takeShots(boatCount);

    Coord expectedCoord = new Coord(3, 4);
    assertEquals(boatCount, result.size());
    assertEquals(expectedCoord, result.get(0));

    Coord expectedCoord1 = new Coord(2, 5);
    Coord expectedCoord2 = new Coord(2, 3);
    assertEquals(expectedCoord1, result.get(1));
    assertEquals(expectedCoord2, result.get(2));

  }

  @Test
  public void testTakeShots4() {

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 2);
    board.placeShips(specifications);


    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(2, 4));
    opponentShots.add(new Coord(7, 4));

    board.reportDamage(opponentShots);

    int boatCount = 1;
    List<Coord> result = board.takeShots(boatCount);

    Coord expectedCoord = new Coord(3, 4);
    assertEquals(boatCount, result.size());
    assertEquals(expectedCoord, result.get(0));

  }

  @Test
  public void testTakeShots5() {

    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.DESTROYER, 1);
    specifications.put(ShipType.SUBMARINE, 2);
    board.placeShips(specifications);


    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(new Coord(2, 4));
    opponentShots.add(new Coord(7, 4));

    board.reportDamage(opponentShots);

    int boatCount = 10;
    List<Coord> result = board.takeShots(boatCount);

    Coord expectedCoord = new Coord(3, 4);
    Coord expectedCoord1 = new Coord(2, 5);

    assertEquals(boatCount, result.size());
    assertEquals(expectedCoord, result.get(0));
    assertEquals(expectedCoord1, result.get(1));

    Coord expectedCoord2 = new Coord(2, 3);
    Coord expectedCoord3 = new Coord(8, 4);
    Coord expectedCoord4 = new Coord(6, 4);
    assertEquals(expectedCoord2, result.get(2));
    assertEquals(expectedCoord3, result.get(3));
    assertEquals(expectedCoord4, result.get(4));

  }


  @Test
  public void testSuccessfulHits() {
    Map<ShipType, Integer> specifications = new HashMap<>();
    specifications.put(ShipType.SUBMARINE, 1);
    board = new Board(10, 10);
    board.placeShips(specifications);

    Coord coord = new Coord(0, 5);
    Coord coord1 = new Coord(7, 4);


    List<Coord> opponentShots = new ArrayList<>();
    opponentShots.add(coord);
    opponentShots.add(coord1);

    board.reportDamage(opponentShots);

    List<Coord> opponentShotsHit = new ArrayList<>();
    opponentShotsHit.add(coord);

    board.successfulHits(opponentShots, opponentShotsHit);

    assertEquals("H", board.returnBoard()[5][0]);
    assertEquals("M", board.returnBoard()[4][7]);
  }


}

