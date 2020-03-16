package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;

public class RegenCommand extends Command {

    public RegenCommand() {
        setName("regen");
        setAlias("r");
        setDescription("Reset the floor of an arena");
        setBePlayer(false);
        setPermission("bowspleef.admin.regen");
        setUsage("[Name]");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2) {
            String name = getArgs().get(1);
            Game game = GameManager.getInstance().getGame(name);

            if (game != null) {

                game.arena.regen();
                MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The arena, " +
                        ChatColor.AQUA + name + ChatColor.GRAY + ", has been reset.");

                return CommandResult.SUCCESS;
            }

            MessageManager.msg(MessageManager.MessageType.ERROR, player, "The arena, " +
                    ChatColor.AQUA + name + ChatColor.GRAY + ", does not exist.");
            return CommandResult.FAIL;
        }

        return CommandResult.INVALID_USAGE;
    }

}
