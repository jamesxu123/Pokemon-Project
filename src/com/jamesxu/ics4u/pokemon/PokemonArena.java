package com.jamesxu.ics4u.pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PokemonArena {
    static final ArrayList<Pokemon> roster = new ArrayList<>();

    static void loadPokemon() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("pokemon.txt"));
            int num = Integer.parseInt(file.readLine());
            while (file.ready()) {
                String line = file.readLine();
                roster.add(new Pokemon(line));

            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args) throws IOException {
        loadPokemon();
        Utilities.displayFile("uiText/opening.txt");
        Player player = new Player("Player");
        player.chooseRoster();
        Bot bot = new Bot("Bot", roster.get(Utilities.getInputFromRange(0, roster.size())));
        System.out.println(player.roster.size());

    }
}
