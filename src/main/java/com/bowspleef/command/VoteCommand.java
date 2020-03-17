package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;

public class VoteCommand extends Command {

    public VoteCommand() {
        setName("vote");
        setAlias("v");
        setDescription("Vote to start a game.");
        setBePlayer(true);
        setPermission("bowspleef.player.vote");
        setUsage("");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 1) {
            Game game = GameManager.getInstance().getGame(player);

            if (game != null) {
                if (game.vote(player)) {
                    return CommandResult.SUCCESS;
                }
            }

            return CommandResult.FAIL;
        }

        return CommandResult.INVALID_USAGE;
    }
}
