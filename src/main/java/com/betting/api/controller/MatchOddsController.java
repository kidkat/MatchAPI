package com.betting.api.controller;

import com.betting.api.dto.requests.CreateOddRequest;
import com.betting.api.dto.requests.UpdateOddRequest;
import com.betting.api.dto.responses.MatchOddsResponse;
import com.betting.api.dto.responses.MatchResponse;
import com.betting.api.model.MatchOdd;
import com.betting.api.service.MatchOddsService;
import com.betting.api.utils.Utils;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
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

    @GetMapping
    public ResponseEntity<List<MatchOddsResponse>> getAllOdds() {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Get All Odds' request.", PREFIX);

        List<MatchOddsResponse> response = matchOddsService.getAllOdds().stream().map(MatchOdd::toMatchOddsResponse).toList();

        log.info("{} Request 'Get All Matches' request executed within '{}' ms",
                PREFIX, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<List<MatchOddsResponse>> getOddByMatchId(@PathVariable Long matchId) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Get Odd' request with match: '{}'", PREFIX, matchId);

        List<MatchOddsResponse> response = matchOddsService.getOddByMatchId(matchId).stream().map(MatchOdd::toMatchOddsResponse).toList();

        log.info("{} Request 'Get Odd' request for match: '{}' executed within '{}' ms",
                PREFIX, matchId, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<MatchOddsResponse> createOdd(@Valid @RequestBody CreateOddRequest createOddRequest) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Create Odd' request.", PREFIX);

        MatchOddsResponse response = matchOddsService.createOdd(createOddRequest).toMatchOddsResponse();

        log.info("{} Request 'Create Odd' request executed within '{}' ms",
                PREFIX, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{oddId}")
    public ResponseEntity<MatchOddsResponse> updateOdd(@PathVariable Long oddId, @RequestBody UpdateOddRequest updateOddRequest) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Update Odd' request for oddId: '{}'", PREFIX, oddId);

        MatchOddsResponse response = matchOddsService.updateOdd(oddId, updateOddRequest).toMatchOddsResponse();

        log.info("{} Request 'Update Odd' request for oddId: '{}' executed within '{}' ms",
                PREFIX, oddId, Utils.getExecutionTime(startTime));

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{oddId}")
    public ResponseEntity<Void> delete(@PathVariable Long oddId) {
        long startTime = Utils.getStartTime();
        log.info("{} Received 'Delete Odd' request for oddId: '{}'", PREFIX, oddId);

        matchOddsService.deleteOdd(oddId);

        log.info("{} Request 'Delete Odd' request for oddId: '{}' executed within '{}' ms",
                PREFIX, oddId, Utils.getExecutionTime(startTime));
        return ResponseEntity.noContent().build();
    }
}
