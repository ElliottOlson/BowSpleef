package com.bowspleef.event;

import com.bowspleef.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickupEvent implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();

        if (GameManager.getInstance().getGame(player) != null) {
            e.setCancelled(true);
        }
    }

}
