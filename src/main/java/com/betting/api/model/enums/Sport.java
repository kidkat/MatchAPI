package com.betting.api.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author kidkat
 */

@Getter
public enum Sport {
    FOOTBALL(1),
    BASKETBALL(2);

    private final int value;

    Sport(int value) {
        this.value = value;
    }

    @JsonValue
    public int getValue() {
        return value;
    }

    @JsonCreator
    public static Sport fromValue(int value) {
        for (Sport sport : Sport.values()) {
            if (sport.getValue() == value) {
                return sport;
            }
        }
        throw new IllegalArgumentException("Invalid sport code: " + value);
    }
}
