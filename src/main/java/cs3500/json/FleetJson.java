package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json for a fleet
 *
 * @param ships array of Ships
 */
public record FleetJson(
    @JsonProperty("fleet") ShipJson[] ships) {
}
