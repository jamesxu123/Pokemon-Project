package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;
import java.util.Arrays;

public class Pokemon {
    public static final int DEFAULT = 0, STUNNED = 1, DISABLED = 2;
    private int hp, hpMax, energy = 50;
    private String name, type, resistance, weakness;
    private ArrayList<Attack> availableAttacks = new ArrayList<>();
    private int status = DEFAULT;

    Pokemon(String init) {
        String[] lineArray = init.split(",");
        name = lineArray[0];
        hp = Integer.parseInt(lineArray[1]);
        type = lineArray[2];
        weakness = lineArray[3];
        resistance = lineArray[4];
        int numAttacks = Integer.parseInt(lineArray[5]);
        for (int i = 6; i < numAttacks * 4; i += 4) {
            String attackName = lineArray[i];
            int energyCost = Integer.parseInt(lineArray[i + 1]);
            int damage = Integer.parseInt(lineArray[i + 2]);
            String special = lineArray[i + 3];
            availableAttacks.add(new Attack(attackName, energyCost, damage, special));
        }
    }

    Pokemon(Pokemon p) {
    }

    public void heal(int amount) {
        if (this.hp + amount <= this.hpMax) this.hp += amount;
    }

    public void heal() {
        heal(10);
    }

    public int getStatus() {
        return status;
    }

    public void recharge() {
        if (this.energy + 10 > 50) {
            this.energy = 50;
        } else {
            this.energy += 50;
        }
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
                case Attack.NONE:
                    totalDamage += a.damage;
            }
            if (p.resistance.equals(this.type)) {
                p.hp -= totalDamage / 2;
            } else if (p.weakness.equals(this.type)) {
                p.hp -= totalDamage * 2;
            } else {
                p.hp -= totalDamage;
            }
        } else {
            return new Response("You don't have enough energy!", false);
        }

        return new Response("Attack Success", true);
    }

    protected class Attack {
        public static final String STUN = "stun", WILDCARD = "wild card",
                WILDSTORM = "wild storm", DISABLE = "disable", RECHARGE = "recharge", NONE = " ";
        final String name, special;
        final int energyCost, damage;

        Attack(String name, int energyCost, int damage, String special) {
            this.name = name;
            this.energyCost = energyCost;
            this.damage = damage;
            this.special = special;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Attack{");
            sb.append("name='").append(name).append('\'');
            sb.append(", special='").append(special).append('\'');
            sb.append(", energyCost=").append(energyCost);
            sb.append(", damage=").append(damage);
            sb.append('}');
            return sb.toString();
        }
    }
}
