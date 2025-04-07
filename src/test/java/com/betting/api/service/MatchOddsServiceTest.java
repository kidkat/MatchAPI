package com.betting.api.service;

import com.betting.api.dto.requests.CreateOddRequest;
import com.betting.api.dto.requests.UpdateOddRequest;
import com.betting.api.exception.ResourceNotFoundException;
import com.betting.api.model.MatchOdd;
import com.betting.api.model.enums.Sport;
import com.betting.api.repository.MatchOddsRepository;
import com.betting.api.repository.MatchRepository;
import com.betting.api.model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author kidkat
 */
class MatchOddsServiceTest {

    private MatchOddsRepository matchOddsRepository;
    private MatchRepository matchRepository;
    private MatchService matchService;
    private MatchOddsService matchOddsService;

    @BeforeEach
    void setUp() {
        matchOddsRepository = mock(MatchOddsRepository.class);
        matchRepository = mock(MatchRepository.class);
        matchService = mock(MatchService.class);

        matchOddsService = new MatchOddsService(matchOddsRepository, matchRepository, matchService);
    }

    private Match createMatch() {
        Match match = new Match();
        match.setId(1L);
        match.setTeamA("A");
        match.setTeamB("B");
        match.setMatchDate(LocalDate.now());
        match.setMatchTime(LocalTime.NOON);
        match.setSport(Sport.FOOTBALL);
        return match;
    }

    @Test
    void getAllOddsTest() {
        when(matchOddsRepository.findAll()).thenReturn(List.of(new MatchOdd()));

        List<MatchOdd> result = matchOddsService.getAllOdds();

        assertThat(result).hasSize(1);
    }

    @Test
    void throwExceptionIfNoOddsTest() {
        when(matchOddsRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> matchOddsService.getAllOdds())
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getOddsByMatchIdTest() {
        Match match = createMatch();
        List<MatchOdd> odds = List.of(new MatchOdd());

        when(matchService.getMatchById(1L)).thenReturn(match);
        when(matchOddsRepository.findMatchOddByMatch(match)).thenReturn(Optional.of(odds));

        List<MatchOdd> result = matchOddsService.getOddByMatchId(1L);

        assertThat(result).hasSize(1);
    }

    @Test
    void throwExceptionOnGetOddByMatchIdTest() {
        Match match = createMatch();
        when(matchService.getMatchById(1L)).thenReturn(match);
        when(matchOddsRepository.findMatchOddByMatch(match)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matchOddsService.getOddByMatchId(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createOddTest() {
        Match match = createMatch();
        CreateOddRequest request = new CreateOddRequest();
        request.setMatchId(1L);
        request.setSpecifier("1");
        request.setOdd(2.5);

        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchOddsRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        MatchOdd result = matchOddsService.createOdd(request);

        assertThat(result.getOdd()).isEqualTo(2.5);
        assertThat(result.getSpecifier()).isEqualTo("1");
        assertThat(result.getMatch()).isEqualTo(match);
    }

    @Test
    void updateOddTest() {
        Match match = createMatch();
        MatchOdd odd = new MatchOdd();
        odd.setId(1L);
        odd.setSpecifier("1");
        odd.setOdd(2.0);
        odd.setMatch(match);

        UpdateOddRequest request = new UpdateOddRequest();
        request.setOdd(3.0);
        request.setSpecifier("X");

        when(matchOddsRepository.findById(1L)).thenReturn(Optional.of(odd));
        when(matchOddsRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        MatchOdd result = matchOddsService.updateOdd(1L, request);

        assertThat(result.getOdd()).isEqualTo(3.0);
        assertThat(result.getSpecifier()).isEqualTo("X");
    }

    @Test
    void updateMatchTest() {
        MatchOdd odd = new MatchOdd();
        odd.setId(1L);
        odd.setOdd(1.8);

        Match newMatch = createMatch();
        newMatch.setId(2L);

        UpdateOddRequest request = new UpdateOddRequest();
        request.setMatchId(2L);

        when(matchOddsRepository.findById(1L)).thenReturn(Optional.of(odd));
        when(matchRepository.findById(2L)).thenReturn(Optional.of(newMatch));
        when(matchOddsRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        MatchOdd result = matchOddsService.updateOdd(1L, request);

        assertThat(result.getMatch().getId()).isEqualTo(2L);
    }

    @Test
    void throwExceptionOnUpdateOddTest() {
        MatchOdd odd = new MatchOdd();
        odd.setId(1L);

        UpdateOddRequest request = new UpdateOddRequest();
        request.setMatchId(99L);

        when(matchOddsRepository.findById(1L)).thenReturn(Optional.of(odd));
        when(matchRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matchOddsService.updateOdd(1L, request))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteOddTest() {
        MatchOdd odd = new MatchOdd();
        odd.setId(1L);

        when(matchOddsRepository.findById(1L)).thenReturn(Optional.of(odd));

        matchOddsService.deleteOdd(1L);

        verify(matchOddsRepository).delete(odd);
    }

    @Test
    void throwExceptionOnDeleteTest() {
        when(matchOddsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matchOddsService.deleteOdd(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}