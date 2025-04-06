package com.betting.api.repository;

import com.betting.api.model.Match;
import com.betting.api.model.MatchOdd;
import com.betting.api.model.enums.Sport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author kidkat
 */
@DataJpaTest
@ActiveProfiles("test")
class MatchOddsRepositoryTest {
    @Autowired
    private MatchOddsRepository matchOddsRepository;

    @Autowired
    private MatchRepository matchRepository;

    private Match createAndSaveMatch() {
        Match match = new Match();
        match.setDescription("Chelsea vs Arsenal");
        match.setMatchDate(LocalDate.of(2025, 5, 1));
        match.setMatchTime(LocalTime.of(19, 45));
        match.setTeamA("CHELSEA");
        match.setTeamB("ARSENAL");
        match.setSport(Sport.FOOTBALL);

        return matchRepository.save(match);
    }

    @Test
    void shouldSaveMatchOdd() {
        Match match = createAndSaveMatch();

        MatchOdd odd = new MatchOdd();
        odd.setSpecifier("1");
        odd.setOdd(2.25);
        odd.setMatch(match);

        MatchOdd saved = matchOddsRepository.save(odd);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getMatch().getId()).isEqualTo(match.getId());
        assertThat(saved.getSpecifier()).isEqualTo("1");
        assertThat(saved.getOdd()).isEqualTo(2.25);
    }

    @Test
    void shouldFindMatchOddsByMatch() {
        Match match = createAndSaveMatch();

        MatchOdd odd1 = new MatchOdd();
        odd1.setSpecifier("1");
        odd1.setOdd(1.90);
        odd1.setMatch(match);

        MatchOdd odd2 = new MatchOdd();
        odd2.setSpecifier("2");
        odd2.setOdd(2.10);
        odd2.setMatch(match);

        matchOddsRepository.saveAll(List.of(odd1, odd2));

        Optional<List<MatchOdd>> result = matchOddsRepository.findMatchOddByMatch(match);

        assertThat(result).isPresent();
        assertThat(result.get().size()).isEqualTo(2);
    }

    @Test
    void shouldReturnEmptyIfNoOddsFound() {
        Match match = createAndSaveMatch();

        Optional<List<MatchOdd>> result = matchOddsRepository.findMatchOddByMatch(match);

        assertThat(result).isPresent();
        assertThat(result.get().isEmpty()).isTrue();
    }
}