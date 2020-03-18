package com.bowspleef.event;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignEvent implements Listener {

    @EventHandler
    public void onChange(SignChangeEvent e) {
        Player player = e.getPlayer();

        if (e.getLine(0).equalsIgnoreCase("[BowSpleef]")
                && e.getLine(1).equalsIgnoreCase("Join")) {
            if (player.hasPermission("bowspleef.admin.sign.create")) {

                String name = e.getLine(2);
                Game game = GameManager.getInstance().getGame(name);

                if (game != null) {

                    MessageManager.msg(MessageManager.MessageType.SUCCESS, player, "The sign for arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", has been created.");

                    game.setSign(e.getBlock().getLocation());

                    e.setLine(0, ChatColor.AQUA + "[BowSpleef]");
                    e.setLine(1, name);
                    e.setLine(2, game.getState().getColor() + game.getState().getName());
                    e.setLine(3, ChatColor.DARK_GREEN.toString() + game.getPlayers().size() + ChatColor.DARK_GRAY + "/"
                        + ChatColor.DARK_RED.toString() + game.getMaxPlayers());
                } else {
                    MessageManager.msg(MessageManager.MessageType.ERROR, player, "The arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", does not exist.");
                }

            } else {
                MessageManager.msg(MessageManager.MessageType.ERROR, player, "You do not have permission to create a BowSpleef sign");
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getClickedBlock() == null) {
            return;
        }

        if (e.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) e.getClickedBlock().getState();

            if (e.getAction() == Action.RIGHT_CLICK_BLOCK && sign.getLine(0).equalsIgnoreCase(ChatColor.AQUA + "[BowSpleef]")) {
                String name = sign.getLine(1);
                Game game = GameManager.getInstance().getGame(name);

                if (game != null) {
                    game.addPlayer(player);
                } else {
                    MessageManager.msg(MessageManager.MessageType.ERROR, player, "The arena, " +
                            ChatColor.AQUA + name + ChatColor.GRAY + ", does not exist.");
                }
            }
        }
    }

}
