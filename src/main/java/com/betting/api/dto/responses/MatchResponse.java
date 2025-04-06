package com.betting.api.dto.responses;

import com.betting.api.model.enums.Sport;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author kidkat
 */
@Data
public class MatchResponse {
    private Long id;
    private String description;

    @JsonProperty("match_date")
    private LocalDate matchDate;

    @JsonProperty("match_time")
    private LocalTime matchTime;

    @JsonProperty("team_a")
    private String teamA;

    @JsonProperty("team_b")
    private String teamB;
    private Sport sport;
}
