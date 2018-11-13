package com.jamesxu.ics4u;

import java.util.ArrayList;

public class Pokemon {
    private int hp, hpMax, energy;
    private String name, type, resistance, weakness;
    public static final int DEFAULT = 0, STUNNED = 1, DISABLED = 2;
    private ArrayList<Attack> availableAttacks;
    private int status = DEFAULT;

    public int getStatus() {
        return status;
    }

    public Response attack(Pokemon p, Attack a) {
        int totalDamage = 0;
        if (a.energyCost >= this.energy) {
            switch (a.special) {
                case Attack.STUN:
                    totalDamage = a.damage;
                    if (Utilities.coinFlip()) {
                        p.status = STUNNED;
                    }
                case Attack.WILDCARD:
                    if (Utilities.coinFlip()) {
                        totalDamage = a.damage;
                    }
                case Attack.WILDSTORM:
                    while (Utilities.coinFlip()) {
                        totalDamage += a.damage;
                    }
                case Attack.DISABLE:
                    p.status = DISABLED;
                case Attack.RECHARGE:
                    this.energy += 20;
            }
            if (p.resistance.equals(this.type)) {
                p.hp -= totalDamage / 2;
            } else if (p.weakness.equals(this.type)) {
                p.hp -= totalDamage * 2;
            }
        } else {
            return new Response("You don't have enough energy!", false);
        }

        return new Response("Attack Success", true);
    }

    class Attack {
        public static final String STUN = "stun", WILDCARD = "wild card",
                WILDSTORM = "wild storm", DISABLE = "disable", RECHARGE = "recharge";
        final String name, special;
        final int energyCost, damage;

        Attack(String name, int energyCost, int damage, String special) {
            this.name = name;
            this.energyCost = energyCost;
            this.damage = damage;
            this.special = special;
        }
    }
}
