package com.github.aeroevan;

import java.util.AbstractMap.SimpleEntry;
import java.util.BitSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by evan on 8/17/16.
 */
public interface Hasher {
    int DEFAULT_LENGTH = 12;
    char[] BASE32 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    Map<Character, Integer> decodeMap = IntStream.range(0, BASE32.length)
            .mapToObj(i -> new SimpleEntry<>(BASE32[i], i))
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
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

    static String fromBitSetToString(BitSet bs) {
        int idx=0;
        StringBuilder geohash = new StringBuilder(bs.length() / 5);
        for (int i=0; i<bs.size(); i++) {
            int rnd = i % 5;
            if (rnd == 0 && (i > 0)) {
                geohash.append(BASE32[idx]);
                idx = 0;
            }
            if (bs.get(i)) {
                idx += 1 << (4 - rnd);
            }
        }
        return geohash.toString();
    }
}
