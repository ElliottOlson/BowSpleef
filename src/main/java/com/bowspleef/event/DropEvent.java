package com.bowspleef.event;

import com.bowspleef.game.GameManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();

        if (GameManager.getInstance().getGame(player) != null) {
            e.setCancelled(true);
            MessageManager.msg(MessageManager.MessageType.ERROR, player, "You cannot drop items while playing BowSpleef");
        }

    }

}
