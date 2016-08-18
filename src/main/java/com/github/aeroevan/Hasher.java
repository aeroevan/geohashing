package com.github.aeroevan;

/**
 * Created by evan on 8/17/16.
 */
public interface Hasher {
    int DEFAULT_LENGTH = 12;
    char[] BASE32 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    long INITIAL_POSITION = 0x8000000000000000L;

    static String fromLongToString(long hash) {
        int length = (int) (hash & 0xf);
        if (length > 12 || length < 1)
            throw new IllegalArgumentException("invalid long geohash " + hash);
        StringBuilder geohash = new StringBuilder(length);
        for (int pos = 0; pos < length; pos++) {
            geohash.append(BASE32[(int) (hash >>> 59)]);
            hash <<= 5;
        }
        return geohash.toString();
    }
}
