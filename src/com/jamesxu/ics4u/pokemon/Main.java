package com.jamesxu.ics4u.pokemon;

public class Main {
    public static void main(String[] args) {
        Utilities.Response r = PokemonArena.loadPokemon();
        System.out.println(r.message);
        if (!r.status) {
            return;
        }
        for (Pokemon a : PokemonArena.roster) {
            for (Pokemon b : PokemonArena.roster) {
                for (Pokemon.Attack attack : b.availableAttacks) {
                    System.out.println(b.attack(a, attack).message);
                    System.out.println(b);
                }
            }
        }
    }
}
