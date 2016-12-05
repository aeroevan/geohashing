package com.github.aeroevan;

/**
 * Created by evan on 8/17/16.
 */
public class RangedTripleHash implements ZHash {
    private final static int NDIM = 3;
    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final double minZ;
    private final double maxZ;

    public RangedTripleHash(double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;
    }

    /*
    public long encodeToLong(double x, double y, double z) {
        return encodeToLong(x, y, z, DEFAULT_LENGTH);
    }

    public long encodeToLong(double x, double y, double z, int length) {
        long position = INITIAL_POSITION;
        long target = INITIAL_POSITION >>> (5 * length);
        long bits = 0;
        double minX = this.minX;
        double maxX = this.maxX;
        double minY = this.minY;
        double maxY = this.maxY;
        double minZ = this.minZ;
        double maxZ = this.maxZ;
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
            } else if (idx % NDIM == 1){
                double mid = (minY + maxY) / 2;
                if (y >= mid) {
                    bits |= position;
                    minY = mid;
                } else {
                    maxY = mid;
                }
            } else {
                double mid = (minZ + maxZ) / 2;
                if (z >= mid) {
                    bits |= position;
                    minZ = mid;
                } else {
                    maxZ = mid;
                }
            }
            idx += 1;
            position >>>= 1;
        }
        return bits | length;
    }
    */
}
