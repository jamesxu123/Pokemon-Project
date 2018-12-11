package com.jamesxu.ics4u.pokemon;

import com.jamesxu.ics4u.pokemon.arena.PokemonArena;

import java.util.ArrayList;

public abstract class Actor {
    public static final String ATTACK = "attack", PASS = "pass", RETREAT = "retreat";
    public final String name;

    final ArrayList<Pokemon> roster = new ArrayList<>(); //Roster of available Pokemon
    Pokemon active; //Currently battling Pokemon

    Actor(String name) {
        this.name = name;
    }

    public int getRosterSize() {
        //Returns number of Pokemon in the roster
        return roster.size();
    }

    public void deathRitual() {
        //Handles Pokemon death
        roster.remove(active);
        PokemonArena.roster.remove(active);
        if (roster.size() > 0) { //Prevent errors from choosing non-existent Pokemon
            chooseActive();
        }
    }

    public void recoverAllMax() {
        //Take all Pokemon to max energy
        for (int i = 0; i < 5; i++) {
            recoverAll();
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
        //Un-stun active Pokemon
        active.unStun();
        System.out.println(String.format("%s (%s) is no longer stunned!", active.name, name));
    }

    boolean canAttack() {
        //Check if any attacks are affordable
        return active.validAttacks().size() > 0;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public abstract Utilities.Response turnDecision();

    protected abstract Pokemon.Attack chooseAttack();

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

    protected abstract void chooseActive();
}
