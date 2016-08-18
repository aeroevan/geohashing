package com.github.aeroevan;

/**
 * Created by evan on 8/17/16.
 */
public class RangedHash implements Hasher {

    private final double min;
    private final double max;

    public RangedHash(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public long encodeToLong(double x) {
        return encodeToLong(x, DEFAULT_LENGTH);
    }

    public long encodeToLong(double x, int length) {
        long position = INITIAL_POSITION;
        long target = INITIAL_POSITION >>> (5 * length);
        long bits = 0;
        double min = this.min;
        double max = this.max;
        while (position != target) {
            double mid = (min + max) / 2;
            if (x >= mid) {
                bits |= position;
                min = mid;
            } else {
                max = mid;
            }
            position >>>= 1;
        }
        return bits | length;
    }
}
