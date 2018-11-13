package com.jamesxu.ics4u;

public class Pokemon {
    private int hp, hpMax, energy;
    private String name, type, resistance, weakness;

    public void attack(Pokemon p, Attack a) {
        if (a.energyCost >= p.energy) {

        }
    }

    class Attack {
        public static final int STUN = 0, WILDCARD = 1, WILDSTORM = 2, DISABLE = 3, RECHARGE = 4;
        final String name;
        final int energyCost, damage, special;

        Attack(String name, int energyCost, int damage, int special) {
            this.name = name;
            this.energyCost = energyCost;
            this.damage = damage;
            this.special = special;
        }
    }
}
