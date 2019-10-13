package com.bowspleef.game;

import com.bowspleef.BowSpleef;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;

import java.util.List;

public class Game {

    private String name;
    private GameState state;
    private Arena arena;
    private int minPlayers;
    private int maxPlayers;

    List<BowSpleefPlayer> players;
    List<BowSpleefPlayer> spectators;

    public Game(String name) {
        this.name = name;
    }

    public void join(BowSpleefPlayer player) {
        if (!player.hasPermission("bowspleef.player.join")) {
            player.sendMessage(BowSpleefPlayer.MessageType.ERROR, "You do not have permission to join a game.");
            return;
        }

        if (getState() == GameState.NOTSETUP) {
            player.sendMessage(BowSpleefPlayer.MessageType.ERROR, "This game is not yet setup.");
            return;
        }

        //TODO: Check to see if the Player is in a game.

        if (getState() == GameState.LOBBY || getState() == GameState.STARTING) {

            if (getPlayers().size() == getMaxPlayers()) {
                player.sendMessage(BowSpleefPlayer.MessageType.ERROR, "This game is already full. Try another.");
                return;
            }

            player.saveInventory();
            player.setGameMode(GameMode.SURVIVAL);
            player.setFoodLevel(20);
            player.setHealth(20);

            Location returnLocation = player.getLocation();
            BowSpleef.playerFileConfiguration.set(player.getName() + ".return.world", returnLocation.getWorld().getName());
            BowSpleef.playerFileConfiguration.set(player.getName() + ".return.x", returnLocation.getBlockX());
            BowSpleef.playerFileConfiguration.set(player.getName() + ".return.y", returnLocation.getBlockY());
            BowSpleef.playerFileConfiguration.set(player.getName() + ".return.z", returnLocation.getBlockZ());



        }
    }

    public void leave(BowSpleefPlayer player) {

    }

    public String getName() {
        return name;
    }

    public GameState getState() {
        return state;
    }

    public Arena getArena() {
        return arena;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public List<BowSpleefPlayer> getPlayers() {
        return players;
    }

    public List<BowSpleefPlayer> getSpectators() {
        return spectators;
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
