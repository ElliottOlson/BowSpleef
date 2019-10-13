package com.bowspleef.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        BowSpleefPlayer player = (BowSpleefPlayer) e.getPlayer();

        if (player.isInGame()) {
            Game game = player.getGame();
            Arena arena = game.getArena();

            if (game.getState() == Game.GameState.INGAME && player.getLocation().getBlockY() < arena.getPos1().getBlockY()) {
                game.leave(player);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        BowSpleefPlayer player = (BowSpleefPlayer) e.getPlayer();

        if (player.isInGame()) {
            Game game = player.getGame();
            game.leave(player);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            BowSpleefPlayer player = (BowSpleefPlayer) e.getEntity();

            if (player.isInGame()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            BowSpleefPlayer player = (BowSpleefPlayer) e.getEntity();

            if (player.isInGame()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        BowSpleefPlayer player = (BowSpleefPlayer) e.getPlayer();

        if (player.isInGame()) {
            e.setCancelled(true);
            player.sendMessage(BowSpleefPlayer.MessageType.ERROR, "You cannot build while playing BowSpleef.");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        BowSpleefPlayer player = (BowSpleefPlayer) e.getPlayer();

        if (player.isInGame()) {
            e.setCancelled(true);
            player.sendMessage(BowSpleefPlayer.MessageType.ERROR, "You cannot break blocks while playing BowSpleef.");
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        BowSpleefPlayer player = (BowSpleefPlayer) e.getPlayer();

        if (player.isInGame()) {
            e.setCancelled(true);
            player.sendMessage(BowSpleefPlayer.MessageType.ERROR, "You cannot drop items while playing BowSpleef.");
        }
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        BowSpleefPlayer player = (BowSpleefPlayer) e.getPlayer();

        if (player.isInGame()) {
            e.setCancelled(true);
        }
    }

}
