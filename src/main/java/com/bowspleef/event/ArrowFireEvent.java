package com.bowspleef.event;

import com.bowspleef.game.GameManager;
import com.bowspleef.manager.StatManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ArrowFireEvent implements Listener {

    @EventHandler
    public void onFire(ProjectileLaunchEvent e) {
        if (e.getEntity().getShooter() instanceof Player) {
            Player player = (Player) e.getEntity().getShooter();

            if (GameManager.getInstance().getGame(player) != null) {
                StatManager.getInstance().increment(player.getUniqueId(), StatManager.StatType.ARROW_SHOT);
            }
        }
    }

}
