/*
James Xu
ICS4U Pokemon Project

Player class
    - Implements the Actor class
    - Decision-making based on user input
    - Represents the end-user as a player
*/

package com.jamesxu.ics4u.pokemon;

import com.jamesxu.ics4u.pokemon.arena.PokemonArena;

import java.util.ArrayList;

public class Player extends Actor {


    public Player(String name) {
        super(name);
    }

    @Override
    public Utilities.Response turnDecision() {
        if (active.getStatus().equals(Pokemon.STUNNED)) { //Force pass the turn if active is stunned
            System.out.println(String.format("%s (%s) is stunned, you have to pass your turn!",
                    active.name, name));
            return new Utilities.Response(Player.PASS, "");
        }
        System.out.println("Choose from the following: ");
        if (canAttack()) System.out.println("1. ATTACK");
        System.out.println("2. PASS");
        System.out.println("3. RETREAT");
        int choice = Utilities.getInputFromRange(canAttack() ? 1 : 2, 4);
        //Change start index if attack is invalid

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

    public ArrayList<Pokemon> chooseRoster() {
        ArrayList<Pokemon> roster = new ArrayList<>(); //Collection of the chosen Pokemon
        System.out.println("Choose four Pokemon:");
        for (int i = 0; i < PokemonArena.roster.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, PokemonArena.roster.get(i).name));
            //Output all available Pokemon choices
        }
        System.out.println("Enter numbers: ");

        for (int i = 0; i < 4; i++) {
            int choice = Utilities.getInputFromRange(1, PokemonArena.roster.size() + 1) - 1;
            Pokemon c = PokemonArena.roster.get(choice);
            while (roster.contains(c)) { //Do not let user pick the same one
                System.out.println("Already chosen");
                choice = Utilities.getInputFromRange(1, PokemonArena.roster.size() + 1) - 1;
                c = PokemonArena.roster.get(choice);
            }
            roster.add(new Pokemon(c)); //Clone Pokemon to prevent modifying original
        }
        this.roster.addAll(roster); //Add the pokemon to object field roster

        return roster;
    }

    @Override
    protected Pokemon.Attack chooseAttack() {
        System.out.println("Choose an attack: ");
        for (int i = 0; i < active.validAttacks().size(); i++) {
            Pokemon.Attack a = active.validAttacks().get(i); //Retrieve attack to display information
            System.out.println(String.format("%d. %s: %d damage, %d cost, %s special", i + 1, a.name,
                    a.damage, a.energyCost, (a.special.equals(Pokemon.Attack.NONE)) ? "No" : a.special));
        }
        System.out.print("Enter a number: ");
        int index = Utilities.getInputFromRange(1, active.validAttacks().size() + 1) - 1;
        //Shift range up by 1 to account for starting index at 1 instead of 0
        return active.validAttacks().get(index);
    }

    @Override
    public void chooseActive() {
        System.out.println("Choose a Pokemon: ");
        for (int i = 0; i < roster.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, roster.get(i).name));
            Utilities.displayPokemon(roster.get(i)); //Display detailed info for each Pokemon
        }
        System.out.print("Enter a number: ");
        int activeIndex = Utilities.getInputFromRange(1, roster.size() + 1) - 1;
        while (roster.get(activeIndex).equals(active)) {
            //Don't let user pick the same Pokemon again
            System.out.println("You picked your current Pokemon!");
            activeIndex = Utilities.getInputFromRange(1, roster.size() + 1) - 1;
        }
        active = roster.get(activeIndex);
        System.out.println(String.format("%s, I CHOOSE YOU!", active.name));
    }
}
