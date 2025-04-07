package com.betting.api.controller;

import com.betting.api.dto.requests.CreateMatchRequest;
import com.betting.api.dto.requests.CreateOddRequest;
import com.betting.api.dto.requests.UpdateOddRequest;
import com.betting.api.dto.responses.MatchOddsResponse;
import com.betting.api.dto.responses.MatchResponse;
import com.betting.api.exception.ApiErrorResponse;
import com.betting.api.model.MatchOdd;
import com.betting.api.service.MatchOddsService;
import com.betting.api.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kidkat
 */

@Log4j2
@RestController
@RequestMapping("/odds")
public class MatchOddsController {
    private final String PREFIX = this.getClass().getSimpleName() + ":>";

    private final MatchOddsService matchOddsService;

    public MatchOddsController(MatchOddsService matchOddsService) {
        this.matchOddsService = matchOddsService;
    }

    @Operation(summary = "Get all odds", description = "Returns a response with list of all odds.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get list of odds"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<MatchOddsResponse>> getAllOdds() {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Get All Odds' request.", PREFIX);

        List<MatchOddsResponse> response = matchOddsService.getAllOdds().stream().map(MatchOdd::toMatchOddsResponse).toList();

        log.info("{} Request 'Get All Matches' request executed within '{}' ms",
                PREFIX, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all odds by match's ID", description = "Returns a response with list of all odds for specific match id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get odds by match id"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{matchId}")
    public ResponseEntity<List<MatchOddsResponse>> getOddByMatchId(
            @Parameter(description = "ID of the match to get odds", example = "1")
            @PathVariable Long matchId
    ) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Get Odd' request with match: '{}'", PREFIX, matchId);

        List<MatchOddsResponse> response = matchOddsService.getOddByMatchId(matchId).stream().map(MatchOdd::toMatchOddsResponse).toList();

        log.info("{} Request 'Get Odd' request for match: '{}' executed within '{}' ms",
                PREFIX, matchId, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create odd", description = "Returns a response created odd.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Get created odd."),
            @ApiResponse(responseCode = "400", description = "Invalid request or Bad request or match already exists.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<MatchOddsResponse> createOdd(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Odd to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateOddRequest.class),
                            examples = @ExampleObject(value = """
                    {
                      "specifier": "X",
                      "odd": 2.25,
                      "match_id": 1
                    }
                """)
                    )
            )
            @Valid @RequestBody CreateOddRequest createOddRequest
    ) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Create Odd' request.", PREFIX);

        MatchOddsResponse response = matchOddsService.createOdd(createOddRequest).toMatchOddsResponse();

        log.info("{} Request 'Create Odd' request executed within '{}' ms",
                PREFIX, Utils.getExecutionTime(startTime));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Update odd", description = "Returns a response updated odd.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get updated odd"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{oddId}")
    public ResponseEntity<MatchOddsResponse> updateOdd(
            @Parameter(description = "ID of a odd to update", example = "1")
            @PathVariable Long oddId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Odd to update",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateOddRequest.class),
                            examples = @ExampleObject(value = """
                    {
                      "specifier": "X",
                      "odd": 2.25,
                      "match_id": 1
                    }
                """)
                    )
            )
            @RequestBody UpdateOddRequest updateOddRequest
    ) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Update Odd' request for oddId: '{}'", PREFIX, oddId);

        MatchOddsResponse response = matchOddsService.updateOdd(oddId, updateOddRequest).toMatchOddsResponse();

        log.info("{} Request 'Update Odd' request for oddId: '{}' executed within '{}' ms",
                PREFIX, oddId, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete odd", description = "Returns nothing if deletion goes successful.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Return No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{oddId}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of a odd to delete", example = "1")
            @PathVariable Long oddId
    ) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Delete Odd' request for oddId: '{}'", PREFIX, oddId);

        matchOddsService.deleteOdd(oddId);

        log.info("{} Request 'Delete Odd' request for oddId: '{}' executed within '{}' ms",
                PREFIX, oddId, Utils.getExecutionTime(startTime));
        return ResponseEntity.noContent().build();
    }
}
