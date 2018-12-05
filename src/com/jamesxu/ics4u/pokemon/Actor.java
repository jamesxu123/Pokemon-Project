package com.jamesxu.ics4u.pokemon;

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

    public abstract Utilities.Response turnDecision();

    public abstract Pokemon.Attack chooseAttack();

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
