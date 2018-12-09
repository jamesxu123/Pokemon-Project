package com.jamesxu.ics4u.pokemon;

import com.jamesxu.ics4u.pokemon.arena.PokemonArena;

import java.util.ArrayList;

public abstract class Actor {
    public static final String ATTACK = "attack", PASS = "pass", RETREAT = "retreat";
    public final String name;

    protected final ArrayList<Pokemon> roster = new ArrayList<>();
    protected Pokemon active;

    Actor(String name) {
        this.name = name;
    }

    public int getRosterSize() {
        return roster.size();
    }

    public void deathRitual() {
        roster.remove(active);
        PokemonArena.roster.remove(active);
        if (roster.size() > 0) {
            chooseActive();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;

        Actor actor = (Actor) o;

        return name.equals(actor.name);
    }

    private void unStunActive() {
        active.unStun();
        System.out.println(String.format("%s (%s) is no longer stunned!", active.name, name));
    }

    boolean canAttack() {
        for (Pokemon.Attack attack : active.availableAttacks) {
            if (attack.energyCost <= active.getEnergy()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public abstract Utilities.Response turnDecision();

    protected abstract Pokemon.Attack chooseAttack();

    public abstract ArrayList<Pokemon> chooseRoster();

    public void recoverAll() {
        for (Pokemon p : this.roster) p.recharge();
    }

    public void healAll() {
        for (Pokemon p : this.roster) p.heal();
    }

    public Utilities.Response attack(Actor actor) {
        return active.attack(actor.active, chooseAttack());
    }

    public void pass() {
        if (this.active.getStatus().equals(Pokemon.STUNNED)) {
            this.unStunActive();
        }
        System.out.println(String.format("%s has passed their turn!", name));
    }

    public abstract void chooseActive();
}
