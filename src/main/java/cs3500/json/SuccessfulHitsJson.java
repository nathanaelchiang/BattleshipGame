package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.model.Coord;

/**
 * Record for successful hits
 *
 * @param coordinates array of coordinates
 */
public record SuccessfulHitsJson(
        @JsonProperty("coordinates") Coord[] coordinates) {

}
