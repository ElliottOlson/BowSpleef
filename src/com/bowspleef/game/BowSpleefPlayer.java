package com.bowspleef.game;

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

}

