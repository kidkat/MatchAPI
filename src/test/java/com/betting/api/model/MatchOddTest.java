package com.betting.api.model;

import com.betting.api.model.enums.Sport;
import com.betting.api.repository.MatchOddsRepository;
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
class MatchOddTest {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private MatchOddsRepository matchOddsRepository;

    private Match createAndSaveMatch() {
        Match match = new Match();
        match.setDescription("Roma vs Milan");
        match.setMatchDate(LocalDate.of(2025, 8, 15));
        match.setMatchTime(LocalTime.of(20, 45));
        match.setTeamA("ROMA");
        match.setTeamB("MILAN");
        match.setSport(Sport.FOOTBALL);

        return matchRepository.save(match);
    }

    @Test
    void saveAndFindOddTest() {
        Match match = createAndSaveMatch();

        MatchOdd odd = new MatchOdd();
        odd.setSpecifier("1");
        odd.setOdd(2.10);
        odd.setMatch(match);

        MatchOdd saved = matchOddsRepository.save(odd);
        MatchOdd loaded = matchOddsRepository.findById(saved.getId()).orElseThrow();

        assertThat(loaded.getId()).isNotNull();
        assertThat(loaded.getSpecifier()).isEqualTo("1");
        assertThat(loaded.getOdd()).isEqualTo(2.10);
        assertThat(loaded.getMatch()).isNotNull();
        assertThat(loaded.getMatch().getId()).isEqualTo(match.getId());
    }

    @Test
    void convertToResponseTest() {
        Match match = new Match();
        match.setId(123L);
        MatchOdd odd = new MatchOdd();
        odd.setId(456L);
        odd.setSpecifier("X");
        odd.setOdd(3.25);
        odd.setMatch(match);

        var response = odd.toMatchOddsResponse();

        assertThat(response.getId()).isEqualTo(456L);
        assertThat(response.getSpecifier()).isEqualTo("X");
        assertThat(response.getOdd()).isEqualTo(3.25);
        assertThat(response.getMatchId()).isEqualTo(123L);
    }
}