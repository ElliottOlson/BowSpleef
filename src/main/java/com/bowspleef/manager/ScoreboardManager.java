package com.bowspleef.manager;

import com.bowspleef.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager {

    private Game game;
    private org.bukkit.scoreboard.ScoreboardManager manager;

    public ScoreboardManager(Game game) {
        this.game = game;
        manager = Bukkit.getScoreboardManager();
    }

    public void applyScoreboard(Player player) {

        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("BowSpleef", "BowSpleef");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "BowSpleef");

        if (game.getState() == Game.GameState.WAITING) {

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME: ", 16);
            addLine(objective, ChatColor.WHITE + "Waiting: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(objective, ChatColor.WHITE + "Needed: " + ChatColor.RED + game.getMinPlayers(), 14);
            addLine(objective, ChatColor.WHITE + "Votes: " + ChatColor.YELLOW + game.getVotes().size(), 13);

            addLine(objective, "", 12);

            // TODO: Stats

        } else if (game.getState() == Game.GameState.STARTING) {

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME: ", 16);
            addLine(objective, ChatColor.WHITE + "Waiting: " + ChatColor.GREEN + game.getPlayers().size(), 15);

            addLine(objective, "", 14);

            // TODO: Stats

        } else if (game.getState() == Game.GameState.IN_GAME) {

            addLine(objective, ChatColor.GOLD.toString() + ChatColor.BOLD + "GAME: ", 16);
            addLine(objective, ChatColor.WHITE + "Players: " + ChatColor.GREEN + game.getPlayers().size(), 15);
            addLine(objective, ChatColor.WHITE + "Spectators: " + ChatColor.RED + game.getSpectators().size(), 14);

            addLine(objective, "", 13);

            // TODO: Stats

        }

        player.setScoreboard(scoreboard);

    }

    private void addLine(Objective objective, String text, int value) {
        Score score = objective.getScore(text);
        score.setScore(value);
    }
}
