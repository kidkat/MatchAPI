package com.betting.api.repository;

import com.betting.api.model.Match;
import com.betting.api.model.enums.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kidkat
 */

@DataJpaTest
@ActiveProfiles("test")
class MatchRepositoryTest {
    @Autowired
    private MatchRepository matchRepository;

    private Match createMatch() {
        Match match = new Match();
        match.setDescription("PSG vs Bayern");
        match.setMatchDate(LocalDate.of(2025, 4, 10));
        match.setMatchTime(LocalTime.of(20, 0));
        match.setTeamA("PSG");
        match.setTeamB("BAYERN");
        match.setSport(Sport.FOOTBALL);

        return match;
    }

    @Test
    void getAllMatches() {
        matchRepository.save(createMatch());
        matchRepository.save(createMatch());

        assertThat(matchRepository.findAll()).hasSize(2);
    }

    @Test
    void getMatchById() {
        Match saved = matchRepository.save(createMatch());

        Optional<Match> result = matchRepository.findById(saved.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getTeamA()).isEqualTo("PSG");
    }

    @Test
    void checkExistsMatch() {
        Match match = createMatch();
        matchRepository.save(match);

        boolean exists = matchRepository.existsByMatchDateAndMatchTimeAndTeamAAndTeamB(
                match.getMatchDate(),
                match.getMatchTime(),
                match.getTeamA(),
                match.getTeamB()
        );

        assertThat(exists).isTrue();
    }

    @Test
    void checkNotExistsMatch() {
        boolean exists = matchRepository.existsByMatchDateAndMatchTimeAndTeamAAndTeamB(
                LocalDate.of(2024, 1, 1),
                LocalTime.of(10, 0),
                "A",
                "B"
        );

        assertThat(exists).isFalse();
    }
}