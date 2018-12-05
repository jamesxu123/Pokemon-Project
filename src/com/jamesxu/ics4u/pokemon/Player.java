package com.jamesxu.ics4u.pokemon;

import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Actor {

    Player(String name, ArrayList<Pokemon> roster) {
        super(name);
        for (Pokemon p : roster) {
            addPokemon(new Pokemon(p));
        }
    }

    @Override
    public Utilities.Response turnDecision() {
        return null;
    }

    @Override
    public Utilities.Response attack(Pokemon p) {
        active.attack(p, chooseAttack());
        return null;
    }

    public Utilities.Response retreat() {
        return null;
    }

    @Override
    public Pokemon.Attack chooseAttack() {
        System.out.println("Choose an attack: ");
        for (int i = 0; i < active.availableAttacks.size(); i++) {
            Pokemon.Attack a = active.availableAttacks.get(i);
            System.out.println(String.format("%d. %s: %d damage, %d cost, %s special", a.name, a.damage, a.energyCost, a.special));
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
