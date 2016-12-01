package com.github.aeroevan;

/**
 * Created by evan on 9/9/16.
 */
public class ReverseRangedHash {
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
