package com.bowspleef.manager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageManager {

    public static void msg(MessageType type, Player player, String message) {
        player.sendMessage(type.getPrefix() + ChatColor.GRAY + message);
    }

    public enum MessageType {

        ERROR(ChatColor.RED.toString() + ChatColor.BOLD.toString() + ">> "),
        SUCCESS(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + ">> "),
        INFO(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() + ">> "),
        SUB_INFO(ChatColor.GOLD.toString() + ChatColor.BOLD + "> ");

        String prefix;

        MessageType(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }
    }

}
