package com.betting.api.dto.requests;

import com.betting.api.model.enums.Sport;
import com.betting.api.utils.UppercaseDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import jakarta.validation.constraints.*;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author kidkat
 */
@Data
@ToString
public class CreateMatchRequest{
    @NotBlank(message = "Description is required.")
    @Schema(description = "Match description", example = "Barcelona vs Real Madrid")
    private String description;

    @JsonProperty("match_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "'match_date' is required.")
    @Schema(description = "Match date", example = "08/04/2025")
    private LocalDate matchDate;

    @JsonProperty("match_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @NotNull(message = "'match_time' is required.")
    @Schema(description = "Match time", example = "20:45")
    private LocalTime matchTime;

    @JsonProperty("team_a")
    @NotBlank(message = "'team_a' is required.")
    @JsonDeserialize(using = UppercaseDeserializer.class)
    @Schema(description = "Team A name", example = "Barcelona")
    private String teamA;

    @JsonProperty("team_b")
    @NotBlank(message = "'team_b' is required.")
    @JsonDeserialize(using = UppercaseDeserializer.class)
    @Schema(description = "Team B name", example = "Real Madrid")
    private String teamB;

    @NotNull(message = "'sport' is required.")
    @Schema(description = "Sport type", example = "1")
    private Sport sport;
}
