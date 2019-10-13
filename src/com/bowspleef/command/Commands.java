package com.bowspleef.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    private static List<Command> commandList = new ArrayList();

    public static List<Command> getCommandList() {
        return commandList;
    }

    public static void setCommandList(List<Command> commands) {
        commandList = commands;
    }

    public static void displayCommands(Player player) {
        for (Command command : commandList) {
            if (player.hasPermission(command.getPermission())) {
                player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + "> " + command.getDisplayUsage());
            }
        }
    }

}
