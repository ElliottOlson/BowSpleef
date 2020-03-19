package com.bowspleef.kit;

import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JumperKit extends Kit {

    @Override
    public String getName() {
        return "Jumper";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GOLD;
    }

    @Override
    public Material getIcon() {
        return Material.GOLDEN_BOOTS;
    }

    @Override
    public int getCost() {
        return 625;
    }

    @Override
    public ItemStack getSpecialItem() {
        ItemStack specialItem = new ItemStack(Material.GOLDEN_BOOTS);
        ItemMeta specialItemMeta = specialItem.getItemMeta();
        specialItemMeta.setDisplayName(getColor().toString() + ChatColor.BOLD + "JUMP BOOST "
            + ChatColor.GRAY.toString() + ChatColor.ITALIC + "(RIGHT CLICK)");
        specialItem.setItemMeta(specialItemMeta);

        return specialItem;
    }

    @Override
    public void execute(Player player) {
        MessageManager.msg(MessageManager.MessageType.INFO, player, "You will have a jump boost for the next 5 seconds.");
        player.getInventory().remove(getSpecialItem());

        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 2));
    }

    @Override
    public int getCooldown() {
        return 0;
    }

}
