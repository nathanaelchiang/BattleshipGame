package cs3500;

import cs3500.controller.ProxyController;
import cs3500.model.Opponent;
import cs3500.viewer.ViewerConsole;
import java.io.IOException;
import java.net.Socket;

/**
 * Driver class
 */
public class Driver {
  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    ProxyController proxyDealer = new ProxyController(server, new Opponent());
    proxyDealer.run();
  }

  /**
   * main function
   *
   * @param args run config arguments
   */
  public static void main(String[] args) {
    try {
      if (args.length == 0) {
        System.out.println("Hello from Battle Salvo - PA03 Template Repo");
        ViewerConsole test = new ViewerConsole();
        test.getDimension();
        test.fleetSelection();
        while (test.displayWinner() == 2) {
          test.enterShots();
          test.takeShots();
        }
      } else if (args.length == 2) {
        String host = args[0].trim();
        int port = Integer.parseInt(args[1]);
        runClient(host, port);
      } else {
        throw new IllegalArgumentException("Invalid number of arguments. (0 = PA03) (2 = PA04)");
      }
    } catch (IOException | IllegalStateException e) {
      System.out.println("Unable to connect to the server.");
    } catch (NumberFormatException e) {
      System.out.println("Second argument should be an integer. Format: `[host] [part]`.");
    }

  }
}

