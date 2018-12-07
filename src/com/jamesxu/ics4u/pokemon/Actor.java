package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

abstract class Actor {
    static final String ATTACK = "attack", PASS = "pass", RETREAT = "retreat";
    final String name;

    final ArrayList<Pokemon> roster = new ArrayList<>();

    Actor(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;

        Actor actor = (Actor) o;

        return name.equals(actor.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public abstract Utilities.Response turnDecision();

    public abstract Pokemon.Attack chooseAttack();

    public abstract ArrayList<Pokemon> chooseRoster();

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

    public abstract Utilities.Response chooseActive();
}
