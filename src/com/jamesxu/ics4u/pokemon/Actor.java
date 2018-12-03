package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public abstract class Actor {
    public static final String ATTACK = "attack", PASS = "pass";
    public final String name;
    protected Pokemon active;
    protected ArrayList<Pokemon> roster;

    Actor(String name) {
        this.name = name;
    }

    public abstract Utilities.Response turnDecision();

    public abstract Pokemon.Attack attackDecision();

    public void recoverAll() {
        for (Pokemon p : this.roster) p.recharge();
    }

    public void healAll() {
        for (Pokemon p : this.roster) p.heal();
    }

    public void addPokemon(Pokemon p) {
        roster.add(p);
    }

    public abstract Utilities.Response attack(Pokemon p);

    public void pass() {
        System.out.println(String.format("%s has passed their turn!", name));
    }

    public abstract Utilities.Response choosePokemon();
}
