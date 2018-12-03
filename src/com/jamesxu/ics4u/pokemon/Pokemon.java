package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public class Pokemon {
    public static final int DEFAULT = 0, STUNNED = 1, DISABLED = 2;
    public final ArrayList<Attack> availableAttacks = new ArrayList<>();
    public final String name;
    private final int hpMax;
    private final String type;
    private final String resistance;
    private final String weakness;
    private int hp;
    private int energy = 50;
    private int status = DEFAULT;

    Pokemon(String init) {
        String[] lineArray = init.split(",");
        name = lineArray[0];
        hp = Integer.parseInt(lineArray[1]);
        hpMax = hp;
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
        name = p.name;
        hp = p.hpMax;
        hpMax = p.hpMax;
        type = p.type;
        resistance = p.resistance;
        weakness = p.weakness;
        availableAttacks.addAll(p.availableAttacks);
        energy = p.energy;
        status = p.status;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public String toString() {
        return "Pokemon{" + "hp=" + hp +
                ", hpMax=" + hpMax +
                ", energy=" + energy +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", resistance='" + resistance + '\'' +
                ", weakness='" + weakness + '\'' +
                ", availableAttacks=" + availableAttacks +
                ", status=" + status +
                '}';
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

    public int getEnergy() {
        return energy;
    }

    public Utilities.Response attack(Pokemon p, Attack a) {
        int totalDamage = 0;
        if (a.energyCost <= this.energy) {
            this.energy -= a.energyCost;
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
            p.hp = p.hp < 0 ? 0 : p.hp;
        } else {
            return new Utilities.Response(Attack.NOT_ENOUGH_ENERGY, false);
        }

        return new Utilities.Response(Attack.SUCCESS, true);
    }

    protected class Attack {
        public static final String STUN = "stun", WILDCARD = "wild card",
                WILDSTORM = "wild storm", DISABLE = "disable", RECHARGE = "recharge", NONE = " ";
        public static final String NOT_ENOUGH_ENERGY = "NOT_ENOUGH_ENERGY", SUCCESS = "SUCCESS";
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
            return "Attack{" + "name='" + name + '\'' +
                    ", special='" + special + '\'' +
                    ", energyCost=" + energyCost +
                    ", damage=" + damage +
                    '}';
        }
    }
}
