package com.betting.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author kidkat
 */
@Data
public class MatchOddsResponse {
    private Long id;
    private String specifier;
    private Double odd;

    @JsonProperty("match_id")
    private Long matchId;
}
