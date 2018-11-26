package com.jamesxu.ics4u.pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Engine {
    ArrayList<Pokemon> roster = new ArrayList<>();

    public Utilities.Response loadPokemon() {
        try {
            Scanner file = new Scanner(new BufferedReader(new FileReader("pokemon.txt")));
            int num = Integer.parseInt(file.nextLine());
            while (file.hasNextLine()) {
                String line = file.nextLine();
                this.roster.add(new Pokemon(line));

            }
        } catch (IOException e) {
            return new Utilities.Response("Error: " + e, false);
        }
        return new Utilities.Response("Pokemon Loaded Successfully", true);
    }
}
