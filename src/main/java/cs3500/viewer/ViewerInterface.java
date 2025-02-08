package cs3500.viewer;

/**
 * Interface for the viewer class
 */
public interface ViewerInterface {

  /**
   * Gets the dimension of the board from the user
   */
  void getDimension();

  /**
   * Gets the fleet selected by the user
   */
  void fleetSelection();

  /**
   * Gets the coordinates from the user
   */
  void enterShots();

  /**
   * Takes the shots from the list from enterShots
   */
  void takeShots();

  /**
   * displays a winner of the game has ended
   *
   * @return the winner
   */
  int displayWinner();
}
