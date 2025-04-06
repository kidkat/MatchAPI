package com.betting.api.service;

import com.betting.api.dto.requests.CreateMatchRequest;
import com.betting.api.dto.requests.UpdateMatchRequest;
import com.betting.api.exception.ResourceNotFoundException;
import com.betting.api.model.Match;
import com.betting.api.repository.MatchRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author kidkat
 */

@Log4j2
@Service
@Transactional
public class MatchService {
    private final String PREFIX = this.getClass().getSimpleName() + ":>";

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getAllMatches(){
        List<Match> matchList = matchRepository.findAll();

        if(matchList.isEmpty()){
            log.error("{} No match found in database!", PREFIX);
            throw new ResourceNotFoundException("No match found");
        }

        return matchRepository.findAll();
    }

    public Match getMatchById(Long matchId){
        Optional<Match> matchOptional = matchRepository.findById(matchId);

        matchOptional.orElseThrow(()-> new ResourceNotFoundException("Match with Id: " + matchId + " do not exists!"));

        return matchOptional.get();
    }

    public Match createMatch(CreateMatchRequest createMatchRequest) throws BadRequestException {
        boolean matchExists = matchRepository.existsByMatchDateAndMatchTimeAndTeamAAndTeamB(
                createMatchRequest.getMatchDate(), createMatchRequest.getMatchTime(),
                createMatchRequest.getTeamA(), createMatchRequest.getTeamB()
        );

        if(matchExists){
            throw new BadRequestException("Match already exists, cannot create.");
        }

        Match match = new Match();
        match.setDescription(createMatchRequest.getDescription());
        match.setMatchDate(createMatchRequest.getMatchDate());
        match.setMatchTime(createMatchRequest.getMatchTime());
        match.setTeamA(createMatchRequest.getTeamA());
        match.setTeamB(createMatchRequest.getTeamB());
        match.setSport(createMatchRequest.getSport());

        return matchRepository.save(match);
    }

    public Match updateMatch(Long matchId, UpdateMatchRequest updateMatchRequest){
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + matchId));

        log.info("Found match with id: '{}', updating...", matchId);

        if(updateMatchRequest.getDescription() != null)
            match.setDescription(updateMatchRequest.getDescription());

        if(updateMatchRequest.getMatchDate() != null)
            match.setMatchDate(updateMatchRequest.getMatchDate());

        if(updateMatchRequest.getMatchTime() != null)
            match.setMatchTime(updateMatchRequest.getMatchTime());

        if(updateMatchRequest.getTeamA() != null)
            match.setTeamA(updateMatchRequest.getTeamA().toUpperCase());

        if(updateMatchRequest.getTeamB() != null)
            match.setTeamB(updateMatchRequest.getTeamB().toUpperCase());

        if(updateMatchRequest.getSport() != null)
            match.setSport(updateMatchRequest.getSport());

        return matchRepository.save(match);
    }

    public void deleteMatch(Long matchId) {
        Optional<Match> matchOptional = matchRepository.findById(matchId);
        Match match = matchOptional.orElseThrow(()-> new ResourceNotFoundException("Match with Id: " + matchId + " do not exists!"));

        matchRepository.delete(match);
    }
}
