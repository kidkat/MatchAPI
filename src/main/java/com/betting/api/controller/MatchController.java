package com.betting.api.controller;

import com.betting.api.dto.requests.CreateMatchRequest;
import com.betting.api.dto.requests.UpdateMatchRequest;
import com.betting.api.dto.responses.MatchResponse;
import com.betting.api.model.Match;
import com.betting.api.service.MatchService;
import com.betting.api.utils.Utils;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<MatchResponse>> getAllMatches() {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Get All Matches' request.", PREFIX);

        List<MatchResponse> matches = matchService.getAllMatches().stream().map(Match::toMatchResponse).toList();

        log.info("{} Request 'Get All Matches' request executed within '{}' ms",
                PREFIX, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<MatchResponse> getMatchById(@PathVariable Long matchId) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Get Match' request with matchId: '{}'", PREFIX, matchId);

        MatchResponse response = matchService.getMatchById(matchId).toMatchResponse();

        log.info("{} Request 'Get match' request for matchId: '{}' executed within '{}' ms",
                PREFIX, matchId, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MatchResponse> createMatch(@Valid @RequestBody CreateMatchRequest createMatchRequest) throws BadRequestException {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Create Match' request.", PREFIX);

        MatchResponse response = matchService.createMatch(createMatchRequest).toMatchResponse();

        log.info("{} Request 'Create Match' request executed within '{}' ms",
                PREFIX, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{matchId}")
    public ResponseEntity<MatchResponse> updateMatch(@PathVariable Long matchId, @RequestBody UpdateMatchRequest matchUpdate) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Update Match' request for matchId: '{}'", PREFIX, matchId);

        MatchResponse response = matchService.updateMatch(matchId, matchUpdate).toMatchResponse();

        log.info("{} Request 'Update Match' request for matchId: '{}' executed within '{}' ms",
                PREFIX, matchId, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long matchId) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Delete Match' request for matchId: '{}'", PREFIX, matchId);

        matchService.deleteMatch(matchId);

        log.info("{} Request 'Delete Match' request for matchId: '{}' executed within '{}' ms",
                PREFIX, matchId, Utils.getExecutionTime(startTime));
        return ResponseEntity.noContent().build();
    }
}
