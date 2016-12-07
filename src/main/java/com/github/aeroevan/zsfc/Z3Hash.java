package com.github.aeroevan.zsfc;

import java.io.Serializable;
import java.util.BitSet;

/**
 * 3D Z-order curve hashing.
 */
public interface Z3Hash extends ZHash {
    static String encode(double a, double b, double c,
                         double minA, double maxA, double minB, double maxB,
                         double minC, double maxC) {
        return encode(a, b, c, DEFAULT_LENGTH, minA, maxA, minB, maxB, minC, maxC);
    }

    static String encode(double a, double b, double c, int length,
                         double minA, double maxA, double minB, double maxB,
                         double minC, double maxC) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be > 0");
        }
        int n = 5 * length;
        BitSet bs = new BitSet(n);
        int idx = 0;
        for (int i=0; i<n; i++) {
            if (idx % 3 == 0) {
                double mid = (minA + maxA) / 2d;
                if (a >= mid) {
                    bs.set(i);
                    minA = mid;
                } else {
                    maxA = mid;
                }
            } else if (idx % 3 == 1){
                double mid = (minB + maxB) / 2d;
                if (b >= mid) {
                    bs.set(i);
                    minB = mid;
                } else {
                    maxB = mid;
                }
            } else {
                double mid = (minC + maxC) / 2d;
                if (c >= mid) {
                    bs.set(i);
                    minC = mid;
                } else {
                    maxC = mid;
                }
            }
            idx += 1;
        }
        return ZHash.fromBitSetToString(bs, length);
    }

    static Z3 decode(String hash, double minA, double maxA, double minB, double maxB,
                     double minC, double maxC) {
        int idx = 0;
        for (Character c : hash.toCharArray()) {
            int cd = decodeMap.get(c);
            for (int i=0; i<5; i++) {
                int mask = 1 << (4 - i);
                if (idx % 3 == 0) {
                    double mid = (minA + maxA) / 2d;
                    if ((cd & mask) != 0) {
                        minA = mid;
                    } else {
                        maxA = mid;
                    }
                } else if (idx % 3 == 1) {
                    double mid = (minB + maxB) / 2d;
                    if ((cd & mask) != 0) {
                        minB = mid;
                    } else {
                        maxB = mid;
                    }
                } else {
                    double mid = (minC + maxC) / 2d;
                    if ((cd & mask) != 0) {
                        minC = mid;
                    } else {
                        maxC = mid;
                    }
                }
                idx += 1;
            }
        }
        return new Z3((minA + maxA) / 2d,
                (minB + maxB) / 2d,
                (minC + maxC) / 2d,
                maxA - minA,
                maxB - minB,
                maxC - minC);
    }

    class Z3 implements Serializable {
        final double a;
        final double b;
        final double c;
        final double da;
        final double db;
        final double dc;

        @Deprecated
        public Z3() {
            this.a = Double.NaN;
            this.b = Double.NaN;
            this.c = Double.NaN;
            this.da = Double.NaN;
            this.db = Double.NaN;
            this.dc = Double.NaN;
        }

        public Z3(double a, double b, double c,
                  double da, double db, double dc) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.da = da;
            this.db = db;
            this.dc = dc;
        }
    }
}
