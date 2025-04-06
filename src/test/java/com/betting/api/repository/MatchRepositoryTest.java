package com.betting.api.repository;

import com.betting.api.model.Match;
import com.betting.api.model.MatchOdd;
import com.betting.api.model.enums.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kidkat
 */

@DataJpaTest
@ActiveProfiles("test")
class MatchRepositoryTest {
    @Autowired
    private MatchRepository matchRepository;

    @Test
    void shouldSaveAndLoadMatchWithOdds() {
        Match match = new Match();
        match.setDescription("Real vs Barca");
        match.setMatchDate(LocalDate.of(2025, 4, 10));
        match.setMatchTime(LocalTime.of(21, 0));
        match.setTeamA("REAL");
        match.setTeamB("BARCA");
        match.setSport(Sport.FOOTBALL);

        MatchOdd odds = new MatchOdd();
        odds.setSpecifier("1");
        odds.setOdd(1.95);
        odds.setMatch(match);

        match.setOdds(List.of(odds));

        Match saved = matchRepository.save(match);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getOdds()).hasSize(1);
        assertThat(saved.getOdds().get(0).getOdd()).isEqualTo(1.95);
    }

    @Test
    void shouldDetectDuplicateMatchByDateTimeAndTeams() {
        Match match = new Match();
        match.setDescription("Real vs Barca");
        match.setMatchDate(LocalDate.of(2025, 4, 10));
        match.setMatchTime(LocalTime.of(21, 0));
        match.setTeamA("REAL");
        match.setTeamB("BARCA");
        match.setSport(Sport.FOOTBALL);

        matchRepository.save(match);

        boolean exists = matchRepository.existsByMatchDateAndMatchTimeAndTeamAAndTeamB(
                match.getMatchDate(), match.getMatchTime(), match.getTeamA(), match.getTeamB());

        assertThat(exists).isTrue();
    }
}