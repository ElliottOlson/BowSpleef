package com.bowspleef.kit;

import com.bowspleef.manager.ConfigurationManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class Kit {

    public abstract String getName();

    public abstract ChatColor getColor();

    public abstract Material getIcon();

    public abstract int getCost();

    public abstract ItemStack getSpecialItem();

    public abstract void execute(Player player);

    public abstract int getCooldown();

    public boolean give(Player player) {
        if (isUnlocked(player)) {
            ItemStack bow = new ItemStack(Material.BOW);
            ItemMeta bowMeta = bow.getItemMeta();
            bowMeta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BOW" + ChatColor.GRAY.toString() +
                    ChatColor.ITALIC + " - " + getName());
            bow.setItemMeta(bowMeta);
            bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
            bow.addEnchantment(Enchantment.ARROW_FIRE, 1);

            player.getInventory().setItem(0, bow);

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
