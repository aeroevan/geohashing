package com.github.aeroevan;

import java.time.Duration;
import java.time.Instant;

/**
 * Builds a timehash
 * <p />
 * see https://github.com/abeusher/timehash
 */
public class TimeHash implements Z1Hash {
    private static final double min = 0d;
    private static final double max = 4039372800.0d;

    public static String encode(double seconds) {
        return encode(seconds, DEFAULT_LENGTH);
    }

    /**
     * Encode seconds since epoch to a timehash string.
     * @param seconds
     * @param length
     * @return
     */
    public static String encode(double seconds, int length) {
        return Z1Hash.encode(seconds, length, min, max);
    }

    public static String encode(Instant time) {
        return encode(time, DEFAULT_LENGTH);
    }

    public static String encode(Instant time, int length) {
        // Truncates to ms...
        final double seconds = time.toEpochMilli() / 1000.0d;
        if (seconds < min || seconds > max) {
            throw new IllegalArgumentException(
                    "Time must be between 0 and " + max + " seonds since epoch.");
        }
        return encode(seconds, length);
    }

    public static Time decode(String hash) {
        return new Time(Z1Hash.decode(hash, TimeHash.min, TimeHash.max));
    }

    static class Time extends Z1 {

        Time(double seconds, double error) {
            super(seconds, error);
        }

        Time(Z1 z1) {
            super(z1.value, z1.error);
        }

        public Instant getValue() {
            long sec = (long)Math.floor(value);
            long ns = (long)Math.floor((value - sec) * 1e9);
            return Instant.ofEpochSecond(sec, ns);
        }

        public Duration getError() {
            long sec = (long)Math.floor(error);
            long ns = (long)Math.floor((error - sec) * 1e9);
            // Minimum of 1 ms error due to timestamp truncation
            // (64 bit long + 32 bit int -> 64 bit double)
            if (sec == 0 && ns < 1000000) {
                ns = 1000000;
            }
            return Duration.ofSeconds(sec, ns);
        }
    }
}
