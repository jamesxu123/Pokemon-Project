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

    public static void turnLoop(Player player, Bot bot) throws IOException {
        boolean running = true;
        player.chooseActive();
        while (running) {
            Utilities.Response playerDecision = player.turnDecision();
            switch (playerDecision.status) {
                case Player.ATTACK:
                    final Utilities.Response attack = player.attack(bot);
                    System.out.print(attack.message);
                    switch (attack.status) {
                        case Pokemon.Attack.NOT_ENOUGH_ENERGY:
                            System.out.println("Not enough energy!");
                            break;
                        case Pokemon.Attack.KILLED:
                            player.healAll();
                            bot.deathRitual();
                            break;
                    }
                    break;
                case Player.RETREAT:
                    player.chooseActive();
                    break;
                case Player.PASS:
                    player.pass();
                    break;
            }
            if (roster.size() > 0) {
                Utilities.Response botDecision = bot.turnDecision();
                switch (botDecision.status) {
                    case Bot.ATTACK:
                        final Utilities.Response attack = bot.attack(player);
                        System.out.print(attack.message);
                        switch (attack.status) {
                            case Pokemon.Attack.KILLED:
                                player.deathRitual();
                                if (player.getRosterSize() == 0) {
                                    Utilities.displayFile("uiText/loss.txt");
                                    running = false;
                                    return;
                                }
                                break;
                            case Pokemon.Attack.SUCCESS:

                        }
                        break;
                    case Bot.PASS:
                        bot.pass();
                        break;
                }
                player.recoverAll();
                bot.recoverAll();
            } else {
                Utilities.displayFile("uiText/win.txt");
                running = false;
            }
        }
    }
}
