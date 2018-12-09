package com.jamesxu.ics4u.pokemon;

import com.jamesxu.ics4u.pokemon.arena.PokemonArena;

import java.util.ArrayList;

public class Player extends Actor {


    public Player(String name) {
        super(name);
    }

    @Override
    public Utilities.Response turnDecision() {
        if (active.getStatus().equals(Pokemon.STUNNED)) {
            System.out.println(String.format("%s (%s) is stunned, you have to pass your turn!",
                    active.name, name));
            return new Utilities.Response(Player.PASS, "");
        }
        System.out.println("Choose from the following: ");
        if (canAttack()) System.out.println("1. ATTACK");
        System.out.println("2. PASS");
        System.out.println("3. RETREAT");
        int choice = Utilities.getInputFromRange(1, 4);
        String status = "";
        switch (choice) {
            case 1:
                status = Player.ATTACK;
                return new Utilities.Response(status, "");
            case 2:
                status = Player.PASS;
                return new Utilities.Response(status, "");
            case 3:
                status = Player.RETREAT;
                return new Utilities.Response(status, "");
        }
        return new Utilities.Response(status, "");

    }

    @Override
    public ArrayList<Pokemon> chooseRoster() {
        ArrayList<Pokemon> roster = new ArrayList<>();
        System.out.println("Choose four Pokemon:");
        for (int i = 0; i < PokemonArena.roster.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, PokemonArena.roster.get(i).name));
        }
        System.out.println("Enter numbers: ");

        for (int i = 0; i < 4; i++) {
            int choice = Utilities.getInputFromRange(1, PokemonArena.roster.size() + 1) - 1;
            Pokemon c = PokemonArena.roster.get(choice);
            while (roster.contains(c)) {
                System.out.println("Already chosen");
                choice = Utilities.getInputFromRange(1, PokemonArena.roster.size() + 1) - 1;
                c = PokemonArena.roster.get(choice);
            }
            roster.add(new Pokemon(c));
        }
        this.roster.addAll(roster);

        return roster;
    }

    @Override
    protected Pokemon.Attack chooseAttack() {
        System.out.println("Choose an attack: ");
        for (int i = 0; i < active.availableAttacks.size(); i++) {
            Pokemon.Attack a = active.availableAttacks.get(i);
            System.out.println(String.format("%d. %s: %d damage, %d cost, %s special", i + 1, a.name,
                    a.damage, a.energyCost, (a.special.equals(Pokemon.Attack.NONE)) ? "No" : a.special));
        }
        System.out.print("Enter a number: ");
        int index = Utilities.getInputFromRange(1, active.availableAttacks.size() + 1) - 1;
        Pokemon.Attack chosen = active.availableAttacks.get(index);
        while (chosen.energyCost > active.getEnergy()) {
            System.out.println("Attack costs too much");
            index = Utilities.getInputFromRange(1, active.availableAttacks.size() + 1) - 1;
        }
        return active.availableAttacks.get(index);
    }

    @Override
    public void chooseActive() {
        System.out.println("Choose a Pokemon: ");
        for (int i = 0; i < roster.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, roster.get(i).name));
            Utilities.displayPokemon(roster.get(i));
        }
        System.out.print("Enter a number: ");
        int activeIndex = Utilities.getInputFromRange(1, roster.size() + 1) - 1;
        active = roster.get(activeIndex);
        System.out.println(String.format("%s, I CHOOSE YOU!", active.name));
    }
}
