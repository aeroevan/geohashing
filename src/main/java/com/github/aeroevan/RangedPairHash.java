package com.github.aeroevan;

import java.util.BitSet;

/**
 * Created by evan on 8/17/16.
 */
public class RangedPairHash implements Hasher {
    private final static int NDIM = 2;

    private static final RangedPairHash geohash = new RangedPairHash(-180.0, 180.0, -90.0, 90.0);
    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;

    public RangedPairHash(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public static RangedPairHash geohash() {
        return geohash;
    }

    public BitSet encode(double x, double y) {
        return encode(x, y, DEFAULT_LENGTH);
    }

    public BitSet encode(double x, double y, int length) {
        int n = 5 * length;
        BitSet bs = new BitSet(n);
        double minX = this.minX;
        double maxX = this.maxX;
        double minY = this.minY;
        double maxY = this.maxY;
        int idx = 0;
        for (int i=0; i<n; i++) {
            if (idx % NDIM == 0) {
                double mid = (minX + maxX) / 2;
                if (x >= mid) {
                    bs.set(i);
                    minX = mid;
                } else {
                    maxX = mid;
                }
            } else {
                double mid = (minY + maxY) / 2;
                if (y >= mid) {
                    bs.set(i);
                    minY = mid;
                } else {
                    maxY = mid;
                }
            }
            idx += 1;
        }
        return bs;
    }

    public long encodeToLong(double x, double y) {
        return encodeToLong(x, y, DEFAULT_LENGTH);
    }

    public long encodeToLong(double x, double y, int length) {
        long position = INITIAL_POSITION;
        long target = INITIAL_POSITION >>> (5 * length);
        long bits = 0;
        double minX = this.minX;
        double maxX = this.maxX;
        double minY = this.minY;
        double maxY = this.maxY;
        int idx = 0;
        while (position != target) {
            if (idx % NDIM == 0) {
                double mid = (minX + maxX) / 2;
                if (x >= mid) {
                    bits |= position;
                    minX = mid;
                } else {
                    maxX = mid;
                }
            } else {
                double mid = (minY + maxY) / 2;
                if (y >= mid) {
                    bits |= position;
                    minY = mid;
                } else {
                    maxY = mid;
                }
            }
            idx += 1;
            position >>>= 1;
        }
        return bits | length;
    }
}
