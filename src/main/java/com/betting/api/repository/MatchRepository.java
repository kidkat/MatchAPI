package com.betting.api.repository;

import com.betting.api.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author kidkat
 */
public interface MatchRepository extends JpaRepository<Match, Long> {
    boolean existsByMatchDateAndMatchTimeAndTeamAAndTeamB(LocalDate matchDate, LocalTime matchTime, String teamA, String teamB);
}