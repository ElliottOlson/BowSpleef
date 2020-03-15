package com.bowspleef.event;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Game game = GameManager.getInstance().getGame(player);

        if (game != null) {

            // TODO: Remove player from game

        }
    }

}
