package com.betting.api.service;

import com.betting.api.dto.requests.CreateMatchRequest;
import com.betting.api.dto.requests.UpdateMatchRequest;
import com.betting.api.exception.ResourceNotFoundException;
import com.betting.api.model.Match;
import com.betting.api.model.enums.Sport;
import com.betting.api.repository.MatchRepository;
import org.apache.coyote.BadRequestException;
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
class MatchServiceTest {
    private MatchRepository matchRepository;
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        matchRepository = mock(MatchRepository.class);
        matchService = new MatchService(matchRepository);
    }

    @Test
    void getAllMatches() {
        when(matchRepository.findAll()).thenReturn(List.of(new Match()));

        List<Match> result = matchService.getAllMatches();

        assertThat(result).hasSize(1);
        verify(matchRepository, times(2)).findAll();
    }

    @Test
    void getMatchesNoMatchFoundTest() {
        when(matchRepository.findAll()).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> matchService.getAllMatches())
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No match found");
    }

    @Test
    void getMatchByIdTest() {
        Match match = new Match();
        match.setId(1L);
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        Match result = matchService.getMatchById(1L);

        assertThat(result).isEqualTo(match);
    }

    @Test
    void getMatchNoMatchFoundTest() {
        when(matchRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matchService.getMatchById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void createMatchTest() throws BadRequestException {
        CreateMatchRequest request = new CreateMatchRequest();
        request.setDescription("Test");
        request.setMatchDate(LocalDate.now());
        request.setMatchTime(LocalTime.NOON);
        request.setTeamA("A");
        request.setTeamB("B");
        request.setSport(Sport.FOOTBALL);

        when(matchRepository.existsByMatchDateAndMatchTimeAndTeamAAndTeamB(any(), any(), any(), any())).thenReturn(false);
        when(matchRepository.save(any(Match.class))).thenAnswer(i -> {
            Match m = i.getArgument(0);
            m.setId(1L);
            return m;
        });

        Match created = matchService.createMatch(request);

        assertThat(created.getId()).isEqualTo(1L);
        verify(matchRepository).save(any(Match.class));
    }

    @Test
    void createNoMatchIfExistsTest() {
        CreateMatchRequest request = new CreateMatchRequest();
        request.setMatchDate(LocalDate.now());
        request.setMatchTime(LocalTime.NOON);
        request.setTeamA("A");
        request.setTeamB("B");

        when(matchRepository.existsByMatchDateAndMatchTimeAndTeamAAndTeamB(any(), any(), any(), any())).thenReturn(true);

        assertThatThrownBy(() -> matchService.createMatch(request))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    void updateMatchTest() {
        Match match = new Match();
        match.setId(1L);
        match.setTeamA("OLD");

        UpdateMatchRequest update = new UpdateMatchRequest();
        update.setTeamA("NEW");

        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Match result = matchService.updateMatch(1L, update);

        assertThat(result.getTeamA()).isEqualTo("NEW");
    }

    @Test
    void deleteMatchTest() {
        Match match = new Match();
        match.setId(1L);

        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        matchService.deleteMatch(1L);

        verify(matchRepository).delete(match);
    }

    @Test
    void deleteMatchNoMatchFoundTest() {
        when(matchRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> matchService.deleteMatch(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}