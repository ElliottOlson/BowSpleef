package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;

public class DeleteCommand extends Command {

    public DeleteCommand() {
        setName("delete");
        setAlias("d");
        setDescription("Delete an arena.");
        setBePlayer(true);
        setPermission("bowspleef.admin.game.delete");
        setUsage("[Name]");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2) {
            String name = getArgs().get(1);

            if (GameManager.getInstance().deleteGame(name)) {
                MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The arena, " +
                        ChatColor.AQUA + name + ChatColor.GRAY + ", has been deleted.");
                return CommandResult.SUCCESS;
            }

            MessageManager.msg(MessageManager.MessageType.ERROR, player, "The arena, " +
                    ChatColor.AQUA + name + ChatColor.GRAY + ", does not exist.");
            return CommandResult.FAIL;
        }

        return CommandResult.INVALID_USAGE;
    }

}
