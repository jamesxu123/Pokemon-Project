package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public class Player extends Actor {

    Player(String name, ArrayList<Pokemon> roster) {
        super(name);
        for (Pokemon p : roster) {
            addPokemon(new Pokemon(p));
        }
    }

    @Override
    public Utilities.Response turnDecision() {
        return null;
    }

    @Override
    public Utilities.Response attack(Pokemon p) {
        return null;
    }

    public Utilities.Response retreat() {
        return null;
    }

    @Override
    public Pokemon.Attack attackDecision() {
        return null;
    }

    @Override
    public Utilities.Response choosePokemon() {
        return null;
    }
}
