package com.bowspleef.game;

import com.bowspleef.BowSpleef;
import org.bukkit.ChatColor;

import java.util.List;

public class Game {

    private String name;
    private GameState state;
    private int minPlayers;
    private int maxPlayers;

    List<BowSpleefPlayer> players;
    List<BowSpleefPlayer> spectators;

    public Game(String name) {
        this.name = name;
    }

    public enum GameState {
        DISABLED("Disabled", ChatColor.DARK_RED),
        LOADING("Loading", ChatColor.GOLD),
        LOBBY("Lobby", ChatColor.GREEN),
        STARTING("Starting", ChatColor.DARK_GREEN),
        SPREAD("Spreading Out", ChatColor.RED),
        INGAME("InGame", ChatColor.DARK_RED),
        RESETTING("Resetting", ChatColor.DARK_RED),
        INACTIVE("Inactive", ChatColor.DARK_RED),
        NOTSETUP("Not Setup", ChatColor.DARK_RED),
        ERROR("Error", ChatColor.DARK_RED);

        private String name;
        private ChatColor color;

        GameState(String name, ChatColor color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public ChatColor getColor() {
            return color;
        }
    }

}
