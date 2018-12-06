package com.jamesxu.ics4u.pokemon;

import java.io.IOException;
import java.util.ArrayList;

public abstract class Actor {
    static final String ATTACK = "attack", PASS = "pass";
    final String name;
    Pokemon active;
    int activeIndex;
    ArrayList<Pokemon> roster = new ArrayList<>();

    Actor(String name) {
        this.name = name;
    }

    public void setActive() {
        active = roster.get(activeIndex);
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

    public abstract void chooseRoster();

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
