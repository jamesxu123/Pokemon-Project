package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;

public class Player extends Actor {

    Player(String name) {
        super(name);
    }

    @Override
    public Utilities.Response turnDecision() {
        return null;
    }

    @Override
    public Utilities.Response attack(Pokemon p) {
        active.attack(p, chooseAttack());
        return new Utilities.Response(Pokemon.Attack.SUCCESS, true);
    }

    public Utilities.Response retreat() {
        return null;
    }

    @Override
    public void chooseRoster() {
        ArrayList<Pokemon> roster = new ArrayList<>();
        System.out.println("Choose four Pokemon:");
        for (int i = 0; i < PokemonArena.roster.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, PokemonArena.roster.get(i).name));
        }
        System.out.println("Enter numbers: ");
        for (int i = 0; i < 4; i++) {
            int choice = Utilities.getInputFromRange(0, PokemonArena.roster.size()) - 1;
            Pokemon c = PokemonArena.roster.get(choice);
            while (roster.contains(c)) {
                choice = Utilities.getInputFromRange(0, PokemonArena.roster.size()) - 1;
                c = PokemonArena.roster.get(choice);
            }
            roster.add(new Pokemon(c));
        }
        this.roster.addAll(roster);
    }

    @Override
    public Pokemon.Attack chooseAttack() {
        System.out.println("Choose an attack: ");
        for (int i = 0; i < active.availableAttacks.size(); i++) {
            Pokemon.Attack a = active.availableAttacks.get(i);
            System.out.println(String.format("%d. %s: %d damage, %d cost, %s special", i + 1, a.name,
                    a.damage, a.energyCost, (a.special.equals(Pokemon.Attack.NONE)) ? "No" : a.special));
        }
        System.out.print("Enter a number: ");
        int index = Utilities.getInputFromRange(1, active.availableAttacks.size() + 1) - 1;
        return active.availableAttacks.get(index);
    }

    @Override
    public Utilities.Response chooseActive() {
        System.out.println("Choose a Pokemon: ");
        for (int i = 0; i < roster.size(); i++) {
            System.out.println(String.format("%d. %s", i + 1, roster.get(i).name));
        }
        System.out.print("Enter a number: ");
//        int choice;
//        boolean done = false;
//        Scanner input = new Scanner(System.in);
//        while (!done) {
//            try {
//                choice = Integer.parseInt(input.nextLine());
//                if (Utilities.withinRange(choice - 1, 0, roster.size())) {
//                    activeIndex = choice - 1;
//                    setActive();
//                    done = true;
//                    System.out.println(String.format("%s, I CHOOSE YOU!", active.name));
//                }
//            } catch (Exception e) {
//                System.out.println("Not a number");
//            }
//
//        }
        activeIndex = Utilities.getInputFromRange(1, roster.size() + 1) - 1;
        setActive();
        System.out.println(String.format("%s, I CHOOSE YOU!", active.name));
        return null;
    }
}
