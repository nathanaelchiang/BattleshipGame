package cs3500.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.json.CoordinatesJson;
import cs3500.json.EndGameJson;
import cs3500.json.FleetJson;
import cs3500.json.JoinJson;
import cs3500.json.JsonUtils;
import cs3500.json.MessageJson;
import cs3500.json.ReportDamageJson;
import cs3500.json.ShipJson;
import cs3500.model.Coord;
import cs3500.model.GameResult;
import cs3500.model.Opponent;
import cs3500.model.Player;
import cs3500.model.Ship;
import cs3500.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Proxy for the controller
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();
  private static final JsonNode VOID_RESPONSE =
          new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Proxy controller constructor
   *
   * @param server socket
   * @param player AI Opponent
   * @throws IOException exception
  */
  public ProxyController(Socket server, Opponent player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        System.out.println("Attempting to print out the project");
        MessageJson message = parser.readValueAs(MessageJson.class);
        String name = message.messageName();
        System.out.println(name);
        JsonNode arguments = message.arguments();
        System.out.println("Received message: " + name);
        System.out.println("Received arguments: " + arguments);
        System.out.println(name);
        if (name.equals("join")) {
          handleJoin();
        } else if (name.equals("setup")) {
          handleSetup(arguments);
        } else if (name.equals("take-shots")) {
          handleTakeShots();
        } else if (name.equals("report-damage")) {
          handleReportDamage(arguments);
        } else if (name.equals("successful-hits")) {
          handleSuccessfulHits(arguments);
        } else if (name.equals("end-game")) {
          handleWin(arguments);
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Disconnected from server or parsing exception"
              + e.getMessage());
    }
  }

  /**
   * Gets our project name
   *
   */
  public void handleJoin() {
    JoinJson joinJson = new JoinJson("pa04-lakers-in-9", "SINGLE");
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.valueToTree(joinJson);
    MessageJson json = new MessageJson("join", jsonNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(json);
    this.out.println(jsonResponse);
  }

  /**
   * Runs the setup method for the AI
   *
   * @param args a JsonNode
   */
  public void handleSetup(JsonNode args) {
    int height = args.get("height").asInt();
    int width = args.get("width").asInt();
    Map<ShipType, Integer> specifications = new HashMap<>();
    JsonNode ships = args.get("fleet-spec");

    int carrierCount = ships.get("CARRIER").asInt();
    int battleshipCount = ships.get("BATTLESHIP").asInt();
    int destroyerCount = ships.get("DESTROYER").asInt();
    int submarineCount = ships.get("SUBMARINE").asInt();

    specifications.put(ShipType.CARRIER, carrierCount);
    specifications.put(ShipType.BATTLESHIP, battleshipCount);
    specifications.put(ShipType.DESTROYER, destroyerCount);
    specifications.put(ShipType.SUBMARINE, submarineCount);

    List<Ship> shipList = player.setup(height, width, specifications);
    List<ShipJson> fleetList = new ArrayList<>();
    for (Ship ship : shipList) {
      fleetList.add(
                new ShipJson(ship.getCoord().get(0), ship.getCoord().size(),
                        getDirection(ship.getCoord())));
    }
    //ShipAdapter shipAdapter = fleetList.toArray(new Coord[fleetList.size()]);
    FleetJson fleetJson = new FleetJson(fleetList.toArray(new ShipJson[fleetList.size()]));
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.valueToTree(fleetJson);
    MessageJson messageJson = new MessageJson("setup", jsonNode);

    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Gets the direction of a boat
   *
   * @param list of coordinates
   * @return vertical or horizontal
   */
  private String getDirection(List<Coord> list) {
    Coord startingCoord = list.get(0);
    Coord secondCoord = list.get(1);
    if (startingCoord.getX() == secondCoord.getX()) {
      return "VERTICAL";
    } else {
      return "HORIZONTAL";
    }
  }

  /**
   * Runs the takeShots method for the AI
   */
  public void handleTakeShots() {
    List<Coord> aiShots = player.takeShots();
    CoordinatesJson volleyJson = new CoordinatesJson(aiShots.toArray(new Coord[aiShots.size()]));
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.valueToTree(volleyJson);
    MessageJson messageJson = new MessageJson("take-shots", jsonNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Runs the reportDamage method for the AI
   *
   * @param args a JsonNode
   * @throws JsonProcessingException exception for reportDamage
   */
  public void handleReportDamage(JsonNode args) throws JsonProcessingException {
    JsonNode node = args.get("coordinates");
    ObjectMapper mapper = new ObjectMapper();
    Coord[] record = mapper.convertValue(node, Coord[].class);
    List<Coord> arrayList = new ArrayList<>(Arrays.asList(record));
    List<Coord> hitList = player.reportDamage(arrayList);
    ReportDamageJson reportDamage = new ReportDamageJson(hitList.toArray(
            new Coord[hitList.size()]));

    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.valueToTree(reportDamage);
    MessageJson messageJson = new MessageJson("report-damage", jsonNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson);
    this.out.println(jsonResponse);
  }

  /**
   * Runs the successfulHits method for the AI
   *
   * @param args a JsonNode
   */
  public void handleSuccessfulHits(JsonNode args) {
    JsonNode node = args.get("coordinates");

    ObjectMapper mapper = new ObjectMapper();
    Coord[] record = mapper.convertValue(node, Coord[].class);
    List<Coord> arrayList = new ArrayList<>(Arrays.asList(record));
    player.successfulHits(arrayList);
    MessageJson json = new MessageJson("successful-hits", null);
    JsonNode jsonResponse = JsonUtils.serializeRecord(json);
    this.out.println(jsonResponse);

  }

  /**
   * handles if the game ends
   *
   * @param args JsonNode
   * @throws IOException throws IOException
   */
  public void handleWin(JsonNode args) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    EndGameJson record = mapper.convertValue(args, EndGameJson.class);
    boolean endGame = false;

    if (record.result().equals("WIN")) {
      player.endGame(GameResult.WIN, args.get("reason").asText());
      endGame = true;
    } else if (record.result().equals("LOSE")) {
      player.endGame(GameResult.LOSE, args.get("reason").asText());
      endGame = true;
    } else if (record.result().equals("DRAW")) {
      player.endGame(GameResult.DRAW, args.get("reason").asText());
      endGame = true;
    }

    MessageJson json = new MessageJson("end-game", null);
    JsonNode jsonResponse = JsonUtils.serializeRecord(json);
    this.out.println(jsonResponse);
    this.out.println(VOID_RESPONSE);
    if (endGame) {
      server.close();
    }
  }
}
