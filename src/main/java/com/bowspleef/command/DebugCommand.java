package com.bowspleef.command;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;

public class DebugCommand extends Command {

    public DebugCommand() {
        setName("debug");
        setAlias("d");
        setDescription("Debug an arena");
        setUsage("[Name]");
        setPermission("bowspleef.admin.debug");
        setBePlayer(false);
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 2) {

            String name = getArgs().get(1);

            if (GameManager.getInstance().getGame(name) != null) {
                Game game = GameManager.getInstance().getGame(name);

                MessageManager.msg(MessageManager.MessageType.INFO, player, "Displaying debug information for game: " + ChatColor.AQUA + name);
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "State: " + ChatColor.YELLOW + game.getState().name());
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Min Players: " + ChatColor.YELLOW + game.getMinPlayers());
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Max Players: " + ChatColor.YELLOW + game.getMaxPlayers());
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Spawn: " + ChatColor.YELLOW + (game.arena.getSpawn() != null ? "Setup" : "Not Setup"));
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Pos1: " + ChatColor.YELLOW + (game.arena.getPos1() != null ? "Setup" : "Not Setup"));
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Pos2: " + ChatColor.YELLOW + (game.arena.getPos2() != null ? "Setup" : "Not Setup"));
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Lobby: " + ChatColor.YELLOW + (game.arena.getLobby() != null ? "Setup" : "Not Setup"));
                MessageManager.msg(MessageManager.MessageType.SUB_INFO, player, "Specspawn: " + ChatColor.YELLOW + (game.arena.getSpectatorSpawn() != null ? "Setup" : "Not Setup"));

            }

            return CommandResult.FAIL;

        }

        return CommandResult.INVALID_USAGE;
    }

}
