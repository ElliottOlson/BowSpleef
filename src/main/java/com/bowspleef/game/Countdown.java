package com.bowspleef.game;

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
            game.msgAll(MessageManager.MessageType.SUB_INFO, "You will be teleported in " +
                    ChatColor.DARK_AQUA + time + ChatColor.GRAY + " seconds...");
        }

        if (time == 0) {
            game.state = Game.GameState.IN_GAME;

            for (Player player : game.getPlayers()) {
                player.teleport(game.getArena().getSpawn());
                game.giveItems(player);
            }

            cancel();
        }

        game.updateScoreboard();
        game.updateSign();

        time--;
    }
}
