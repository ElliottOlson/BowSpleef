package com.bowspleef.command;

import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;

public class CreateCommand extends Command {

    public CreateCommand() {
        setName("create");
        setAlias("c");
        setDescription("Create a game");
        setUsage("[Name]");
        setPermission("bowspleef.admin.game.create");
        setBePlayer(true);
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2) {

            String name = getArgs().get(1);

            if (GameManager.getInstance().createGame(name)) {
                MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The arena, " +
                        ChatColor.AQUA + name + ChatColor.GRAY + ", has been created.");
                return CommandResult.SUCCESS;
            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "The arena, " +
                        ChatColor.AQUA + name + ChatColor.GRAY + ", already exists.");
                return CommandResult.FAIL;
            }

        }

        return CommandResult.INVALID_USAGE;
    }
}
