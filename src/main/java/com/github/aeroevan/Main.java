package com.github.aeroevan;

import java.time.Instant;

/**
 * Created by evan on 8/17/16.
 */
public class Main {
    public static void main(String[] args) {
        Instant now = Instant.now();
        String th = TimeHash.encode(now);
        System.out.println(now);
        System.out.println(th);
        System.out.println(TimeHash.decode(th).getValue());
        System.out.println(TimeHash.decode(th).getError().toNanos());
    }
}
