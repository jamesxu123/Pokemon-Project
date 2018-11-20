package com.jamesxu.ics4u.pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Engine {
    public Response loadPokemon() {
        try {
            Scanner file = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
            while (file.hasNextLine()) {
                System.out.println(file.nextLine());
            }
        } catch (IOException e) {
            return new Response("Error: " + e, false);
        }
        return new Response("Pokemon Loaded Successfully", true);
    }
}
