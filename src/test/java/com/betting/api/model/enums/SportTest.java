package com.betting.api.model.enums;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * @author kidkat
 */
class SportTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void returnEnumFromValueTest() {
        assertThat(Sport.fromValue(1)).isEqualTo(Sport.FOOTBALL);
        assertThat(Sport.fromValue(2)).isEqualTo(Sport.BASKETBALL);
    }

    @Test
    void throwForInvalidValueTest() {
        assertThatThrownBy(() -> Sport.fromValue(999))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid sport code");
    }

    @Test
    void serializeEnumAsIntTest() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(Sport.BASKETBALL);
        assertThat(json).isEqualTo("2");
    }

    @Test
    void deserializeEnumFromIntTest() throws JsonProcessingException {
        Sport sport = objectMapper.readValue("1", Sport.class);
        assertThat(sport).isEqualTo(Sport.FOOTBALL);
    }
}