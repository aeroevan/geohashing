package com.github.aeroevan.zsfc;

/**
 * Geohashing with more precision.
 *
 * 
 */
public class Geohash implements Z2Hash {
    private static final double minLon = -180d;
    private static final double maxLon = 180d;
    private static final double minLat = -90d;
    private static final double maxLat = 90d;

    public static String encode(double latitude, double longitude) {
        return encode(latitude, longitude, DEFAULT_LENGTH);
    }

    public static String encode(double latitude, double longitude, int length) {
        if (latitude < minLat || latitude > maxLat) {
            throw new IllegalArgumentException(
                    "Latitude (" + latitude + ") must be between " + minLat + " and " + maxLat + " degrees.");
        }
        if (longitude < minLon || longitude > maxLon) {
            throw new IllegalArgumentException(
                    "Longitude (" + longitude + ") must be between " + minLon + " and " + maxLon + " degrees.");
        }
        return Z2Hash.encode(longitude, latitude, length, minLon, maxLon, minLat, maxLat);
    }

    public static LatLon decode(String geohash) {
        return new LatLon(Z2Hash.decode(geohash, minLon, maxLon, minLat, maxLat));
    }

    public static class LatLon extends Z2 {
        public LatLon(double latitude, double longitude, double dLat, double dLon) {
            super(longitude, latitude, dLon, dLat);
        }

        public LatLon(Z2 z2) {
            super(z2.x, z2.y, z2.dx, z2.dy);
        }

        public double getLatitude() {
            return y;
        }

        public double getLongitude() {
            return x;
        }

        public double getLatitudeError() {
            return dy;
        }

        public double getLongitudeError() {
            return dx;
        }
    }
}
