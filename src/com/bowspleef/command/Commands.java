package com.bowspleef.command;

import com.bowspleef.game.BowSpleefPlayer;
import org.bukkit.ChatColor;

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

    public static void displayCommands(BowSpleefPlayer player) {
        for (Command command : commandList) {
            if (player.hasPermission(command.getPermission())) {
                player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + ">> " + command.getDisplayUsage());
            }
        }
    }

}
