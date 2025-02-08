package cs3500.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json for Endgame
 *
 * @param result result
 * @param reason reason for endgame result
 */
public record EndGameJson(
        @JsonProperty("result") String result,
        @JsonProperty("reason") String reason
) {
}
