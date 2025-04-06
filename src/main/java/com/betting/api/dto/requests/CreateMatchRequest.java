package com.betting.api.dto.requests;

import com.betting.api.model.enums.Sport;
import com.betting.api.utils.UppercaseDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
    private String description;

    @JsonProperty("match_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @NotNull(message = "'match_date' is required.")
    private LocalDate matchDate;

    @JsonProperty("match_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @NotNull(message = "'match_time' is required.")
    private LocalTime matchTime;

    @JsonProperty("team_a")
    @NotBlank(message = "'team_a' is required.")
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String teamA;

    @JsonProperty("team_b")
    @NotBlank(message = "'team_b' is required.")
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String teamB;

    @NotNull(message = "'sport' is required.")
    private Sport sport;
}
