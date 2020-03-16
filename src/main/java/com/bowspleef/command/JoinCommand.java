package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;

public class JoinCommand extends Command {

    public JoinCommand() {
        setName("join");
        setAlias("j");
        setDescription("Join a BowSpleef game");
        setUsage("[Name]");
        setBePlayer(true);
        setPermission("bowspleef.player.join");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2) {
            String name = getArgs().get(1);
            Game game = GameManager.getInstance().getGame(name);

            if (game != null) {
                game.addPlayer(player);
                return CommandResult.SUCCESS;
            }

            MessageManager.msg(MessageManager.MessageType.ERROR, player, "The arena, " +
                    ChatColor.AQUA + name + ChatColor.GRAY + ", does not exist.");
            return CommandResult.FAIL;
        }

        return CommandResult.INVALID_USAGE;
    }

}
