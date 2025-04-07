package com.betting.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author kidkat
 */
@Data
public class MatchOddsResponse {
    @Schema(description = "Match odd ID", example = "1")
    private Long id;

    @Schema(description = "Match odd specifier", example = "X")
    private String specifier;

    @Schema(description = "Match odd", example = "2.25")
    private Double odd;

    @JsonProperty("match_id")
    @Schema(description = "Match ID", example = "1")
    private Long matchId;
}
