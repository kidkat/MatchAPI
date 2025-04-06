package com.betting.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author kidkat
 */
@Data
public class CreateOddRequest {

    @NotBlank(message = "'specifier' is required.")
    private String specifier;

    @NotNull(message = "'odd' is required.")
    private Double odd;

    @JsonProperty("match_id")
    @NotNull(message = "'match_id' is required.")
    private Long matchId;
}
