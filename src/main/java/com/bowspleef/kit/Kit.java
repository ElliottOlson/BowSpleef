package com.bowspleef.kit;

import com.bowspleef.manager.ConfigurationManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Kit {

    public abstract String getName();

    public abstract ChatColor getColor();

    public abstract ItemStack getIcon();

    public abstract int getCost();

    public abstract ItemStack getBow();

    public abstract ItemStack getSpecialItem();

    public abstract void execute(Player player);

    public abstract int getCooldown();

    public boolean give(Player player) {
        if (isUnlocked(player)) {
            if (getBow() != null) player.getInventory().addItem(getBow());
            if (getSpecialItem() != null) player.getInventory().addItem(getSpecialItem());

            player.getInventory().setItem(8, new ItemStack(Material.ARROW));

            MessageManager.msg(MessageManager.MessageType.INFO, player, "You have received the kit: " + getColor() + getName());

            return true;
        }

        return false;
    }

    public boolean buy(Player player) {
        if (!isUnlocked(player)) {
            if (ConfigurationManager.getStatsConfig().contains(player.getUniqueId() + ".points")) {
                int points = ConfigurationManager.getStatsConfig().getInt(player.getUniqueId() + ".points");

                if (points >= getCost()) {
                    // Add kit to config
                    List<String> kits = ConfigurationManager.getStatsConfig().getStringList(player.getUniqueId() + ".kits");
                    kits.add(getName());
                    ConfigurationManager.getStatsConfig().set(player.getUniqueId() + ".kits", kits);

                    int remainingPoints = points - getCost();

                    ConfigurationManager.getStatsConfig().set(player.getUniqueId() + ".points", remainingPoints);
                    ConfigurationManager.saveConfig();

                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "You have unlocked the kit: " + getColor() + getName());
                    //TODO: Update scoreboard?
                } else {
                    MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have the sufficient funds to unlock the kit: " + getColor() + getName());
                }
            }
        } else {
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You have already unlocked the kit: " + getColor() + getName());
        }

        return false;
    }

    public boolean isUnlocked(Player player) {

        if (ConfigurationManager.getStatsConfig().contains(player.getUniqueId() + ".kits")) {
            List<String> kits = ConfigurationManager.getStatsConfig().getStringList(player.getUniqueId() + ".kits");

            if (kits.contains(getName())) {
                return true;
            }
        }

        return false;
    }

}
