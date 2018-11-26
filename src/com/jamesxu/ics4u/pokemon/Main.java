package com.jamesxu.ics4u.pokemon;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Utilities.Response r = engine.loadPokemon();
        System.out.println(r.message);
        if (!r.status) {
            return;
        }
        for (Pokemon a : engine.roster) {
            for (Pokemon b : engine.roster) {
                for (Pokemon.Attack attack : b.availableAttacks) {
                    System.out.println(b.attack(a, attack).message);
                    System.out.println(b);
                }
            }
        }
    }
}
