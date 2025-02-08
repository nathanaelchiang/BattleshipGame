package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Record for FleetSpec
 *
 * @param carrierCount number of carriers
 * @param battleshipCount number of battleships
 * @param destroyerCount number of destroyers
 * @param submarineCount number of submarines
 */
public record FleetSpecJson(
    @JsonProperty("CARRIER") int carrierCount,
    @JsonProperty("BATTLESHIP") int battleshipCount,
    @JsonProperty("DESTROYER") int destroyerCount,
    @JsonProperty("SUBMARINE") int submarineCount
) {
}
