package com.github.aeroevan;

/**
 * Created by evan on 8/17/16.
 */
public class RangedHash implements Hasher {

    private static final RangedHash timehash = new RangedHash(0.0, 4039372800.0);
    private final double min;
    private final double max;

    public RangedHash(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public static RangedHash timehash() {
        return timehash;
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

    public ReverseRangedHash decode(String hash) {
        double min = this.min;
        double max = this.max;
        for (Character c : hash.toCharArray()) {
            int cd = decodeMap.get(c);
            for (int i=0; i<5; i++) {
                int mask = 1 << (4 - i);
                double mid = (min + max) / 2.0;
                if ((cd & mask) != 0) {
                    min = mid;
                } else {
                    max = mid;
                }
            }
        }
        return new ReverseRangedHash((min + max) / 2.0, max - min);
    }

    public static class ReverseRangedHash {
        private final double value;
        private final double error;
        public ReverseRangedHash(double value, double error) {
            this.value = value;
            this.error = error;
        }

        public double getValue() {
            return value;
        }

        public double getError() {
            return error;
        }
    }
}
