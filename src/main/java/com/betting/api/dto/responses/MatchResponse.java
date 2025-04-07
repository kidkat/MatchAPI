package com.betting.api.dto.responses;

import com.betting.api.model.enums.Sport;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author kidkat
 */
@Data
public class MatchResponse {
    @Schema(description = "Match ID", example = "1")
    private Long id;
    @Schema(description = "Match description", example = "Barcelona vs Real Madrid")
    private String description;

    @Schema(description = "Match date", example = "2025-04-08")
    @JsonProperty("match_date")
    private LocalDate matchDate;

    @Schema(description = "Match time", example = "20:45")
    @JsonProperty("match_time")
    private LocalTime matchTime;

    @Schema(description = "Team A name", example = "Barcelona")
    @JsonProperty("team_a")
    private String teamA;

    @Schema(description = "Team B name", example = "Real Madrid")
    @JsonProperty("team_b")
    private String teamB;

    @Schema(description = "Sport type", example = "1")
    private Sport sport;
}
