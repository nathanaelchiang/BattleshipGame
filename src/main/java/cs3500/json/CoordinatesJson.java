package cs3500.json;


import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.model.Coord;

/**
 * Json for coordinates
 *
 * @param shots array of Coord
 */
public record CoordinatesJson(
        @JsonProperty("coordinates") Coord[] shots) {
}