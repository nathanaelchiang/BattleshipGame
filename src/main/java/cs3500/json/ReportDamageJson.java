package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.model.Coord;

/**
 * Record for reportDamage
 *
 * @param coordinates array of coords
 */
public record ReportDamageJson(
      @JsonProperty("coordinates") Coord[] coordinates) {
}
