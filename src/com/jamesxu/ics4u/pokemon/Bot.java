package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public class Bot extends Actor {

    Bot(String name, ArrayList<Pokemon> roster) {
        super(name);
        for (Pokemon p : roster) {
            addPokemon(new Pokemon(p));
        }
    }

    @Override
    public Utilities.Response attack(Pokemon p) {
        return active.attack(p, attackDecision());
    }

    @Override
    public Pokemon.Attack attackDecision() {
        return active.availableAttacks.get(Utilities.randint(0, active.availableAttacks.size()));
    }

    @Override
    public Utilities.Response turnDecision() {
        String message;
        boolean status = false;
        Pokemon active = this.active;
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
    public Utilities.Response choosePokemon() {
        int index = Utilities.randint(0, this.roster.size());
        Pokemon chosen = this.roster.get(index);
        this.active = chosen;
        return new Utilities.Response(chosen.name, true);
    }
}
