package com.betting.api.utils;

/**
 * @author kidkat
 */
public class Utils {
    public static long getStartTime() {
        return System.currentTimeMillis();
    }

    public static long getExecutionTime(long startTime) {
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
