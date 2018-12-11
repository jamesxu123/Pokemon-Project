package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public class Pokemon {
    public static final String DEFAULT = "NORMAL", STUNNED = "STUNNED", DEAD = "DEAD";
    public final ArrayList<Attack> availableAttacks = new ArrayList<>();
    public final String name;
    private final int hpMax;
    private final String type;
    private final String resistance;
    private final String weakness;
    private int hp;
    private int energy = 50;
    private String status = DEFAULT;
    private boolean DISABLED = false;

    public Pokemon(String init) {
        String[] lineArray = init.split(",");
        name = lineArray[0];
        hp = Integer.parseInt(lineArray[1]);
        hpMax = hp;
        type = lineArray[2];
        weakness = lineArray[3];
        resistance = lineArray[4];
        int numAttacks = Integer.parseInt(lineArray[5]);
        for (int i = 6; i < 6 + numAttacks * 4; i += 4) { //Loop through attacks and create an object for each
            String attackName = lineArray[i];
            int energyCost = Integer.parseInt(lineArray[i + 1]);
            int damage = Integer.parseInt(lineArray[i + 2]);
            String special = lineArray[i + 3];
            availableAttacks.add(new Attack(attackName, energyCost, damage, special));
        }
    }

    public Pokemon(Pokemon p) {
        //Clone constructor to avoid changing original
        name = p.name;
        hp = p.hpMax;
        hpMax = p.hpMax;
        type = p.type;
        resistance = p.resistance;
        weakness = p.weakness;
        availableAttacks.addAll(p.availableAttacks);
        energy = p.energy;
        status = p.status;
        DISABLED = p.DISABLED;
    }

    public String getResistance() {
        return resistance;
    }

    public String getWeakness() {
        return weakness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pokemon)) return false;

        Pokemon pokemon = (Pokemon) o;

        if (hpMax != pokemon.hpMax) return false;
        if (!name.equals(pokemon.name)) return false;
        if (!resistance.equals(pokemon.resistance)) return false;
        return weakness.equals(pokemon.weakness);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + hpMax;
        result = 31 * result + resistance.hashCode();
        result = 31 * result + weakness.hashCode();
        return result;
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
                ", message=" + status +
                '}';
    }

    public ArrayList<Attack> validAttacks() {
        ArrayList<Attack> attacks = new ArrayList<>(); //Build a new list of attacks the Pokemon can afford
        for (Pokemon.Attack attack : availableAttacks) {
            if (attack.energyCost <= this.energy) {
                attacks.add(attack);
            }
        }
        return attacks;
    }

    public void unStun() {
        this.status = DEFAULT;
    }

    public void heal(int amount) {
        if (this.hp + amount <= this.hpMax) { //Don't allow health to go over max
            this.hp += amount;
        } else {
            this.hp = this.hpMax;
        }
    }

    public void heal() {
        heal(20); //Method overload heal for per-battle recovery
    }

    public String getStatus() {
        return status;
    }

    public void recharge() {
        if (this.energy + 10 > 50) { //Don't allow energy to go over max
            this.energy = 50;
        } else {
            this.energy += 10;
        }
    }

    public int getEnergy() {
        return energy;
    }

    public Utilities.Response attack(Pokemon p, Attack a) {
        StringBuilder message = new StringBuilder(String.format("-----%10s's Attack-----\n", name));
        //Final output that is displayed to the user

        int damage = DISABLED ? a.damage - 10 < 0 ? 0 : a.damage - 10 : a.damage;
        //Reduce damage by 10 for disabled without going below 0

        int totalDamage = 0;
        if (a.energyCost <= this.energy) {
            this.energy -= a.energyCost;
            switch (a.special) { //Special attack implementation
                case Attack.STUN:
                    totalDamage += a.damage;
                    if (Utilities.coinFlip()) {
                        p.status = STUNNED;
                        message.append(String.format("%s has been stunned!\n", p.name));
                    }
                    break;
                case Attack.WILDCARD:
                    if (Utilities.coinFlip()) {
                        totalDamage += damage;
                        message.append("Wildcard suceeded!\n");
                    } else {
                        message.append("Wildcard missed!\n");
                    }
                    break;
                case Attack.WILDSTORM:
                    while (Utilities.coinFlip()) {
                        totalDamage += damage;
                        message.append("WILDSTORM!\n");
                    }
                    break;
                case Attack.DISABLE:
                    p.DISABLED = true;
                    message.append(String.format("%s has been disabled!\n", p.name));
                    totalDamage += a.damage;
                    break;
                case Attack.RECHARGE:
                    this.energy += 20;
                    message.append(String.format("%s has recharged itself 20 energy!\n", name));
                    totalDamage += a.damage;
                    break;
                case Attack.NONE:
                    totalDamage += damage;
                    break;
            }
            if (p.resistance.equals(this.type)) {
                totalDamage /= 2; //Halve damage for resistance
            } else if (p.weakness.equals(this.type)) {
                totalDamage *= 2; //Double damage for weakness
            }
            p.hp -= totalDamage;
            message.append(String.format("%s has dealt %d damage to %s\n", this.name, totalDamage, p.name));
            if (p.hp <= 0) {
                p.hp = 0;
                p.status = DEAD; //Mark pokemon as dead when killed
                message.append(String.format("%s has KILLED %s!\n", name, p.name)).append("-".repeat(29)).append("\n");
                return new Utilities.Response(Attack.KILLED, message.toString());
            }
            message.append("-".repeat(29)).append("\n");
        } else {
            return new Utilities.Response(Attack.NOT_ENOUGH_ENERGY, message.toString());
        }
        return new Utilities.Response(Attack.SUCCESS, message.toString());
    }

    public class Attack {
        public static final String STUN = "stun", WILDCARD = "wild card",
                WILDSTORM = "wild storm", DISABLE = "disable", RECHARGE = "recharge", NONE = " ";
        public static final String NOT_ENOUGH_ENERGY = "NOT_ENOUGH_ENERGY", SUCCESS = "SUCCESS", KILLED = "KILLED";
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
