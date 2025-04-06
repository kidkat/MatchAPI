package com.betting.api.model;

import com.betting.api.dto.responses.MatchOddsResponse;
import jakarta.persistence.*;
import lombok.Data;

/**
 * @author kidkat
 */

@Entity
@Table(name = "match_odds")
@Data
public class MatchOdd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specifier")
    private String specifier;

    @Column(name = "odd")
    private Double odd;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    public MatchOddsResponse toMatchOddsResponse() {
        MatchOddsResponse response = new MatchOddsResponse();

        response.setId(id);
        response.setSpecifier(specifier);
        response.setOdd(odd);
        response.setMatchId(match.getId());

        return response;
    }
}
