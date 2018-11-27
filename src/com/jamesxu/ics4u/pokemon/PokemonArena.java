package com.jamesxu.ics4u.pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PokemonArena {
    static ArrayList<Pokemon> roster = new ArrayList<>();

    public static Utilities.Response loadPokemon() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("pokemon.txt"));
            int num = Integer.parseInt(file.readLine());
            while (file.ready()) {
                String line = file.readLine();
                roster.add(new Pokemon(line));

            }
        } catch (IOException e) {
            return new Utilities.Response("Error: " + e, false);
        }
        return new Utilities.Response("Pokemon Loaded Successfully", true);
    }
}
