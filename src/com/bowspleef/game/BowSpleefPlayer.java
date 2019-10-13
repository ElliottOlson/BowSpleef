package com.bowspleef.game;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class BowSpleefPlayer implements Player {

    private Game game;
    private boolean inGame;
    private boolean spectator;

    private Inventory inventory;

    //TODO: Implement statistics
    //TODO: Implement preferred language

    public void sendMessage(MessageType messageType, String message) {
        String prefix = messageType.getPrefix();
        sendMessage(prefix + ChatColor.GRAY + message);
    }

    public void saveInventory() {
        ItemStack[] armour = getInventory().getArmorContents();
        ItemStack[] contents = getInventory().getContents();
        float exp = getExp();
        GameMode gameMode = getGameMode();
        int level = getLevel();

        inventory = new Inventory(contents, armour, exp, gameMode, level);
    }

    public void retrieveInventory() {
        getInventory().setArmorContents(inventory.getArmour());
        getInventory().setContents(inventory.getContents());
        setExp(inventory.getExp());
        setGameMode(inventory.getGameMode());
        setLevel(inventory.getLevel());
    }

    public boolean isInGame() {
        return game != null;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isSpectator() {
        return spectator;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    private class Inventory {

        private ItemStack[] contents;
        private ItemStack[] armour;
        private float exp;
        private GameMode gameMode;
        private int level;

        public Inventory(ItemStack[] contents, ItemStack[] armour, float exp, GameMode gameMode, int level) {
            this.contents = contents;
            this.armour = armour;
            this.exp = exp;
            this.gameMode = gameMode;
            this.level = level;
        }

        public ItemStack[] getContents() {
            return contents;
        }

        public ItemStack[] getArmour() {
            return armour;
        }

        public float getExp() {
            return exp;
        }

        public GameMode getGameMode() {
            return gameMode;
        }

        public int getLevel() {
            return level;
        }
    }

    public enum MessageType {

        INFO(ChatColor.GOLD.toString() + ChatColor.BOLD + ">> "),
        ERROR(ChatColor.RED.toString() + ChatColor.BOLD + ">> "),
        SUCCESS(ChatColor.GREEN.toString() + ChatColor.BOLD + ">> "),
        SUB_INFO(ChatColor.GOLD.toString() + ChatColor.BOLD + "> ");

        private String prefix;

        MessageType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return this.prefix;
        }

    }

}

