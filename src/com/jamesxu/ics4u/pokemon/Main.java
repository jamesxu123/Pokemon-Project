package com.jamesxu.ics4u.pokemon;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Response r = engine.loadPokemon();
        System.out.println(r.message);
        if (!r.status) {
            return;
        }
    }
}
