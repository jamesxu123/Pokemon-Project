package com.jamesxu.ics4u.pokemon;

class Utilities {
    static boolean coinFlip() {
        return randint(0, 10) % 2 == 0;
    }

    static int randint(int low, int high) {
        return (int) (Math.random() * (high - low + 1) + low);
    }

    static int getRange(int low, int high) {
        return 0;
    }

    public static class Response {
        public final String message;
        public final boolean status;

        Response(String message, boolean status) {
            this.message = message;
            this.status = status;
        }
    }
}
