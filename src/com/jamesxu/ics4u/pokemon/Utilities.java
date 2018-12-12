/*
James Xu
ICS4U Pokemon Project

Utilities class
    - Contains helpful functions that are used throughout the program
    - Response class
        - Used to return a standardized String message and a custom String message for display
*/

package com.jamesxu.ics4u.pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Utilities {
    public static boolean coinFlip() {
        //Simulate a 50/50 chance
        return randInt(0, 2) == 1;
    }

    public static int randInt(int low, int high) {
        //Returns a random integer low <= num < high
        return (int) (Math.random() * (high - low) + low);
    }

    public static int getInputFromRange(int low, int high) {
        //Gets an integer from the user where low <= num < high
        boolean done = false;
        int result = 0;
        Scanner input = new Scanner(System.in);
        while (!done) {
            try {
                int temp = Integer.parseInt(input.nextLine());
                if (temp < high && temp >= low) { //Check if number is within the range
                    result = temp;
                    done = true;
                } else {
                    System.out.println("Invalid number");
                }
            } catch (Exception e) { //In case user enters an incompatbile value
                System.out.println("Invalid number");
            }
        }
        return result;
    }

    public static void displayPokemon(Pokemon pokemon) {
        //Displays Pokemon data in a nice format
        String output = "";
        output += String.format("--------%10s---------\n", pokemon.name);
        output += String.format("Health: %19d\n", pokemon.getHp());
        output += String.format("Status: %19s\n", pokemon.getStatus());
        output += String.format("Energy: %19d\n", pokemon.getEnergy());
        output += String.format("Type: %21s\n", pokemon
                .getType()
                .equals(" ") ? "None" : pokemon
                .getResistance());
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
        //Outputs a given file
        BufferedReader file = new BufferedReader(new FileReader(fileName));
        while (file.ready()) {
            System.out.println(file.readLine());
        }
    }

    public static class Response {
        //Class used to return a standardized status and a customized message
        public final String status;
        public final String message;

        Response(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
