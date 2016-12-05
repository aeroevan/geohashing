package com.github.aeroevan;

import java.util.AbstractMap.SimpleEntry;
import java.util.BitSet;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Base interface for all Z-order curve hashes
 */
public interface ZHash {
    int DEFAULT_LENGTH = 12;
    char[] BASE32 = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    Map<Character, Integer> decodeMap = IntStream.range(0, BASE32.length)
            .mapToObj(i -> new SimpleEntry<>(BASE32[i], i))
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

    static String fromBitSetToString(BitSet bs, int length) {
        if ((5 * length) < bs.length()) {
            throw new IllegalArgumentException("Bitset has more precision than requested length...");
        }
        StringBuilder geohash = new StringBuilder(length);
        for (int i=0; i<length; i++) {
            int idx = 0;
            for (int j=0; j<5; j++) {
                if (bs.get(5*i + j)) {
                    idx += 1 << (4 - j);
                }
            }
            geohash.append(BASE32[idx]);
        }
        return geohash.toString();
    }
}
