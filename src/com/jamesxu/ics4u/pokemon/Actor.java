package com.jamesxu.ics4u.pokemon;

import com.jamesxu.ics4u.pokemon.arena.PokemonArena;

import java.util.ArrayList;

public abstract class Actor {
    public static final String ATTACK = "attack", PASS = "pass", RETREAT = "retreat";
    public final String name;

    final ArrayList<Pokemon> roster = new ArrayList<>();
    protected Pokemon active;

    Actor(String name) {
        this.name = name;
    }

    public Pokemon getActiveCopy() {
        return new Pokemon(active);
    }

    public int getRosterSize() {
        return roster.size();
    }

    public void deathRitual(Pokemon pokemon) {
        roster.remove(pokemon);
        PokemonArena.roster.remove(pokemon);
        chooseActive();
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

    protected abstract Pokemon.Attack chooseAttack();

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

    public Utilities.Response attack(Actor actor) {
        return active.attack(actor.active, chooseAttack());
    }

    public void pass() {
        System.out.println(String.format("%s has passed their turn!", name));
    }

    public abstract Utilities.Response chooseActive();
}
