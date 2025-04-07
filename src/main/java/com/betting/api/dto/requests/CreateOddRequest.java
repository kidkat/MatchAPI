package com.betting.api.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author kidkat
 */
@Data
public class CreateOddRequest {

    @NotBlank(message = "'specifier' is required.")
    @Schema(description = "Match odd specifier", example = "X")
    private String specifier;

    @NotNull(message = "'odd' is required.")
    @Schema(description = "Match odd", example = "2.25")
    private Double odd;

    @JsonProperty("match_id")
    @NotNull(message = "'match_id' is required.")
    @Schema(description = "Match ID", example = "1")
    private Long matchId;
}
