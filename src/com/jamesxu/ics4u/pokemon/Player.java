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
    public Response attack() {
        return null;
    }

    @Override
    public Response retreat() {
        return null;
    }

    @Override
    public Response pass() {
        return null;
    }

    @Override
    public Response choosePokemon() {
        return null;
    }
}
