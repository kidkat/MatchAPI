package com.betting.api.controller;

import com.betting.api.dto.requests.CreateMatchRequest;
import com.betting.api.dto.requests.UpdateMatchRequest;
import com.betting.api.dto.responses.MatchResponse;
import com.betting.api.exception.ApiErrorResponse;
import com.betting.api.model.Match;
import com.betting.api.service.MatchService;
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
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kidkat
 */

@Log4j2
@RestController
@RequestMapping("/matches")
public class MatchController {
    private final String PREFIX = this.getClass().getSimpleName() + ":>";

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @Operation(summary = "Get all matches", description = "Returns a response with list of all matches.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get List of Matches"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping
    public ResponseEntity<List<MatchResponse>> getAllMatches() {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Get All Matches' request.", PREFIX);

        List<MatchResponse> matches = matchService.getAllMatches().stream().map(Match::toMatchResponse).toList();

        log.info("{} Request 'Get All Matches' request executed within '{}' ms",
                PREFIX, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(matches);
    }

    @Operation(summary = "Get match by it's ID", description = "Returns a response with match.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get match"),
            @ApiResponse(responseCode = "404", description = "Not Found",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
    })
    @GetMapping("/{matchId}")
    public ResponseEntity<MatchResponse> getMatchById(
            @Parameter(description = "ID of the match to retrieve", example = "1")
            @PathVariable Long matchId
    ) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Get Match' request with matchId: '{}'", PREFIX, matchId);

        MatchResponse response = matchService.getMatchById(matchId).toMatchResponse();

        log.info("{} Request 'Get match' request for matchId: '{}' executed within '{}' ms",
                PREFIX, matchId, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create match", description = "Returns a response with created match.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Get created match."),
            @ApiResponse(responseCode = "400", description = "Invalid request or Bad request or match already exists.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<MatchResponse> createMatch(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Match to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CreateMatchRequest.class),
                            examples = @ExampleObject(value = """
                    {
                      "description": "Chelsea vs Arsenal",
                      "match_date": "08/04/2025",
                      "match_time": "18:00",
                      "team_a": "Chelsea",
                      "team_b": "Arsenal",
                      "sport": 1
                    }
                """)
                    )
            )
            @Valid @RequestBody CreateMatchRequest createMatchRequest
    ) throws BadRequestException {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Create Match' request.", PREFIX);

        MatchResponse response = matchService.createMatch(createMatchRequest).toMatchResponse();

        log.info("{} Request 'Create Match' request executed within '{}' ms",
                PREFIX, Utils.getExecutionTime(startTime));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Update existing match", description = "Returns a response with updated match.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get updated match"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{matchId}")
    public ResponseEntity<MatchResponse> updateMatch(
            @Parameter(description = "ID of the match to update", example = "1")
            @PathVariable Long matchId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Match to update",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = UpdateMatchRequest.class),
                            examples = @ExampleObject(value = """
                    {
                      "description": "Chelsea vs Arsenal",
                      "match_date": "08/04/2025",
                      "match_time": "18:00",
                      "team_a": "Chelsea",
                      "team_b": "Arsenal",
                      "sport": 1
                    }
                """)
                    )
            )
            @RequestBody UpdateMatchRequest matchUpdate
    ) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Update Match' request for matchId: '{}'", PREFIX, matchId);

        MatchResponse response = matchService.updateMatch(matchId, matchUpdate).toMatchResponse();

        log.info("{} Request 'Update Match' request for matchId: '{}' executed within '{}' ms",
                PREFIX, matchId, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete match", description = "Returns nothing if deletion goes successful.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Return No Content"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteMatch(
            @Parameter(description = "ID of the match to delete", example = "1")
            @PathVariable Long matchId
    ) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Delete Match' request for matchId: '{}'", PREFIX, matchId);

        matchService.deleteMatch(matchId);

        log.info("{} Request 'Delete Match' request for matchId: '{}' executed within '{}' ms",
                PREFIX, matchId, Utils.getExecutionTime(startTime));
        return ResponseEntity.noContent().build();
    }
}
