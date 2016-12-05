package com.github.aeroevan;

import java.io.Serializable;
import java.util.BitSet;

/**
 * Hash a value along a 1 dimensional range.
 */
public interface Z1Hash extends ZHash {
    /**
     * Encode value using DEFAULT_LENGTH
     * @param value
     * @return hash
     */
    static String encode(double value, double min, double max) {
        return encode(value, DEFAULT_LENGTH, min, max);
    }

    /**
     * ENcode value using specified length
     * @param value
     * @param length
     * @return hash
     */
    static String encode(double value, int length, double min, double max) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be > 0");
        }
        final int n = 5 * length;
        final BitSet bs = new BitSet(n);
        for (int i=0; i<n; i++) {
            double mid = (min + max) / 2d;
            if (value >= mid) {
                bs.set(i);
                min = mid;
            } else {
                max = mid;
            }
        }
        return ZHash.fromBitSetToString(bs, length);
    }

    static Z1 decode(String hash, double min, double max) {
        for (Character c : hash.toCharArray()) {
            int cd = decodeMap.get(c);
            for (int i=0; i<5; i++) {
                int mask = 1 << (4 - i);
                double mid = (min + max) / 2d;
                if ((cd & mask) != 0) {
                    min = mid;
                } else {
                    max = mid;
                }
            }
        }
        return new Z1((min + max) / 2d, max - min);
    }

    class Z1 implements Serializable {
        final double value;
        final double error;

        @Deprecated
        public Z1() {
            this.value = Double.NaN;
            this.error = Double.NaN;
        }

        public Z1(double value, double error) {
            this.value = value;
            this.error = error;
        }
    }
}
