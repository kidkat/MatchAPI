package com.betting.api.model;

import com.betting.api.model.enums.Sport;
import com.betting.api.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kidkat
 */

@DataJpaTest
@ActiveProfiles("test")
class MatchTest {
    @Autowired
    private MatchRepository matchRepository;

    @Test
    void saveMatchTest() {
        Match match = new Match();
        match.setDescription("Argentina vs Brazil");
        match.setMatchDate(LocalDate.of(2025, 6, 10));
        match.setMatchTime(LocalTime.of(20, 30));
        match.setTeamA("ARGENTINA");
        match.setTeamB("BRAZIL");
        match.setSport(Sport.FOOTBALL);

        Match saved = matchRepository.save(match);
        Match loaded = matchRepository.findById(saved.getId()).orElseThrow();

        assertThat(loaded.getId()).isNotNull();
        assertThat(loaded.getDescription()).isEqualTo("Argentina vs Brazil");
        assertThat(loaded.getTeamA()).isEqualTo("ARGENTINA");
        assertThat(loaded.getMatchDate()).isEqualTo(LocalDate.of(2025, 6, 10));
    }

    @Test
    void convertToResponseTest() {
        Match match = new Match();
        match.setId(123L);
        match.setDescription("City vs United");
        match.setMatchDate(LocalDate.of(2025, 7, 1));
        match.setMatchTime(LocalTime.of(18, 0));
        match.setTeamA("CITY");
        match.setTeamB("UNITED");
        match.setSport(Sport.FOOTBALL);

        var response = match.toMatchResponse();

        assertThat(response.getId()).isEqualTo(123L);
        assertThat(response.getDescription()).isEqualTo("City vs United");
        assertThat(response.getSport()).isEqualTo(Sport.FOOTBALL);
        assertThat(response.getTeamA()).isEqualTo("CITY");
        assertThat(response.getMatchTime()).isEqualTo(LocalTime.of(18, 0));
    }
}