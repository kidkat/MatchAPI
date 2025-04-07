package com.betting.api.model;

import com.betting.api.dto.responses.MatchResponse;
import com.betting.api.model.enums.Sport;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @author kidkat
 */

@Entity
@Table(name = "match")
@Data
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Match ID", example = "1")
    private Long id;

    @Column(name = "description")
    @Schema(description = "Match description", example = "Barcelona vs Real Madrid")
    private String description;

    @Column(name = "match_date")
    @Schema(description = "Match date", example = "2025-04-08")
    private LocalDate matchDate;

    @Column(name = "match_time")
    @Schema(description = "Match time", example = "20:45")
    private LocalTime matchTime;

    @Column(name = "team_a")
    @Schema(description = "Team A name", example = "Barcelona")
    private String teamA;

    @Column(name = "team_b")
    @Schema(description = "Team B name", example = "Real Madrid")
    private String teamB;

    @Column(name = "sport")
    @Schema(description = "Sport type", example = "1")
    private Sport sport;

    @Schema(hidden = true)
    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchOdd> odds;

    public MatchResponse toMatchResponse(){
        MatchResponse matchResponse = new MatchResponse();
        matchResponse.setId(this.id);
        matchResponse.setDescription(this.description);
        matchResponse.setMatchDate(this.matchDate);
        matchResponse.setMatchTime(this.matchTime);
        matchResponse.setTeamA(this.teamA);
        matchResponse.setTeamB(this.teamB);
        matchResponse.setSport(this.sport);

        return matchResponse;
    }
}
