package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;

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

            return CommandResult.FAIL;
        }

        return CommandResult.INVALID_USAGE;
    }

}
