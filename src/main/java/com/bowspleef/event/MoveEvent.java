package com.bowspleef.event;

import com.bowspleef.game.Game;
import com.bowspleef.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        Game game = GameManager.getInstance().getGame(player);


        if (game != null) {
            if (game.getState() == Game.GameState.IN_GAME &&
                    player.getLocation().getY() <= game.getArena().getPos1().getY()) {
                game.removePlayer(player);
            }
        }
    }

}
