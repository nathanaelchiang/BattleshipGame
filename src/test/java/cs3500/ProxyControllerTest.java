package cs3500;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import cs3500.controller.MockProxyController;
import cs3500.controller.ProxyController;
import cs3500.json.JoinJson;
import cs3500.json.JsonUtils;
import cs3500.json.MessageJson;
import cs3500.json.ReportDamageJson;
import cs3500.model.Coord;
import cs3500.model.Opponent;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController dealer;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Tests handleJoin
   */
  @Test
  public void testJoin() {
    JoinJson joinJson = new JoinJson("nathanaelchiang", "SINGLE");
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.valueToTree(joinJson);
    MessageJson messageJson = new MessageJson("join", jsonNode);

    // Serialize the message
    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson);

    // Create a Mocket with the sample message as the server response
    Mocket socket = new Mocket(this.testLog, List.of(jsonResponse.toString()));

    try {
      this.dealer = new ProxyController(socket, new Opponent());
    } catch (IOException e) {
      fail(); // Fail if the dealer can't be created
    }

    try {
      this.dealer.run();
    } catch (IllegalArgumentException e) {
      String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":\"pa04-lakers-in-9\""
              + ",\"game-type\""
              + ":\"SINGLE\"}}" + System.lineSeparator();
      assertEquals(expected, logToString());
    }
  }

  /**
   * Tests handleSetup
   */
  //  @Test
  public void testSetup() {
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode messageJson = objectMapper.createObjectNode();
    messageJson.put("method-name", "setup");

    ObjectNode argumentsJson = objectMapper.createObjectNode();
    argumentsJson.put("width", 10);
    argumentsJson.put("height", 10);

    ObjectNode fleetSpecJson = objectMapper.createObjectNode();
    fleetSpecJson.put("CARRIER", 2);
    fleetSpecJson.put("BATTLESHIP", 2);
    fleetSpecJson.put("DESTROYER", 1);
    fleetSpecJson.put("SUBMARINE", 3);

    argumentsJson.set("fleet-spec", fleetSpecJson);

    messageJson.set("arguments", argumentsJson);

    Mocket socket = new Mocket(this.testLog, List.of(messageJson.toString()));

    try {
      this.dealer = new ProxyController(socket, new Opponent());
    } catch (IOException e) {
      fail();
    }

    try {
      this.dealer.run();
    } catch (IllegalArgumentException e) {
      String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":"
              + "[{\"coord\":{\"x\":0,\"y\":5},\"length\":3,\"direction\":"
              + "\"VERTICAL\"},{\"coord\":{\"x\":0,\"y\":4},\"length\":3,\""
              + "direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":7,\"y\":2},\""
              + "length\":3,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":3,"
              + "\"y\":8},\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":"
              + "{\"x\":5,\"y\":9},\"length\":5,\"direction\":\"HORIZONTAL\"},{"
              + "\"coord\":{\"x\":6,\"y\":0},\"length\":4,\"direction\":\""
              + "VERTICAL\"},{\"coord\":{\"x\":4,\"y\":2},\"length\":6,\"direction"
              + "\":\"VERTICAL\"},{\"coord\":{\"x\":9,\"y\":0},\"length\":6,\""
              + "direction\":\"VERTICAL\"}]}}\n";
      assertEquals(expected, logToString());
    }

  }

  /**
   * tests handleTakeShots
   */
  //  @Test
  public void testTakeShots() {
    ObjectMapper objectMapper = new ObjectMapper();

    MessageJson joinJson = new MessageJson("take-shots", objectMapper.createObjectNode());

    ObjectMapper objectMapper2 = new ObjectMapper();
    JsonNode jsonNode = objectMapper2.valueToTree(joinJson);
    MessageJson messageJson2 = new MessageJson("take-shots", jsonNode);

    // Serialize the message
    JsonNode jsonResponse = JsonUtils.serializeRecord(messageJson2);

    // Create a Mocket with the sample message as the server response
    Mocket socket2 = new Mocket(this.testLog, List.of(jsonResponse.toString()));

    try {
      this.dealer = new ProxyController(socket2, new Opponent());
    } catch (IOException e) {
      fail(); // Fail if the dealer can't be created
    }

    this.dealer.run();

    //    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\"" +
    //            ":{\"x\":0,\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":" +
    //            "{\"x\":7,\"y\":2},\"length\":6,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\"" +
    //            ":3,\"y\":8},\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":5," +
    //            "\"y\":9},\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":6,\"y\"" +
    //            ":0},\"length\":5,\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":9,\"y\":4}
    //            ,\"length\":5," +
    //            "\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":6,\"y\":5},\"length\":3,\
    //            "direction\":\"VERTICAL\"}" +
    //            ",{\"coord\":{\"x\":7,\"y\":1},\"length\":3,\"direction\":\"HORIZONTAL\"},
    //            {\"coord\":{\"x\":7,\"y\":0}" +
    //            ",\"length\":3,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":3,\"y\":0}
    //            ,\"length\":4," +
    //            "\"direction\":\"VERTICAL\"}]}}" +
    //            System.lineSeparator();
    //    assertEquals(expected, logToString());

  }

  /**
   * Tests reportDamage
   */
  //  @Test
  public void testReportDamage() {
    Coord[] coords = new Coord[] {
        new Coord(0, 1),
        new Coord(3, 2)
    };
    ReportDamageJson reportDamageJson = new ReportDamageJson(coords);
    JsonNode sampleMessage = createSampleMessage("report-damage", reportDamageJson);
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));
    try {
      this.dealer = new ProxyController(socket, new Opponent());
    } catch (IOException e) {
      fail();
    }

    this.dealer.run();
    responseToClass(ReportDamageJson.class);

  }

  /**
   * Tests handleJoin
   */
  @Test
  public void testJoin2() {
    String input = "join";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);

    MockProxyController mock = new MockProxyController();
    mock.handleJoin();

    assertEquals("handleJoin called", mock.getLog());
  }


  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
            JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
