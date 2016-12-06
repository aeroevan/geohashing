package com.github.aeroevan.zsfc;

import org.testng.annotations.Test;

import java.time.Duration;
import java.time.Instant;

import static org.testng.Assert.*;

/**
 * Created by evan on 12/5/16.
 */
public class GeoTemporalHashTest {

    @Test
    public void random() {
        double lat = (Math.random() - 0.5d) * 180d;
        double lon = (Math.random() - 0.5d) * 360d;
        Instant now = Instant.now();
        String hash = GeoTemporalHash.encode(lat, lon, now);
        GeoTemporalHash.LatLonTime llt = GeoTemporalHash.decode(hash);
        Instant test = llt.getTime();
        Duration de = llt.getTimeError();
        long err = de.toMillis();
        assertTrue(Math.abs(now.toEpochMilli() - test.toEpochMilli()) <= err,
                now.toString() + ": " + test.toString() + " - " + de.toString());
        assertTrue(Math.abs(lat - llt.getLatitude()) < llt.getLatitudeError(),
                "Decoded latitude outside of error bounds: " + lat + ", " + lon);
        assertTrue(Math.abs(lon - llt.getLongitude()) < llt.getLongitudeError(),
                "Decoded longitude outside of error bounds: " + lat + ", " + lon);
    }

}