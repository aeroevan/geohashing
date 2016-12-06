package com.github.aeroevan.zsfc;

import java.time.Duration;
import java.time.Instant;

/**
 * Created by evan on 12/5/16.
 */
public class GeoTemporalHash implements Z3Hash {
    private static final double minLon = -180d;
    private static final double maxLon = 180d;
    private static final double minLat = -90d;
    private static final double maxLat = 90d;
    private static final double minTime = 0d;
    private static final double maxTime = 4039372800.0d;

    public static String encode(double latitude, double longitude, Instant time) {
        return encode(latitude, longitude, time, DEFAULT_LENGTH);
    }

    public static String encode(double latitude, double longitude, Instant time, int length) {
        // Truncates to ms...
        final double seconds = time.toEpochMilli() / 1000.0d;
        return Z3Hash.encode(longitude, latitude, seconds, length,
                minLon, maxLon, minLat, maxLat, minTime, maxTime);
    }

    public static String encode(double latitude, double longitude, double timeSec) {
        return encode(latitude, longitude, timeSec, DEFAULT_LENGTH);
    }

    public static String encode(double latitude, double longitude, double timeSec, int length) {
        if (timeSec < minTime || timeSec > maxTime) {
            throw new IllegalArgumentException(
                    "Time must be between 0 and " + maxTime + " seonds since epoch.");
        }
        if (latitude < minLat || latitude > maxLat) {
            throw new IllegalArgumentException(
                    "Latitude (" + latitude + ") must be between " + minLat + " and " + maxLat + " degrees.");
        }
        if (longitude < minLon || longitude > maxLon) {
            throw new IllegalArgumentException(
                    "Longitude (" + longitude + ") must be between " + minLon + " and " + maxLon + " degrees.");
        }
        return Z3Hash.encode(longitude, latitude, timeSec, length,
                minLon, maxLon, minLat, maxLat, minTime, maxTime);
    }

    public static LatLonTime decode(String hash) {
        return new LatLonTime(Z3Hash.decode(hash, minLon, maxLon, minLat, maxLat, minTime, maxTime));
    }

    public static class LatLonTime extends Z3 {
        public LatLonTime(Z3 z3) {
            super(z3.a, z3.b, z3.c, z3.da, z3.db, z3.dc);
        }
        public LatLonTime(double latitude, double longitude, double time,
                          double dLat, double dLon, double dTime) {
            super(longitude, latitude, time, dLon, dLat, dTime);
        }

        public double getLatitude() {
            return b;
        }

        public double getLongitude() {
            return a;
        }

        public Instant getTime() {
            long sec = (long)Math.floor(c);
            long ns = (long)Math.floor((c - sec) * 1e9);
            return Instant.ofEpochSecond(sec, ns);
        }

        public Duration getTimeError() {
            long sec = (long)Math.floor(dc);
            long ns = (long)Math.floor((dc - sec) * 1e9);
            // Minimum of 1 ms error due to timestamp truncation
            // (64 bit long + 32 bit int -> 64 bit double)
            if (sec == 0 && ns < 1000000) {
                ns = 1000000;
            }
            return Duration.ofSeconds(sec, ns);
        }

        public double getLatitudeError() {
            return db;
        }

        public double getLongitudeError() {
            return da;
        }
    }
}
