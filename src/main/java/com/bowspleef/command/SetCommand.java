package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class SetCommand extends Command {

    public SetCommand() {
        setName("set");
        setAlias("s");
        setDescription("Set values for games"); // TODO: Rewrite this
        setUsage("[Game] [Option]");
        setBePlayer(true);
        setPermission("bowspleef.admin.set");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 3 || getArgs().size() == 4) {

            String name = getArgs().get(1);
            Game game = GameManager.getInstance().getGame(name);

            if (game == null) {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "The arena, " +
                        ChatColor.AQUA + name + ChatColor.GRAY + ", does not exist.");
                return CommandResult.FAIL;
            }

            if (getArgs().size() == 3) {

                String action = getArgs().get(2);

                if (action.equalsIgnoreCase("spawn")) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The spawn for arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been set.");
                    game.getArena().setSpawn(player.getLocation());
                    return CommandResult.SUCCESS;
                } else if (action.equalsIgnoreCase("lobby")) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The lobby for arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been set.");
                    game.getArena().setLobby(player.getLocation());
                    return CommandResult.SUCCESS;
                } else if (action.equalsIgnoreCase("pos1")) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The first corner for arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been set.");
                    game.getArena().setPos1(player.getLocation());
                    return CommandResult.SUCCESS;
                } else if (action.equalsIgnoreCase("pos2")) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The second corner for arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been set.");
                    game.getArena().setPos2(player.getLocation());
                    return CommandResult.SUCCESS;
                } else if (action.equalsIgnoreCase("specspawn")) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The spectator spawn for arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been set.");
                    game.getArena().setSpectatorSpawn(player.getLocation());
                    return CommandResult.SUCCESS;
                } else if (action.equalsIgnoreCase("enabled")) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been enabled.");
                    game.enable();
                    return CommandResult.SUCCESS;
                }

            } else {

                String action = getArgs().get(2);

                if (action.equalsIgnoreCase("minplayers")) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The minimum amount of players for arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been set.");
                    game.setMinPlayers(Integer.valueOf(getArgs().get(3)));
                    return CommandResult.SUCCESS;
                } else if (action.equalsIgnoreCase("maxplayers")) {
                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The maximum amount of players for arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been set.");
                    game.setMaxPlayers(Integer.valueOf(getArgs().get(3)));
                    return CommandResult.SUCCESS;
                }

            }

        }

        return CommandResult.INVALID_USAGE;
    }
}
