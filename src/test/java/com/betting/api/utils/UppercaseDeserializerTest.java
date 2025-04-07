package com.betting.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kidkat
 */
class UppercaseDeserializerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void convertToUpperTest() throws Exception {
        String json = "{\"team\": \"chelsea\"}";

        TestDTO dto = objectMapper.readValue(json, TestDTO.class);

        assertThat(dto.getTeam()).isEqualTo("CHELSEA");
    }

    @Test
    void handleNullTest() throws Exception {
        String json = "{\"team\": null}";

        TestDTO dto = objectMapper.readValue(json, TestDTO.class);

        assertThat(dto.getTeam()).isNull();
    }
}

class TestDTO {
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String team;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}