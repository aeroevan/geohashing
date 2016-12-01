package com.github.aeroevan;

import java.util.BitSet;

import static com.github.aeroevan.Hasher.fromBitSetToString;
import static com.github.aeroevan.Hasher.fromLongToString;

/**
 * Created by evan on 8/17/16.
 */
public class Main {
    public static void main(String[] args) {
        RangedPairHash rp = new RangedPairHash(-180.0, 180.0, -90.0, 90.0);
        long tp = rp.encodeToLong(-84.4278640, 33.6366996);
        System.out.println(fromLongToString(tp));
        BitSet encode = rp.encode(-84.4278640, 33.6366996, 24);
        System.out.println(Hasher.fromBitSetToString(encode));
    }
    public static void main2(String[] args) {
        RangedHash r = new RangedHash(0.0, 1.0);
        long t = r.encodeToLong(0.1234);
        String s = fromLongToString(t);
        ReverseRangedHash rrh = r.decode(s);
        System.out.println(s);
        System.out.println(rrh.getValue() + " " + rrh.getError());

        RangedPairHash rp = new RangedPairHash(-180.0, 180.0, -90.0, 90.0);
        long tp = rp.encodeToLong(-84.4278640, 33.6366996);
        System.out.println(fromLongToString(tp));

        RangedTripleHash rt = new RangedTripleHash(-180.0, 180.0, -90.0, 90.0, 0.0, 100000.0);
        long tt = rt.encodeToLong(-84.4278640, 33.6366996, 25000.0);
        System.out.println(fromLongToString(tt));

        long tt2 = rt.encodeToLong(-84.4278640, 33.6366996, 25000.1);
        System.out.println(fromLongToString(tt2));

        long tt3 = rt.encodeToLong(-84.4278640, 33.6367096, 25000.0);
        System.out.println(fromLongToString(tt3));

        long tt4 = rt.encodeToLong(-84.4278740, 33.6366996, 25000.0);
        System.out.println(fromLongToString(tt4));
    }

}
