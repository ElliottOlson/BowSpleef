package com.bowspleef.game;

import com.bowspleef.BowSpleef;
import com.bowspleef.api.*;
import com.bowspleef.manager.MessageManager;
import com.bowspleef.manager.ScoreboardManager;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class Game {

    public String name;
    public Arena arena;
    public GameState state = GameState.NOT_SETUP;

    private ScoreboardManager scoreboardManager;

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

    private Location sign;

    private FileConfiguration arenaFile = BowSpleef.arenaFileConfiguration;

    public Game(String name) {
        this.name = name;
        scoreboardManager = new ScoreboardManager(this);
        arena = new Arena();
        load();
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

        if (state == GameState.LOBBY || state == GameState.STARTING) {

            if (players.size() >= maxPlayers) {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game is already full.");
                return false;
            }

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

            GameJoinEvent event = new GameJoinEvent(player, this);
            Bukkit.getServer().getPluginManager().callEvent(event);

            players.add(player);

            // TODO: Statistics

            msgAll(MessageManager.MessageType.SUCCESS, player.getDisplayName() + ChatColor.AQUA + " has joined the game! " +
                    ChatColor.GRAY + "(" + ChatColor.YELLOW + getPlayers().size() + ChatColor.GRAY + "/" +
                    ChatColor.YELLOW + maxPlayers + ChatColor.GRAY + ")");

            if (getPlayers().size() == getMinPlayers() && getState() == GameState.LOBBY) {
                msgAll(MessageManager.MessageType.INFO, "Minimum player count reached. Starting soon."); // TODO: Redo message
                start();
            }

            updateSign();
            updateScoreboard();

            return true;

        } else if (state == GameState.IN_GAME) {

            if (arena.getSpectatorSpawn() == null) {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "Spectator spawn has not been set.");
                return false;
            }

            MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "Joining BowSpleef game: " + ChatColor.AQUA + name
                + ChatColor.GRAY + ", as a spectator.");

            gameModeStorage.put(player, player.getGameMode().getValue());
            foodLevelStorage.put(player, player.getFoodLevel());
            healthStorage.put(player, player.getHealth());

            player.setHealth(20.0);
            player.setGameMode(GameMode.SPECTATOR);
            player.setFoodLevel(20);

            saveInventory(player);
            player.getInventory().clear();

            prevLocationStorage.put(player, player.getLocation());
            player.teleport(arena.getSpectatorSpawn());

            GameSpectateEvent event = new GameSpectateEvent(player, this);
            Bukkit.getServer().getPluginManager().callEvent(event);

            spectators.add(player);

            msgAll(MessageManager.MessageType.INFO, player.getName() + ChatColor.AQUA + " is spectating this game!");

            updateSign();
            updateScoreboard();

            return true;

        } else {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "This game is currently unavailable.");
            return false;
        }

    }

    public boolean removePlayer(Player player) {

        if (GameManager.getInstance().getGame(player) == null) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You are not currently in a game.");
            return false;
        }

        player.setFoodLevel(foodLevelStorage.get(player));
        player.setGameMode(GameMode.getByValue(gameModeStorage.get(player)));
        player.setHealth(healthStorage.get(player));
        player.teleport(prevLocationStorage.get(player));

        foodLevelStorage.remove(player);
        gameModeStorage.remove(player);
        healthStorage.remove(player);
        prevLocationStorage.remove(player);

        retrieveInventory(player);

        if (votes.contains(player))
            votes.remove(player);

        if (players.contains(player)) {
            if (players.size() != 1 && getState() == GameState.IN_GAME) {
                msgAll(MessageManager.MessageType.SUB_INFO, player.getName() + ChatColor.GRAY + " has lost!");
            } else if (players.size() == 1 && getState() == GameState.IN_GAME) {
                MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "You won this round of BowSpleef!"); // TODO: Look into
            } else {
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "You have left the game.");
            }

            players.remove(player);
        }

        if (spectators.contains(player)) {
            MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "You have stopped spectating this game.");
            spectators.remove(player);
        }

        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        updateScoreboard();
        updateSign();

        GameLeaveEvent event = new GameLeaveEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        if (players.size() == 0 && getState() == GameState.IN_GAME) {
            if (spectators.size() > 0)
                for (Player spectator : spectators) {
                    removePlayer(spectator);
                }

            end();
        }

        return true;
    }

    public boolean removePlayerNoWin(Player player) {

        if (GameManager.getInstance().getGame(player) == null) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You are not currently in a game.");
            return false;
        }

        player.setFoodLevel(foodLevelStorage.get(player));
        player.setGameMode(GameMode.getByValue(gameModeStorage.get(player)));
        player.setHealth(healthStorage.get(player));
        player.teleport(prevLocationStorage.get(player));

        foodLevelStorage.remove(player);
        gameModeStorage.remove(player);
        healthStorage.remove(player);
        prevLocationStorage.remove(player);

        retrieveInventory(player);

        if (votes.contains(player))
            votes.remove(player);

        if (players.contains(player)) {
            players.remove(player);
            MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "You have left the game.");
        }

        if (spectators.contains(player)) {
            MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "You have stopped spectating this game.");
            spectators.remove(player);
        }

        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        GameLeaveEvent event = new GameLeaveEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        return true;
    }

    public boolean vote(Player player) {

        if (!player.hasPermission("bowspleef.player.vote")) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have permission to join this game.");
            return false;
        }

        if (GameManager.getInstance().getGame(player) == null) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You are not currently in a game.");
            return false;
        }

        if (getState() != GameState.LOBBY) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "Voting can only take place while in lobby.");
            return false;
        }

        if (getVotes().contains(player)) {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You can only vote once.");
            return false;
        }

        votes.add(player);
        MessageManager.msg(MessageManager.MessageType.INFO, player, "You have voted to start the game.");

        GameVoteEvent event = new GameVoteEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        updateScoreboard();

        if (getPlayers().size() >= 2) {
            int votesNeeded = (int) Math.round(getMinPlayers() * 0.66);

            if (getVotes().size() >= votesNeeded) {
                start();
                updateScoreboard();
            }
        }

        return true;
    }

    public void start() {
        state = GameState.STARTING;

        GameStartEvent event = new GameStartEvent(this);
        Bukkit.getPluginManager().callEvent(event);

        msgAll(MessageManager.MessageType.INFO, "Game starting in 15 seconds...");
        new Countdown(this).runTaskTimer(BowSpleef.getInstance(), 0L, 20L);
    }

    public void end() {
        state = GameState.RESETTING;
        arena.regen();
        state = GameState.LOBBY;
    }

    public void enable() {
        if (arena.getPos1() == null)
            return;

        if (arena.getPos2() == null)
            return;

        if (arena.getSpectatorSpawn() == null)
            return;

        if (arena.getLobby() == null)
            return;

        if (arena.getSpawn() == null)
            return;

        setState(GameState.RESETTING);
        arena.regen();
        setState(GameState.LOBBY);
        updateSign();
    }

    public void disable() {
        setState(GameState.DISABLED);
        updateSign();
    }

    public void giveItems(Player player) {
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOW " + ChatColor.GRAY.toString() +
            ChatColor.ITALIC + "- Classic");
        bow.setItemMeta(bowMeta);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        bow.addEnchantment(Enchantment.ARROW_FIRE, 1);

        player.getInventory().addItem(bow);
        player.getInventory().addItem(new ItemStack(Material.ARROW));
    }

    public void load() {
        BowSpleef.loadConfigurationFiles();

        if (arenaFile.contains("arenas." + name + ".spawn")) {
            String world = arenaFile.getString("arenas." + name + ".spawn.world");
            int x = arenaFile.getInt("arenas." + name + ".spawn.x");
            int y = arenaFile.getInt("arenas." + name + ".spawn.y");
            int z = arenaFile.getInt("arenas." + name + ".spawn.z");

            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            arena.setSpawn(location);
        }

        if (arenaFile.contains("arenas." + name + ".lobby")) {
            String world = arenaFile.getString("arenas." + name + ".lobby.world");
            int x = arenaFile.getInt("arenas." + name + ".lobby.x");
            int y = arenaFile.getInt("arenas." + name + ".lobby.y");
            int z = arenaFile.getInt("arenas." + name + ".lobby.z");

            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            arena.setLobby(location);
        }

        if (arenaFile.contains("arenas." + name + ".pos1")) {
            String world = arenaFile.getString("arenas." + name + ".pos1.world");
            int x = arenaFile.getInt("arenas." + name + ".pos1.x");
            int y = arenaFile.getInt("arenas." + name + ".pos1.y");
            int z = arenaFile.getInt("arenas." + name + ".pos1.z");

            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            arena.setPos1(location);
        }

        if (arenaFile.contains("arenas." + name + ".pos2")) {
            String world = arenaFile.getString("arenas." + name + ".pos2.world");
            int x = arenaFile.getInt("arenas." + name + ".pos2.x");
            int y = arenaFile.getInt("arenas." + name + ".pos2.y");
            int z = arenaFile.getInt("arenas." + name + ".pos2.z");

            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            arena.setPos2(location);
        }

        if (arenaFile.contains("arenas." + name + ".specspawn")) {
            String world = arenaFile.getString("arenas." + name + ".specspawn.world");
            int x = arenaFile.getInt("arenas." + name + ".specspawn.x");
            int y = arenaFile.getInt("arenas." + name + ".specspawn.y");
            int z = arenaFile.getInt("arenas." + name + ".specspawn.z");

            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            arena.setSpectatorSpawn(location);
        }

        if (arenaFile.contains("arenas." + name + ".sign")) {
            String world = arenaFile.getString("arenas." + name + ".sign.world");
            int x = arenaFile.getInt("arenas." + name + ".sign.x");
            int y = arenaFile.getInt("arenas." + name + ".sign.y");
            int z = arenaFile.getInt("arenas." + name + ".sign.z");

            Location location = new Location(Bukkit.getWorld(world), x, y, z);
            setSign(location);
        }

        if (arenaFile.contains("arenas." + name + ".min-players")) {
            minPlayers = arenaFile.getInt("arenas." + name + ".min-players");
        }

        if (arenaFile.contains("arenas." + name + ".max-players")) {
            maxPlayers = arenaFile.getInt("arenas." + name + ".max-players");
        }

        enable();
    }

    public void save() {
        state = GameState.DISABLED;

        for (Player player : players) {
            removePlayerNoWin(player);
        }

        for (Player player : spectators) {
            removePlayerNoWin(player);
        }

        if (arena.getSpawn() != null) {
            arenaFile.set("arenas." + name + ".spawn.world", arena.getSpawn().getWorld().getName());
            arenaFile.set("arenas." + name + ".spawn.x", arena.getSpawn().getBlockX());
            arenaFile.set("arenas." + name + ".spawn.y", arena.getSpawn().getBlockY());
            arenaFile.set("arenas." + name + ".spawn.z", arena.getSpawn().getBlockZ());
        }

        if (arena.getPos1() != null) {
            arenaFile.set("arenas." + name + ".pos1.world", arena.getPos1().getWorld().getName());
            arenaFile.set("arenas." + name + ".pos1.x", arena.getPos1().getBlockX());
            arenaFile.set("arenas." + name + ".pos1.y", arena.getPos1().getBlockY());
            arenaFile.set("arenas." + name + ".pos1.z", arena.getPos1().getBlockZ());
        }

        if (arena.getPos2() != null) {
            arenaFile.set("arenas." + name + ".pos2.world", arena.getPos2().getWorld().getName());
            arenaFile.set("arenas." + name + ".pos2.x", arena.getPos2().getBlockX());
            arenaFile.set("arenas." + name + ".pos2.y", arena.getPos2().getBlockY());
            arenaFile.set("arenas." + name + ".pos2.z", arena.getPos2().getBlockZ());
        }

        if (arena.getLobby() != null) {
            arenaFile.set("arenas." + name + ".lobby.world", arena.getLobby().getWorld().getName());
            arenaFile.set("arenas." + name + ".lobby.x", arena.getLobby().getBlockX());
            arenaFile.set("arenas." + name + ".lobby.y", arena.getLobby().getBlockY());
            arenaFile.set("arenas." + name + ".lobby.z", arena.getLobby().getBlockZ());
        }

        if (arena.getSpectatorSpawn() != null) {
            arenaFile.set("arenas." + name + ".specspawn.world", arena.getSpectatorSpawn().getWorld().getName());
            arenaFile.set("arenas." + name + ".specspawn.x", arena.getSpectatorSpawn().getBlockX());
            arenaFile.set("arenas." + name + ".specspawn.y", arena.getSpectatorSpawn().getBlockY());
            arenaFile.set("arenas." + name + ".specspawn.z", arena.getSpectatorSpawn().getBlockZ());
        }

        if (getSign() != null) {
            arenaFile.set("arenas." + name + ".sign.world", getSign().getWorld().getName());
            arenaFile.set("arenas." + name + ".sign.x", getSign().getBlockX());
            arenaFile.set("arenas." + name + ".sign.y", getSign().getBlockY());
            arenaFile.set("arenas." + name + ".sign.z", getSign().getBlockZ());
        }

        arenaFile.set("arenas." + name + ".min-players", minPlayers);
        arenaFile.set("arenas." + name + ".max-players", maxPlayers);

        BowSpleef.saveConfigurationFiles();
    }

    public void updateSign() {
        if (getSign() == null)
            return;

        if (getSign().getBlock() instanceof Sign) {
            Sign sign = (Sign) getSign().getBlock();

            sign.setLine(0, ChatColor.AQUA + "[BowSpleef]");
            sign.setLine(1, getName());
            sign.setLine(2, state.getColor() + state.getName());
            sign.setLine(3, ChatColor.DARK_GREEN.toString() + players.size() + ChatColor.DARK_GRAY + "/" +
                    ChatColor.DARK_GREEN.toString() + maxPlayers);

            sign.update();
        }
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

    public void msgAll(MessageManager.MessageType type, String message) {
        for (Player player : getPlayers()) {
            MessageManager.msg(type, player, message);
        }

        for (Player player : getSpectators()) {
            MessageManager.msg(type, player, message);
        }
    }

    public void updateScoreboard() {
        for (Player player : getPlayers()) {
            scoreboardManager.applyScoreboard(player);
        }

        for (Player player : getPlayers()) {
            scoreboardManager.applyScoreboard(player);
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

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
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

    public HashMap<Player, ItemStack[][]> getInventoryStorage() {
        return inventoryStorage;
    }

    public HashMap<Player, Integer> getGameModeStorage() {
        return gameModeStorage;
    }

    public HashMap<Player, Integer> getFoodLevelStorage() {
        return foodLevelStorage;
    }

    public HashMap<Player, Double> getHealthStorage() {
        return healthStorage;
    }

    public HashMap<Player, Location> getPrevLocationStorage() {
        return prevLocationStorage;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public FileConfiguration getArenaFile() {
        return arenaFile;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public Location getSign() {
        return sign;
    }

    public void setSign(Location sign) {
        this.sign = sign;
    }

    public enum GameState {
        DISABLED("Disabled", 0, ChatColor.DARK_RED),
        LOADING("Loading", 1, ChatColor.GOLD),
        LOBBY("Lobby", 2, ChatColor.GREEN),
        STARTING("Starting", 3, ChatColor.DARK_GREEN),
        IN_GAME("In Game", 4, ChatColor.DARK_RED),
        RESETTING("Resetting", 5, ChatColor.DARK_RED),
        INACTIVE("Inactive", 6, ChatColor.DARK_RED),
        NOT_SETUP("Not Setup", 7, ChatColor.DARK_RED),
        ERROR("Error", 8, ChatColor.DARK_RED);

        private String name;
        private int id;
        private ChatColor color;

        GameState(String name, int id, ChatColor color) {
            this.name = name;
            this.id = id;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public ChatColor getColor() {
            return color;
        }

    }

}
