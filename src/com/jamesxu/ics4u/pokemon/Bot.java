package com.jamesxu.ics4u.pokemon;

import com.jamesxu.ics4u.pokemon.arena.PokemonArena;

import java.util.ArrayList;

public class Bot extends Actor {

    public Bot(String name, Pokemon active) {
        super(name);
        this.active = active;
    }

    @Override
    public void deathRitual() {
        if (PokemonArena.roster.size() > 0) chooseActive();
    }

    @Override
    public ArrayList<Pokemon> chooseRoster() {
        return null;
    }

    @Override
    public Pokemon.Attack chooseAttack() {
        Pokemon.Attack a = active.validAttacks().get(Utilities.randInt(0, active.validAttacks().size()));
        System.out.println(String.format("%s (%s) has chosen %s", active.name, this.name, a.name));
        return a;
    }

    @Override
    public Utilities.Response turnDecision() {
        String message;
        boolean status = canAttack();
        if (status) {
            message = Bot.ATTACK;
        } else {
            message = Bot.PASS;
        }
        return new Utilities.Response(message, "");
    }

    @Override
    public void recoverAll() {
        active.recharge();
    }

    @Override
    public void healAll() {
        active.heal();
    }

    @Override
    public void chooseActive() {
        int activeIndex = Utilities.randInt(0, PokemonArena.roster.size());
        active = PokemonArena.roster.get(activeIndex);
        PokemonArena.roster.remove(activeIndex);
        System.out.println(String.format("%s (%s), I CHOOSE YOU!", active.name, name));
    }
}
