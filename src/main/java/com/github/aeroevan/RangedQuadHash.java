package com.github.aeroevan;

import com.github.aeroevan.zsfc.ZHash;

/**
 * Created by evan on 8/31/16.
 */
public class RangedQuadHash implements ZHash {
    private final static int NDIM = 4;
    private final double minA;
    private final double maxA;
    private final double minB;
    private final double maxB;
    private final double minC;
    private final double maxC;
    private final double minD;
    private final double maxD;

    public RangedQuadHash(double minA, double maxA, double minB, double maxB,
                          double minC, double maxC, double minD, double maxD) {
        this.minA = minA;
        this.maxA = maxA;
        this.minB = minB;
        this.maxB = maxB;
        this.minC = minC;
        this.maxC = maxC;
        this.minD = minD;
        this.maxD = maxD;
    }

    /*
    public long encodeToLong(double a, double b, double c, double d) {
        return encodeToLong(a, b, c, d, DEFAULT_LENGTH);
    }

    public long encodeToLong(double a, double b, double c, double d, int length) {
        long position = INITIAL_POSITION;
        long target = INITIAL_POSITION >>> (5 * length);
        long bits = 0;
        double minA = this.minA;
        double maxA = this.maxA;
        double minB = this.minB;
        double maxB = this.maxB;
        double minC = this.minC;
        double maxC = this.maxC;
        double minD = this.minD;
        double maxD = this.maxD;
        int idx = 0;
        while (position != target) {
            if (idx % NDIM == 0) {
                double mid = (minA + maxA) / 2;
                if (a >= mid) {
                    bits |= position;
                    minA = mid;
                } else {
                    maxA = mid;
                }
            } else if (idx % NDIM == 1){
                double mid = (minB + maxB) / 2;
                if (b >= mid) {
                    bits |= position;
                    minB = mid;
                } else {
                    maxB = mid;
                }
            } else if (idx % NDIM == 2){
                double mid = (minC + maxC) / 2;
                if (c >= mid) {
                    bits |= position;
                    minC = mid;
                } else {
                    maxC = mid;
                }
            } else {
                double mid = (minD + maxD) / 2;
                if (d >= mid) {
                    bits |= position;
                    minD = mid;
                } else {
                    maxD = mid;
                }
            }
            idx += 1;
            position >>>= 1;
        }
        return bits | length;
    }
    */
}
