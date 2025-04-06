package com.betting.api.model;

import com.betting.api.dto.responses.MatchResponse;
import com.betting.api.model.enums.Sport;
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
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "match_date")
    private LocalDate matchDate;

    @Column(name = "match_time")
    private LocalTime matchTime;

    @Column(name = "team_a")
    private String teamA;

    @Column(name = "team_b")
    private String teamB;

    @Column(name = "sport")
    private Sport sport;

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
