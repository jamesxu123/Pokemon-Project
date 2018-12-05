package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public class Bot extends Actor {

    Bot(String name, Pokemon active) {
        super(name);
        this.active = active;
    }

    @Override
    public void chooseRoster() {

    }

    @Override
    public Utilities.Response attack(Pokemon p) {
        return active.attack(p, chooseAttack());
    }

    @Override
    public Pokemon.Attack chooseAttack() {
        Pokemon.Attack a = active.availableAttacks.get(Utilities.randInt(0, active.availableAttacks.size()));
        System.out.println(String.format("%s (%s) has chosen %s", active.name, this.name, a.name));
        return a;
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
    public Utilities.Response chooseActive() {
        activeIndex = Utilities.randInt(0, this.roster.size());
        setActive();
        return new Utilities.Response(active.name, true);
    }
}
