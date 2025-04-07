package com.betting.api.controller;

import com.betting.api.dto.requests.CreateOddRequest;
import com.betting.api.dto.requests.UpdateOddRequest;
import com.betting.api.dto.responses.MatchOddsResponse;
import com.betting.api.model.Match;
import com.betting.api.model.MatchOdd;
import com.betting.api.service.MatchOddsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

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

@WebMvcTest(MatchOddsController.class)
class MatchOddsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchOddsService matchOddsService;

    @Autowired
    private ObjectMapper objectMapper;

    private MatchOddsResponse sampleOddResponse() {
        MatchOddsResponse response = new MatchOddsResponse();
        response.setId(1L);
        response.setSpecifier("1");
        response.setOdd(1.75);
        response.setMatchId(100L);
        return response;
    }

    private MatchOdd sampleMatchOddResponse() {
        Match match = new Match();
        match.setId(100L);


        MatchOdd response = new MatchOdd();
        response.setId(1L);
        response.setSpecifier("1");
        response.setOdd(1.75);
        response.setMatch(match);

        return response;
    }

    @Test
    void getOddsTest() throws Exception {
        when(matchOddsService.getAllOdds()).thenReturn(List.of(sampleMatchOddResponse()));

        mockMvc.perform(get("/odds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specifier").value("1"));
    }

    @Test
    void getOddByMatchTest() throws Exception {
        when(matchOddsService.getOddByMatchId(100L)).thenReturn(List.of(sampleMatchOddResponse()));

        mockMvc.perform(get("/odds/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].match_id").value(100));
    }

    @Test
    void createOddTest() throws Exception {
        CreateOddRequest request = new CreateOddRequest();
        request.setMatchId(100L);
        request.setSpecifier("2");
        request.setOdd(2.25);

        MatchOddsResponse response = new MatchOddsResponse();
        response.setId(2L);
        response.setSpecifier("2");
        response.setOdd(2.25);
        response.setMatchId(100L);

        when(matchOddsService.createOdd(any())).thenReturn(sampleMatchOddResponse());

        mockMvc.perform(post("/odds")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specifier").value("1"));
    }

    @Test
    void updateOddTest() throws Exception {
        UpdateOddRequest request = new UpdateOddRequest();
        request.setOdd(3.0);
        request.setSpecifier("X");

        MatchOddsResponse response = new MatchOddsResponse();
        response.setId(1L);
        response.setOdd(3.0);
        response.setSpecifier("X");
        response.setMatchId(100L);

        when(matchOddsService.updateOdd(eq(1L), any())).thenReturn(sampleMatchOddResponse());

        mockMvc.perform(put("/odds/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.odd").value(1.75));
    }

    @Test
    void deleteOddTest() throws Exception {
        doNothing().when(matchOddsService).deleteOdd(1L);

        mockMvc.perform(delete("/odds/1"))
                .andExpect(status().isNoContent());
    }
}