package com.betting.api.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author kidkat
 */
class UtilsTest {

    @Test
    void currentTimeTest() {
        long before = System.currentTimeMillis();
        long result = Utils.getStartTime();
        long after = System.currentTimeMillis();

        assertThat(result).isBetween(before, after);
    }

    @Test
    void differentTimeTest() throws InterruptedException {
        long start = Utils.getStartTime();

        Thread.sleep(10);

        long execTime = Utils.getExecutionTime(start);

        assertThat(execTime).isGreaterThanOrEqualTo(10);
        assertThat(execTime).isLessThan(100); // запас
    }
}