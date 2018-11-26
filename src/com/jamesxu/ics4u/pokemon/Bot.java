package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public class Bot extends Actor {

    Bot(String name, ArrayList<Pokemon> roster) {
        super(name);
        for (Pokemon p : roster) {
            addPokemon(new Pokemon(p));
        }
    }

    @Override
    public Utilities.Response attack() {
        return null;
    }

    @Override
    public Utilities.Response retreat() {
        return null;
    }

    @Override
    public Utilities.Response pass() {
        return null;
    }

    @Override
    public Utilities.Response choosePokemon() {
        return null;
    }
}
