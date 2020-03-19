package com.bowspleef.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClassicKit extends Kit {

    @Override
    public String getName() {
        return "Classic";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.DARK_AQUA;
    }

    @Override
    public Material getIcon() {
        return Material.BOW;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public ItemStack getSpecialItem() {
        return null;
    }

    @Override
    public void execute(Player player) {

    }

    @Override
    public int getCooldown() {
        return 0;
    }

}
