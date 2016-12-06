package com.github.aeroevan.zsfc;

import java.io.Serializable;
import java.util.BitSet;

/**
 * 2 dimensional Z-order curve based hash.
 */
public interface Z2Hash extends ZHash {
    static String encode(double x, double y, double minX, double maxX, double minY, double maxY) {
        return encode(x, y, DEFAULT_LENGTH, minX, maxX, minY, maxY);
    }

    static String encode(double x, double y, int length, double minX, double maxX, double minY, double maxY) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be > 0");
        }
        int n = 5 * length;
        BitSet bs = new BitSet(n);
        int idx = 0;
        for (int i=0; i<n; i++) {
            if (idx % 2 == 0) {
                double mid = (minX + maxX) / 2d;
                if (x >= mid) {
                    bs.set(i);
                    minX = mid;
                } else {
                    maxX = mid;
                }
            } else {
                double mid = (minY + maxY) / 2d;
                if (y >= mid) {
                    bs.set(i);
                    minY = mid;
                } else {
                    maxY = mid;
                }
            }
            idx += 1;
        }
        return ZHash.fromBitSetToString(bs, length);
    }

    static Z2 decode(String hash, double minX, double maxX, double minY, double maxY) {
        int idx = 0;
        for (Character c : hash.toCharArray()) {
            int cd = decodeMap.get(c);
            for (int i=0; i<5; i++) {
                int mask = 1 << (4 - i);
                if (idx % 2 == 0) {
                    double mid = (minX + maxX) / 2d;
                    if ((cd & mask) != 0) {
                        minX = mid;
                    } else {
                        maxX = mid;
                    }
                } else {
                    double mid = (minY + maxY) / 2d;
                    if ((cd & mask) != 0) {
                        minY = mid;
                    } else {
                        maxY = mid;
                    }
                }
                idx += 1;
            }
        }
        return new Z2((minX + maxX) / 2d, (minY + maxY) / 2d,
                maxX - minX, maxY - minY);
    }

    class Z2 implements Serializable {
        final double x;
        final double y;
        final double dx;
        final double dy;

        @Deprecated
        public Z2() {
            this.x = Double.NaN;
            this.y = Double.NaN;
            this.dx = Double.NaN;
            this.dy = Double.NaN;
        }

        public Z2(double x, double y, double dx, double dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }
    }
}
