package com.bowspleef.command;

import com.bowspleef.manager.MessageManager;
import com.bowspleef.manager.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class StatCommand extends Command {

    public StatCommand() {
        setName("stat");
        setPermission("bowspleef.player.stat");
        setBePlayer(true);
        setDescription("View the stats of a BowSpleef player.");
        setUsage("[Player] [StatType]");
    }

    @Override
    public CommandResult execute() {

        if (getArgs().size() == 3) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(getArgs().get(1));
            UUID uuid = player.getUniqueId();

            if (getArgs().get(2).equalsIgnoreCase("wins")) {
                MessageManager.msg(MessageManager.MessageType.SUCCESS, getPlayer(), "The player, " +
                        ChatColor.AQUA + getArgs().get(1) + ChatColor.GRAY + ", has won " + ChatColor.YELLOW +
                        StatManager.getInstance().getStat(uuid, StatManager.StatType.GAMES_WON) + ChatColor.GRAY + " games.");
                return CommandResult.SUCCESS;
            } else if (getArgs().get(2).equalsIgnoreCase("arrows")) {
                MessageManager.msg(MessageManager.MessageType.SUCCESS, getPlayer(), "The player, " +
                        ChatColor.AQUA + getArgs().get(1) + ChatColor.GRAY + ", has shot " + ChatColor.YELLOW +
                        StatManager.getInstance().getStat(uuid, StatManager.StatType.ARROW_SHOT) + ChatColor.GRAY + " arrows.");
                return CommandResult.SUCCESS;
            } else if (getArgs().get(2).equalsIgnoreCase("games")) {
                MessageManager.msg(MessageManager.MessageType.SUCCESS, getPlayer(), "The player, " +
                        ChatColor.AQUA + getArgs().get(1) + ChatColor.GRAY + ", has played " + ChatColor.YELLOW +
                        StatManager.getInstance().getStat(uuid, StatManager.StatType.GAMES_PLAYED) + ChatColor.GRAY + " games.");
                return CommandResult.SUCCESS;
            }

            return CommandResult.FAIL;
        }

        return CommandResult.INVALID_USAGE;
    }
}
