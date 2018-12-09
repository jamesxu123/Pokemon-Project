package com.jamesxu.ics4u.pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Utilities {
    public static boolean coinFlip() {
        return randInt(0, 10) % 2 == 0;
    }

    public static int randInt(int low, int high) {
        return (int) (Math.random() * (high - low) + low);
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

    public static void displayPokemon(Pokemon pokemon) {
        String output = "";
        output += String.format("--------%10s--------\n", pokemon.name);
        output += String.format("Health: %19d\n", pokemon.getHp());
        output += String.format("Status: %19s\n", pokemon.getStatus());
        output += String.format("Energy: %19d\n", pokemon.getEnergy());
        output += String.format("Resistance: %15s\n", pokemon
                .getResistance()
                .equals(" ") ? "None" : pokemon
                .getResistance());
        output += String.format("Weakness: %17s\n", pokemon
                .getWeakness()
                .equals(" ") ? "None" : pokemon
                .getWeakness());
        output += "-".repeat(27) + "\n";
        System.out.print(output);
    }

    public static void displayFile(String fileName) throws IOException {
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        while (file.ready()) {
            System.out.println(file.readLine());
        }
    }

    public static class Response {
        public final String status;
        public final String message;

        Response(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
