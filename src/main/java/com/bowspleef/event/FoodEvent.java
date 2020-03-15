package com.bowspleef.event;

import com.bowspleef.game.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodEvent implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            if (GameManager.getInstance().getGame(player) != null) {
                e.setCancelled(true);
            }
        }

    }

}
