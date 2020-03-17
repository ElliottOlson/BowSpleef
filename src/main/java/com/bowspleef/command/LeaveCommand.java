package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;

public class LeaveCommand extends Command {

    public LeaveCommand() {
        setName("leave");
        setAlias("l");
        setUsage("");
        setPermission("bowspleef.player.leave");
        setDescription("Leave a BowSpleef game.");
        setBePlayer(true);
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1) {
            Game game = GameManager.getInstance().getGame(player);

            if (game != null) {
                game.removePlayer(player);
                return CommandResult.SUCCESS;
            }

            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You are not currently in a game.");
            return CommandResult.FAIL;
        }

        return CommandResult.INVALID_USAGE;
    }

}
