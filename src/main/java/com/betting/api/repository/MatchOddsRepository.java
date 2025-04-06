package com.betting.api.repository;

import com.betting.api.model.Match;
import com.betting.api.model.MatchOdd;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author kidkat
 */
public interface MatchOddsRepository extends JpaRepository<MatchOdd, Long> {
    Optional<List<MatchOdd>> findMatchOddByMatch(Match match);
}
