package com.jamesxu.ics4u;

class Utilities {
    static boolean coinFlip() {
        return randint(0, 10) % 2 == 0;
    }

    static int randint(int low, int high) {
        return (int) (Math.random() * (high - low + 1) + low);
    }
}
