package com.bowspleef.game;

import com.bowspleef.BowSpleef;
import com.bowspleef.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    private String name;
    private Arena arena;
    private GameState state = GameState.DISABLED;

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> spectators = new ArrayList<>();
    private ArrayList<Player> queue = new ArrayList<>();
    private ArrayList<Player> votes = new ArrayList<>();

    private HashMap<Player, ItemStack[][]> inventoryStorage = new HashMap<>();
    private HashMap<Player, Integer> gameModeStorage = new HashMap<>();
    private HashMap<Player, Integer> foodLevelStorage = new HashMap<>();
    private HashMap<Player, Double> healthStorage = new HashMap<>();
    private HashMap<Player, Location> prevLocationStorage = new HashMap<>();

    private int minPlayers;
    private int maxPlayers;

    private FileConfiguration arenaFile = BowSpleef.playerFileConfiguration;

    public Game(String name) {
        this.name = name;
        setup();
    }

    public void setup() {
        state = GameState.LOADING;

        String world = arenaFile.getString("arenas." + name + ".world");

        int pos1X = arenaFile.getInt("arenas." + name + ".pos1.x");
        int pos1Y = arenaFile.getInt("arenas." + name + ".pos1.y");
        int pos1Z = arenaFile.getInt("arenas." + name + ".pos1.z");

        int pos2X = arenaFile.getInt("arenas." + name + ".pos2.x");
        int pos2Y = arenaFile.getInt("arenas." + name + ".pos2.y");
        int pos2Z = arenaFile.getInt("arenas." + name + ".pos2.z");

        int lobbySpawnX = arenaFile.getInt("arenas." + name + ".lobby.x");
        int lobbySpawnY = arenaFile.getInt("arenas." + name + ".lobby.y");
        int lobbySpawnZ = arenaFile.getInt("arenas." + name + ".lobby.z");

        int spectatorSpawnX = arenaFile.getInt("arenas." + name + ".spectator.x");
        int spectatorSpawnY = arenaFile.getInt("arenas." + name + ".spectator.y");
        int spectatorSpawnZ = arenaFile.getInt("arenas." + name + ".spectator.z");

        int spawnX = arenaFile.getInt("arenas." + name + ".spawn.x");
        int spawnY = arenaFile.getInt("arenas." + name + ".spawn.y");
        int spawnZ = arenaFile.getInt("arenas." + name + ".spawn.z");

        Location pos1 = new Location(Bukkit.getWorld(world), pos1X, pos1Y, pos1Z);
        Location pos2 = new Location(Bukkit.getWorld(world), pos2X, pos2Y, pos2Z);
        Location lobby = new Location(Bukkit.getWorld(world), lobbySpawnX, lobbySpawnY, lobbySpawnZ);
        Location spectatorSpawn = new Location(Bukkit.getWorld(world), spectatorSpawnX, spectatorSpawnY, spectatorSpawnZ);
        Location spawn = new Location(Bukkit.getWorld(world), spawnX, spawnY, spawnZ);

        arena = new Arena(lobby, spawn, spectatorSpawn, pos1, pos2);

        minPlayers = arenaFile.getInt("arenas." + name + ".min-players");
        maxPlayers = arenaFile.getInt("arenas." + name + ".max-players");

        state = GameState.WAITING;
    }

    public boolean addPlayer(Player player) {

        if (!player.hasPermission("bowspleef.player.join")) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have permission to join this game.");
            return false;
        }

        if (state == GameState.DISABLED) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game is not enabled.");
            return false;
        }

        if (GameManager.getInstance().getGame(player) != null) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You cannot join multiple games.");
            return false;
        }

        if (spectators.contains(player)) {
            //Remove spectator
        }

        if (state == GameState.WAITING || state == GameState.STARTING) {

            if (players.size() >= maxPlayers) {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game is already full.");
                return false;
            }

            MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "Joining BowSpleef game: " + ChatColor.AQUA + name);

            gameModeStorage.put(player, player.getGameMode().getValue());
            foodLevelStorage.put(player, player.getFoodLevel());
            healthStorage.put(player, player.getHealth());

            player.setHealth(20.0);
            player.setGameMode(GameMode.SURVIVAL);
            player.setFoodLevel(20);

            saveInventory(player);
            player.getInventory().clear();

            prevLocationStorage.put(player, player.getLocation());
            player.teleport(arena.getLobby());

            //TODO: Event API

            players.add(player);

            //TODO: Message all players and spectators
        }

        return true;
    }

    public void enable() {

    }

    public void disable() {

    }

    private void saveInventory(Player player) {
        ItemStack[][] storage = new ItemStack[2][1];

        storage[0] = player.getInventory().getContents();
        storage[1] = player.getInventory().getArmorContents();

        inventoryStorage.put(player, storage);
    }

    private void retrieveInventory(Player player) {
        if (inventoryStorage.containsKey(player)) {
            player.getInventory().clear();
            player.getInventory().setContents(inventoryStorage.get(player)[0]);
            player.getInventory().setArmorContents(inventoryStorage.get(player)[1]);
            inventoryStorage.remove(player);
            player.updateInventory();
        }
    }

    public String getName() {
        return name;
    }

    public Arena getArena() {
        return arena;
    }

    public GameState getState() {
        return state;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Player> getSpectators() {
        return spectators;
    }

    public ArrayList<Player> getQueue() {
        return queue;
    }

    public ArrayList<Player> getVotes() {
        return votes;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public enum GameState {
        DISABLED, LOADING, INACTIVE, WAITING,
        STARTING, IN_GAME, RESETTING, ERROR;
    }

}
