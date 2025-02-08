package cs3500.viewer;

/**
 * MockView used for testing
 */
public class MockView implements ViewerInterface {
  private StringBuilder log;

  public MockView() {
    this.log = new StringBuilder();
  }

  /**
   * Gets the log
   *
   * @return the log as a string
   */
  public String getLog() {
    return log.toString();
  }

  @Override
  public void getDimension() {
    log.append("Get Dimension called");
  }

  @Override
  public void fleetSelection() {
    log.append("Fleet selection called.");
  }

  @Override
  public void enterShots() {
    log.append("Enter shots called");
  }

  @Override
  public void takeShots() {
    log.append("Take shots called");
  }

  @Override
  public int displayWinner() {
    log.append("Display winner called.");
    return 0;
  }
}
