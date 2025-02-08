package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json for setup
 *
 * @param width width
 * @param height height
 * @param fleetSpec FleetJson
 */
public record SetupJson(

    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") FleetJson fleetSpec) {
}



