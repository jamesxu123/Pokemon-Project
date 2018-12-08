package com.jamesxu.ics4u.pokemon.arena;

import com.jamesxu.ics4u.pokemon.Bot;
import com.jamesxu.ics4u.pokemon.Player;
import com.jamesxu.ics4u.pokemon.Pokemon;
import com.jamesxu.ics4u.pokemon.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PokemonArena {
    public static final ArrayList<Pokemon> roster = new ArrayList<>();

    private static void loadPokemon() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("pokemon.txt"));
            int num = Integer.parseInt(file.readLine());
            while (file.ready()) {
                String line = file.readLine();
                roster.add(new Pokemon(line));

            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static void main(String[] args) throws IOException {
        loadPokemon();
        Utilities.displayFile("uiText/opening.txt");
        Player player = new Player("Player");
        ArrayList<Pokemon> chosen = player.chooseRoster();
        roster.removeAll(chosen);
        Bot bot = new Bot("Bot", roster.get(Utilities.randInt(0, roster.size())));
        Utilities.displayFile("uiText/roundStart.txt");
        turnLoop(player, bot);
    }

    public static void turnLoop(Player player, Bot bot) {
        boolean running = true;
        player.chooseActive();
        while (running) {
            if (player.getRosterSize() == 0) {
                System.out.println("You've lost!");
                running = false;
            }
            Utilities.Response playerDecision = player.turnDecision();
            switch (playerDecision.message) {
                case Player.ATTACK:
                    final Utilities.Response attack = player.attack(bot);
                    System.out.println(bot.getActiveCopy());
                    switch (attack.message) {
                        case Pokemon.Attack.SUCCESS:
                            System.out.println("Attack Success!");
                            break;
                        case Pokemon.Attack.NOT_ENOUGH_ENERGY:
                            System.out.println("Not enough energy!");
                            break;
                        case Pokemon.Attack.KILLED:
                            System.out.println(String.format("%s has beaten %s's %s!", player.name, bot.name,
                                    bot.getActiveCopy().name));
                            bot.deathRitual(bot.getActiveCopy());
                            bot.chooseActive();
                            break;
                    }
                    break;
                case Player.RETREAT:
                    player.chooseActive();
                    break;
            }
            Utilities.Response botDecision = bot.turnDecision();
            switch (botDecision.message) {
                case Bot.ATTACK:
                    final Utilities.Response attack = bot.attack(player);
                    switch (attack.message) {
                        case Pokemon.Attack.KILLED:
                            System.out.println(player.getActiveCopy().name + " has been killed!");
                            player.deathRitual(player.getActiveCopy());
                            break;
                        case Pokemon.Attack.SUCCESS:

                    }
                    break;
                case Bot.PASS:
                    bot.pass();
                    break;
            }
            player.recoverAll();
            player.healAll();
            bot.recoverAll();
            bot.healAll();
        }
    }
}
