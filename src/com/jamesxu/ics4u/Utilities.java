package com.jamesxu.ics4u;

public class Utilities {
    public static boolean coinFlip() {
        return randint(0, 10) % 2 == 0;
    }

    public static int randint(int lowerBound, int upperBound) {
        return (int) (Math.random() * (upperBound - lowerBound)) + lowerBound;
    }
}
