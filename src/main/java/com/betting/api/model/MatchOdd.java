package com.betting.api.model;

import com.betting.api.dto.responses.MatchOddsResponse;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Match odd ID", example = "1")
    private Long id;

    @Column(name = "specifier")
    @Schema(description = "Match odd specifier", example = "X")
    private String specifier;

    @Column(name = "odd")
    @Schema(description = "Match odd", example = "2.25")
    private Double odd;

    @Schema(hidden = true)
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
