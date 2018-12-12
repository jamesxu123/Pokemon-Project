/*
James Xu
ICS4U Pokemon Project

Actor class
    - Abstract class that contains standardized fields and methods
    - Methods that would differ in Bot and Player are left as abstract
    - Used to represent a generic user
*/

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

    public Pokemon getActiveCopy() {
        //Returns a copy of the active Pokemon for safe access in different classes
        return new Pokemon(active);
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

    public abstract Utilities.Response turnDecision(); //Gets decision as ATTACK, PASS, or RETREAT

    protected abstract Pokemon.Attack chooseAttack(); //Chooses an attack for attacking

    public void recoverAll() {
        //Recharge energy of all Pokemon
        for (Pokemon p : this.roster) p.recharge();
    }

    public void healAll() {
        //Heal all Pokemon by per-turn amount
        for (Pokemon p : this.roster) p.heal();
    }

    public Utilities.Response attack(Actor actor) {
        //Call the active Pokemon's attack method and choose an attack
        return active.attack(actor.active, chooseAttack());
    }

    public void pass() {
        //Pass the turn and check for stun
        if (this.active.getStatus().equals(Pokemon.STUNNED)) {
            this.unStunActive(); //If pass is due to stun, un-stun at while passing the turn
        }
        System.out.println(String.format("%s has passed their turn!", name));
    }

    protected abstract void chooseActive();
}
