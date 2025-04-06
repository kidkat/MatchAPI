package com.betting.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author kidkat
 */
@Data
public class UpdateOddRequest {
    private String specifier;
    private Double odd;

    @JsonProperty("match_id")
    private Long matchId;
}
