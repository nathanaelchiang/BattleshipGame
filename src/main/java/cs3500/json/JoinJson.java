package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 *   "name": "method name",
 *   "arguments": {
 *     "name
 *   }
 * }
 * </code>
 * </p>
 *
 * @param messageName the name of the server method request
 * @param gameType the arguments passed along with the message formatted as a Json object
 */
public record JoinJson(
      @JsonProperty("name") String messageName,
      @JsonProperty("game-type") String gameType) {

}
