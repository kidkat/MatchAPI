package com.betting.api.controller;

import com.betting.api.dto.requests.CreateMatchRequest;
import com.betting.api.dto.requests.UpdateMatchRequest;
import com.betting.api.dto.responses.MatchResponse;
import com.betting.api.model.Match;
import com.betting.api.model.enums.Sport;
import com.betting.api.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author kidkat
 */

@WebMvcTest(MatchController.class)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @Autowired
    private ObjectMapper objectMapper;

    private MatchResponse sampleMatchResponse() {
        MatchResponse response = new MatchResponse();
        response.setId(1L);
        response.setTeamA("A");
        response.setTeamB("B");
        response.setDescription("Team A vs Team B");
        response.setMatchDate(LocalDate.of(2025, 4, 7));
        response.setMatchTime(LocalTime.of(20, 0));
        response.setSport(Sport.FOOTBALL);
        return response;
    }

    private Match sampleMatch() {
        Match response = new Match();
        response.setId(1L);
        response.setTeamA("A");
        response.setTeamB("B");
        response.setDescription("Team A vs Team B");
        response.setMatchDate(LocalDate.of(2025, 4, 7));
        response.setMatchTime(LocalTime.of(20, 0));
        response.setSport(Sport.FOOTBALL);

        return response;
    }

    @Test
    @DisplayName("GET /matches should return match list")
    void getAllMatchesTest() throws Exception {
        when(matchService.getAllMatches()).thenReturn(List.of(sampleMatch()));

        mockMvc.perform(get("/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].team_a").value("A"));
    }

    @Test
    @DisplayName("GET /matches/{id} should return match by ID")
    void getMatchByIdTest() throws Exception {
        when(matchService.getMatchById(1L)).thenReturn(sampleMatch());

        mockMvc.perform(get("/matches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team_a").value("A"));
    }

    @Test
    @DisplayName("POST /matches should create match")
    void createMatchTest() throws Exception {
        CreateMatchRequest request = new CreateMatchRequest();
        request.setTeamA("A");
        request.setTeamB("B");
        request.setDescription("Team A vs Team B");
        request.setMatchDate(LocalDate.of(2025, 4, 7));
        request.setMatchTime(LocalTime.of(20, 0));
        request.setSport(Sport.FOOTBALL);

        when(matchService.createMatch(any())).thenReturn(sampleMatch());

        mockMvc.perform(post("/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team_b").value("B"));
    }

    @Test
    @DisplayName("PUT /matches/{id} should update match")
    void updateMatchTest() throws Exception {
        UpdateMatchRequest update = new UpdateMatchRequest();
        update.setTeamA("NEW");

        when(matchService.updateMatch(eq(1L), any())).thenReturn(sampleMatch());

        mockMvc.perform(put("/matches/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.team_a").value("A"));
    }

    @Test
    void deleteMatchTest() throws Exception {
        doNothing().when(matchService).deleteMatch(1L);

        mockMvc.perform(delete("/matches/1"))
                .andExpect(status().isNoContent());
    }
}