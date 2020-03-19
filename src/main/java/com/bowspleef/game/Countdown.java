package com.bowspleef.game;

import com.bowspleef.kit.Kit;
import com.bowspleef.kit.KitManager;
import com.bowspleef.manager.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown extends BukkitRunnable {

    private Game game;
    private int time = 15;

    public Countdown(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        if (time == 10 || time == 5 || (time <= 3 && time > 0)) {
            if (time == 1) {
                game.msgAll(MessageManager.MessageType.SUB_INFO, "You will be teleported in " +
                        ChatColor.DARK_AQUA + time + ChatColor.GRAY + " second...");
            } else {
                game.msgAll(MessageManager.MessageType.SUB_INFO, "You will be teleported in " +
                        ChatColor.DARK_AQUA + time + ChatColor.GRAY + " seconds...");
            }
        }

        if (time == 0) {
            game.state = Game.GameState.IN_GAME;

            Kit classic = KitManager.getInstance().getKit("classic");

            for (Player player : game.getPlayers()) {
                player.teleport(game.getArena().getSpawn());
                Kit kit = KitManager.getInstance().getKit(player);

                if (kit != null) {
                    kit.give(player);
                } else {
                    classic.give(player);
                }
            }

            cancel();
        }

        if (game.getPlayers().size() == 0) {
            game.state = Game.GameState.LOBBY;
            cancel();
        }

        game.updateScoreboard();
        game.updateSign();

        time--;
    }

}
