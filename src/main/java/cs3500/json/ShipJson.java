package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.model.Coord;

/**
 * Record for a ship
 *
 * @param start starting coordinate
 * @param length of ship
 * @param dir direction either horizontal or vertical
 */
public record ShipJson(
    @JsonProperty("coord") Coord start,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String dir) {
}
