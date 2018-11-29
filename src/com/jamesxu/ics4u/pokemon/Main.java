package com.jamesxu.ics4u.pokemon;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        OutputStream out = new BufferedOutputStream(System.out);
        Utilities.Response r = PokemonArena.loadPokemon();
        System.out.println(r.message);
        if (!r.status) {
            return;
        }
        for (Pokemon a : PokemonArena.roster) {
            for (Pokemon b : PokemonArena.roster) {
                for (Pokemon.Attack attack : b.availableAttacks) {
                    out.write((b.attack(a, attack).message + "\n").getBytes());
                    out.write((b.toString() + "\n").getBytes());
                }
                out.flush();
            }
        }
    }
}
