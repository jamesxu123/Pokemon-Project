package com.jamesxu.ics4u.pokemon;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        OutputStream out = new BufferedOutputStream(System.out);
        Utilities.Response r = PokemonArena.loadPokemon();
        System.out.println(r.message);
        if (!r.status) {
            return;
        }
        ArrayList<Pokemon> temp = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            temp.add(new Pokemon(PokemonArena.roster.get(i)));
        }
        Player james = new Player("James", temp);
        Bot bot = new Bot("bot", temp);
        james.chooseActive();
        bot.chooseActive();
        System.out.println(bot.attack(james.active).message);
        System.out.println(bot.active);
        System.out.print(james.active);
    }
}
