package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public abstract class Actor {
    public final String name;
    private Pokemon active;
    private ArrayList<Pokemon> roster;

    Actor(String name) {
        this.name = name;
    }

    public Pokemon getActive() {
        return active;
    }

    public abstract Utilities.Response decision();

    public void recoverAll() {
        for (Pokemon p : this.roster) p.recharge();
    }

    public void healAll() {
        for (Pokemon p : this.roster) p.heal();
    }

    public void addPokemon(Pokemon p) {
        roster.add(p);
    }

    public abstract Utilities.Response attack();

    public abstract Utilities.Response retreat();

    public abstract Utilities.Response pass();

    public abstract Utilities.Response choosePokemon();
}
