package com.jamesxu.ics4u.pokemon;

import java.io.*;
import java.util.Scanner;

public class Utilities {
    public static boolean coinFlip() {
        return randInt(0, 10) % 2 == 0;
    }

    public static int randInt(int low, int high) {
        return (int) (Math.random() * (high - low) + low);
    }

    public static boolean withinRange(int num, int low, int high) {
        return num < high && num >= low;
    }

    public static int getInputFromRange(int low, int high) {
        boolean done = false;
        int result = 0;
        Scanner input = new Scanner(System.in);
        while (!done) {
            try {
                int temp = Integer.parseInt(input.nextLine());
                if (temp < high && temp >= low) {
                    result = temp;
                    done = true;
                } else {
                    System.out.println("Invalid number");
                }
            } catch (Exception e) {
                System.out.println("Invalid number");
            }
        }
        return result;
    }

    public static void displayFile(String fileName) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        while (file.ready()) {
            System.out.println(file.readLine());
        }
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
