package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import net.md_5.bungee.api.ChatColor;

public class ListCommand extends Command {

    public ListCommand() {
        setName("list");
        setDescription("List all BowSpleef arenas.");
        setBePlayer(true);
        setPermission("bowspleef.player.list");
        setUsage("");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1) {

            MessageManager.msg(MessageManager.MessageType.INFO, player, "Displaying all BowSpleef games...");

            if (GameManager.getInstance().getGameCount() > 0) {
                for (Game game : GameManager.getInstance().getGames()) {
                    MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, ChatColor.WHITE + game.getName());
                }
            } else {
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, ChatColor.WHITE + "No games found");
            }

            return CommandResult.SUCCESS;

        }

        return CommandResult.INVALID_USAGE;
    }

}
