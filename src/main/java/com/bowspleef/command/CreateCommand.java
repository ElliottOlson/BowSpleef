package com.bowspleef.command;

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

            // TODO: Create game (check to see if valid)

            return CommandResult.SUCCESS;

        }

        return CommandResult.INVALID_USAGE;
    }
}
