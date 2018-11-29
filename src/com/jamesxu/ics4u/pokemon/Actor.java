package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public abstract class Actor {
    public final String name;
    protected Pokemon active;
    protected ArrayList<Pokemon> roster;

    Actor(String name) {
        this.name = name;
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

    public abstract Utilities.Response attack(Pokemon p, Pokemon.Attack a);

    public abstract Utilities.Response pass();

    public abstract Utilities.Response choosePokemon();
}
