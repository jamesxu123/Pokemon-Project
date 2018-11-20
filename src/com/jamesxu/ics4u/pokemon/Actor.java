package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public abstract class Actor {
    public final String name;
    private Pokemon active;
    private ArrayList<Pokemon> arsenal;

    Actor(String name) {
        this.name = name;
    }

    public void recoverAll() {
        for (Pokemon p : this.arsenal) p.recharge();
    }

    public void healAll() {
        for (Pokemon p : this.arsenal) p.heal();
    }

    public void addPokemon(Pokemon p) {
        arsenal.add(p);
    }

    public abstract Response attack();

    public abstract Response retreat();

    public abstract Response pass();

    public abstract Response choosePokemon();
}
