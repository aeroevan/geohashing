package com.github.aeroevan.zsfc;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Test Geohash implementation
 */
public class GeohashTest {

    @Test
    public void testGeohash() {
        double lat = (Math.random() - 0.5d) * 180d;
        double lon = (Math.random() - 0.5d) * 360d;
        String geohash = Geohash.encode(lat ,lon);
        // Compare against other geohash implementation.
        ch.hsr.geohash.GeoHash hash = ch.hsr.geohash.GeoHash.withCharacterPrecision(lat, lon, 12);
        assertEquals(geohash, hash.toBase32(), geohash + ": " + hash.toBase32());
    }

    @Test
    public void testGeohashDecode() {
        double lat = (Math.random() - 0.5d) * 180d;
        double lon = (Math.random() - 0.5d) * 360d;
        String geohash = Geohash.encode(lat ,lon, 8);
        Geohash.LatLon ll = Geohash.decode(geohash);
        assertTrue(Math.abs(lat - ll.getLatitude()) < ll.getLatitudeError(),
                "Decoded latitude outside of error bounds: " + lat + ", " + lon);
        assertTrue(Math.abs(lon - ll.getLongitude()) < ll.getLongitudeError(),
                "Decoded longitude outside of error bounds: " + lat + ", " + lon);
    }

}