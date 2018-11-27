package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public class Bot extends Actor {
    public static final String ATTACK = "attack", PASS = "pass";

    Bot(String name, ArrayList<Pokemon> roster) {
        super(name);
        for (Pokemon p : roster) {
            addPokemon(new Pokemon(p));
        }
    }

    @Override
    public Utilities.Response decision() {
        String message;
        boolean status = false;
        Pokemon active = getActive();
        for (Pokemon.Attack a : active.availableAttacks) {
            if (a.energyCost < active.getEnergy()) {
                status = true;
                break;
            }
        }
        message = status ? ATTACK : PASS;
        return new Utilities.Response(message, status);
    }

    @Override
    public Utilities.Response attack() {
        return null;
    }

    @Override
    public Utilities.Response retreat() {
        return null;
    }

    @Override
    public Utilities.Response pass() {
        return null;
    }

    @Override
    public Utilities.Response choosePokemon() {
        return null;
    }
}
