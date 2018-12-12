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

    private static void loadPokemon() throws IOException {
        //Load Pokemon from file
        BufferedReader file = new BufferedReader(new FileReader("pokemon.txt"));
        file.readLine(); //Since ArrayList is used, value is not needed
        while (file.ready()) {
            String line = file.readLine();
            roster.add(new Pokemon(line));
        }
    }

    public static void main(String[] args) throws IOException {
        loadPokemon();
        Utilities.displayFile("uiText/opening.txt");
        Player player = new Player("Player"); //Player object for the user
        ArrayList<Pokemon> chosen = player.chooseRoster();
        roster.removeAll(chosen); //Remove all the Pokemon the player has chosen
        Bot bot = new Bot("Bot", roster.get(Utilities.randInt(0, roster.size()))); //AI bot object
        Utilities.displayFile("uiText/roundStart.txt");
        turnLoop(player, bot);
    }

    private static void turnLoop(Player player, Bot bot) throws IOException {
        //Game loop function
        boolean running = true;
        player.chooseActive();
        while (running) {
            System.out.println("-----------Enemy-----------");
            Utilities.displayPokemon(bot.getActiveCopy());
            System.out.println("-".repeat("-----------Enemy-----------".length()));
            Utilities.Response playerDecision = player.turnDecision(); //Turn always begins with player choosing move
            switch (playerDecision.status) {
                case Player.ATTACK:
                    final Utilities.Response attack = player.attack(bot);
                    System.out.print(attack.message);
                    switch (attack.status) {
                        case Pokemon.Attack.NOT_ENOUGH_ENERGY: //Should not be needed, fail-safe
                            System.out.println("Not enough energy!");
                            break;
                        case Pokemon.Attack.KILLED: //Enemy Pokemon killed signifies the start of a new battle
                            player.healAll(); //End-of-battle heals for player Pokemon
                            bot.deathRitual(); //Delete dead Pokemon and force bot to pick a new one
                            player.recoverAllMax(); //Reset player Pokemon energy status
                            bot.recoverAllMax(); //Reset bot Pokemon energy status
                            break;
                    }
                    break;
                case Player.RETREAT:
                    player.chooseActive(); //Retreat is equivalent to picking a new active Pokemon
                    break;
                case Player.PASS:
                    player.pass();
                    break;
            }
            if (roster.size() > 0) { //If PokemonArena roster is empty, player has won the game
                Utilities.Response botDecision = bot.turnDecision(); //Retrieve AI decision
                switch (botDecision.status) {
                    case Bot.ATTACK:
                        final Utilities.Response attack = bot.attack(player); //Get bot attack message
                        System.out.print(attack.message);
                        switch (attack.status) {
                            case Pokemon.Attack.NOT_ENOUGH_ENERGY: //Should not be needed, fail-safe
                                System.out.println("Not enough energy!");
                                break;
                            case Pokemon.Attack.KILLED: //Signifies end of battle
                                player.deathRitual(); //Delete and choose a new active
                                if (player.getRosterSize() == 0) { //If player roster is empty, they have lost
                                    Utilities.displayFile("uiText/loss.txt");
                                    return;
                                }
                                player.recoverAllMax(); //Recover energy of remaining player Pokemon
                                bot.recoverAllMax(); //Recover energy of the bot Pokemon that is still alive
                                break;
                        }
                        break;
                    case Bot.PASS:
                        bot.pass();
                        break;
                }
                player.recoverAll(); //Recharge at end of turn
                bot.recoverAll();
            } else {
                Utilities.displayFile("uiText/win.txt");
                running = false;
            }
        }
    }
}
