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
        while (running) {
            Utilities.Response playerDecision = player.turnDecision();
            switch (playerDecision.message) {
                case Player.ATTACK:
                    final Utilities.Response attack = player.attack(bot);
                    switch (attack.message) {
                        case Pokemon.Attack.SUCCESS:
                            System.out.println("Attack Success!");
                        case Pokemon.Attack.NOT_ENOUGH_ENERGY:
                            System.out.println("Not enough energy!");
                        case Pokemon.Attack.KILLED:
                            System.out.println("You've won!");
                            running = false;
                    }
                case Player.RETREAT:
                    player.chooseActive();
            }
            Utilities.Response botDecision = bot.turnDecision();
            switch (botDecision.message) {
                case Bot.ATTACK:
                    final Utilities.Response attack = bot.attack(player);
                    switch (attack.message) {
                        case Pokemon.Attack.KILLED:
                            System.out.println(player.getActiveCopy().name + " has been killed!");
                            player.deathRitual(player.getActiveCopy());
                    }
            }
        }
    }
}
