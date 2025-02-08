package cs3500.controller;

/**
 * Mock class for ProxyController
 */
public class MockProxyController {
  private StringBuilder log;

  public MockProxyController() {
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

  /**
   * Mock method for handleJoin
   */
  public void handleJoin() {
    log.append("handleJoin called");
  }
}
