package com.betting.api.service;

import com.betting.api.dto.requests.CreateOddRequest;
import com.betting.api.dto.requests.UpdateOddRequest;
import com.betting.api.exception.ResourceNotFoundException;
import com.betting.api.model.Match;
import com.betting.api.model.MatchOdd;
import com.betting.api.repository.MatchOddsRepository;
import com.betting.api.repository.MatchRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * @author kidkat
 */

@Log4j2
@Service
@Transactional
public class MatchOddsService {
    private final String PREFIX = this.getClass().getSimpleName() + ":>";

    private final MatchOddsRepository matchOddsRepository;
    private final MatchRepository matchRepository;
    private final MatchService matchService;

    public MatchOddsService(MatchOddsRepository matchOddsRepository, MatchRepository matchRepository, MatchService matchService) {
        this.matchOddsRepository = matchOddsRepository;
        this.matchRepository = matchRepository;
        this.matchService = matchService;
    }

    public List<MatchOdd> getAllOdds() {
        List<MatchOdd> oddsList = matchOddsRepository.findAll();

        if(oddsList.isEmpty()){
            log.error("{} No Odds found in database!", PREFIX);
            throw new ResourceNotFoundException("No odd found");
        }

        return matchOddsRepository.findAll();
    }

    public List<MatchOdd> getOddByMatchId(Long matchId) {
        Match match = matchService.getMatchById(matchId);
        Optional<List<MatchOdd>> oddOptional = matchOddsRepository.findMatchOddByMatch(match);

        oddOptional.orElseThrow(()-> new ResourceNotFoundException("Odd for matchId: " + matchId + " do not exists!"));

        return oddOptional.get();
    }

    public MatchOdd createOdd(CreateOddRequest createOddRequest) {
        Match match = matchRepository.findById(createOddRequest.getMatchId())
                .orElseThrow(()-> new ResourceNotFoundException("Match with Id: " + createOddRequest.getMatchId() + " do not exists!"));

        MatchOdd odd = new MatchOdd();
        odd.setSpecifier(createOddRequest.getSpecifier());
        odd.setOdd(createOddRequest.getOdd());
        odd.setMatch(match);

        return matchOddsRepository.save(odd);
    }

    public MatchOdd updateOdd(Long oddId, UpdateOddRequest updateOddRequest) {
        MatchOdd odd = matchOddsRepository.findById(oddId)
                .orElseThrow(() -> new ResourceNotFoundException("Odd not found with id: " + oddId));

        log.info("Found Odd with id: '{}', updating...", oddId);

        if(updateOddRequest.getOdd() != null)
            odd.setOdd(updateOddRequest.getOdd());

        if(updateOddRequest.getSpecifier() != null)
            odd.setSpecifier(updateOddRequest.getSpecifier());

        if(updateOddRequest.getMatchId() != null) {
            Optional<Match> match = matchRepository.findById(updateOddRequest.getMatchId());

            if(match.isPresent()) {
                odd.setMatch(match.get());
            }else{
                throw new ResourceNotFoundException("Match not found with id: " + updateOddRequest.getMatchId() + " to update Odd.");
            }
        }

        return matchOddsRepository.save(odd);
    }

    public void deleteOdd(Long id) {
        Optional<MatchOdd> oddOptional = matchOddsRepository.findById(id);
        MatchOdd odd = oddOptional.orElseThrow(()-> new ResourceNotFoundException("Odd with Id: " + id + " do not exists!"));

        matchOddsRepository.delete(odd);
    }
}
