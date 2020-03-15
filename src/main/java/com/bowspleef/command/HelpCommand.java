package com.bowspleef.command;

import org.bukkit.ChatColor;

public class HelpCommand extends Command {

    public HelpCommand() {
        setName("help");
        setAlias("?");
        setBePlayer(true);
        setPermission("bowspleef.player.help");
        setUsage("");
        setDescription("View BowSpleef commands.");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1) {

            player.sendMessage(ChatColor.GOLD.toString() + ChatColor.BOLD + ">> " +
                    ChatColor.GRAY + "Displaying BowSpleef commands...");
            Commands.displayCommands(player);

            return CommandResult.SUCCESS;

        }

        return CommandResult.INVALID_USAGE;
    }

}
