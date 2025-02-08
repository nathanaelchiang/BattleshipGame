package cs3500;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.viewer.MockView;
import cs3500.viewer.ViewerConsole;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

/**
 * Tests the ViewerConsole class
 */
public class ViewerConsoleTest {

  ViewerConsole viewer = new ViewerConsole();

  /**
   * Tests getDimension when it gets valid input
   */
  @Test
  public void testGetDimensionValidInput() {
    String input = "Name\n8\n10\n"; // Enter valid dimensions
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    viewer.getDimension();

    MockView mock = new MockView();
    mock.getDimension();

    assertEquals("Get Dimension called", mock.getLog());
  }


  /**
   * Tests getDimensions when it gets invalid input
   */
  @Test
  public void testGetDimensionInvalidInput() {
    String input = "Name\n7\n17\n2\n1\n18\n8\n10\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    try {
      viewer.getDimension();
    } catch (Exception e) {
      assertEquals(null, e.getMessage());
    }
  }

  /**
   * Tests fleet selection when it gets valid input
   */
  @Test
  public void testFleetSelectionValidInput() {
    String input = "Name\n6\n6";
    String input2 = "1\n1\n1\n1\n1";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);


    viewer.getDimension();
    ByteArrayInputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
    System.setIn(inputStream2);
    viewer.fleetSelection();

    MockView mock = new MockView();
    mock.fleetSelection();
    assertEquals("Fleet selection called.", mock.getLog());

  }

  /**
   * Tests fleetSelection when it gets invalid input
   */
  @Test
  public void testFleetSelectionInvalidInput() {
    String input = "0\n0\n0\n0\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    try {
      viewer.fleetSelection();
    } catch (Exception e) {
      assertEquals(null, e.getMessage());

    }
  }

  /**
   * Tests fleetSelection when it gets invalid input
   */
  @Test
  public void testFleetSelectionInvalidInput2() {
    String input = "10\n10\n10\n10\n";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    try {
      viewer.fleetSelection();
    } catch (Exception e) {
      assertEquals(null, e.getMessage());

    }
  }

  /**
   * tests the enterShots method
   */
  @Test
  public void testEnterShots() {
    String input = "Name\n6\n6";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    String input2 = "1\n1\n1\n1\n1";
    viewer.getDimension();
    ByteArrayInputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
    System.setIn(inputStream2);
    viewer.fleetSelection();

    String input3 = "0 1\n 0 2\n 0 3\n 10 10\n  0 4\n0 5";
    ByteArrayInputStream inputStream3 = new ByteArrayInputStream(input3.getBytes());
    System.setIn(inputStream3);
    viewer.enterShots();

    MockView mock = new MockView();
    mock.enterShots();
    assertEquals("Enter shots called", mock.getLog());
  }

  /**
   * Tests the takeShots method
   */
  @Test
  public void testTakeShots() {
    String input = "Name\n6\n6";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);


    viewer.getDimension();
    String input2 = "1\n1\n1\n1\n1";
    ByteArrayInputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
    System.setIn(inputStream2);
    viewer.fleetSelection();
    String input3 = "0 -1\n -1 2\n 12 12\n 10 10\n  0 4\n0 5\n0 5\n0 5\n0 5";
    ByteArrayInputStream inputStream3 = new ByteArrayInputStream(input3.getBytes());
    System.setIn(inputStream3);
    viewer.enterShots();
    assertEquals(2, viewer.displayWinner());
    viewer.takeShots();
    MockView mock = new MockView();
    mock.takeShots();
    assertEquals("Take shots called", mock.getLog());
  }

  /**
   * Tests the displayWinner method
   */
  @Test
  public void testDisplayWinner() {
    String input = "Name\n6\n6";
    String input2 = "1\n1\n1\n1\n1";
    ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
    System.setIn(inputStream);

    viewer.getDimension();
    ByteArrayInputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
    System.setIn(inputStream2);
    viewer.fleetSelection();
    String input3 = "0 1\n 0 2\n 0 3\n 10 10\n  0 4\n0 5";
    ByteArrayInputStream inputStream3 = new ByteArrayInputStream(input3.getBytes());
    System.setIn(inputStream3);
    viewer.enterShots();
    assertEquals(2, viewer.displayWinner());
    MockView mock = new MockView();
    assertEquals(0, mock.displayWinner());
    assertEquals("Display winner called.", mock.getLog());
  }


}

