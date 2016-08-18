package com.github.aeroevan;

import com.github.aeroevan.RangedHash;
import com.github.aeroevan.RangedPairHash;
import com.github.aeroevan.RangedTripleHash;

import static com.github.aeroevan.Hasher.fromLongToString;

/**
 * Created by evan on 8/17/16.
 */
public class Main {
    public static void main(String[] args) {
        RangedHash r = new RangedHash(0.0, 1.0);
        long t = r.encodeToLong(0.1234);
        System.out.println(fromLongToString(t));

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
