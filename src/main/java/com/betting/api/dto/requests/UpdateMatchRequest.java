package com.betting.api.dto.requests;

import com.betting.api.model.enums.Sport;
import com.betting.api.utils.UppercaseDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author kidkat
 */
@Data
public class UpdateMatchRequest {
    private String description;

    @JsonProperty("match_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate matchDate;

    @JsonProperty("match_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime matchTime;

    @JsonProperty("team_a")
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String teamA;

    @JsonProperty("team_b")
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String teamB;
    private Sport sport;
}
