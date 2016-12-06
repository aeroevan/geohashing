package com.github.aeroevan;

import com.github.aeroevan.zsfc.ZHash;

import java.util.BitSet;

/**
 * Created by evan on 8/17/16.
 */
public class RangedHash implements ZHash {

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

    public BitSet encode(double x) {
        return encode(x, DEFAULT_LENGTH);
    }

    public BitSet encode(double x, int length) {
        int n = 5 * length;
        BitSet bs = new BitSet(n);
        double min = this.min;
        double max = this.max;
        for (int i=0; i<n; i++) {
            double mid = (min + max) / 2.0d;
            if (x >= mid) {
                bs.set(i);
                min = mid;
            } else {
                max = mid;
            }
            System.out.println(mid);
        }
        return bs;
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

}
