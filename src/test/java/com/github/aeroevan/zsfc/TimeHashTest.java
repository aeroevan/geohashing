package com.github.aeroevan.zsfc;

import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test timehash encode/decode round trip.
 */
public class TimeHashTest {

    @Test
    public void compare() {
        Instant now = Instant.now();
        String hash = TimeHash.encode(now, 8);
        Instant test = TimeHash.decode(hash).getValue();
        Duration de = TimeHash.decode(hash).getError();
        long err = de.toMillis();
        assertTrue(Math.abs(now.toEpochMilli() - test.toEpochMilli()) <= err,
                now.toString() + ": " + test.toString() + " - " + de.toString());
    }

    @Test
    public void length() {
        Instant now = Instant.now();
        String hash1 = TimeHash.encode(now);
        String hash2 = TimeHash.encode(now, 12);
        assertEquals(hash1, hash2);
    }

    @Test
    public void sublength() {
        Instant now = Instant.now();
        String hash1 = TimeHash.encode(now, 8);
        String hash2 = TimeHash.encode(now, 12);
        assertEquals(hash1, hash2.substring(0, 8));
    }

}