package com.bowspleef.game;

import com.bowspleef.BowSpleef;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    public static GameManager instance;
    private ArrayList<Game> games = new ArrayList<>();
    private FileConfiguration arenaFile = BowSpleef.arenaFileConfiguration;

    public void setup() {
        loadGames();
    }

    public void loadGames() {
        games.clear();

        for (String name : arenaFile.getStringList("list.games")) {
            Game game = new Game(name);
            game.setup();
            games.add(game);
        }
    }

    public Game getGame(String name) {
        for (Game game : games) {
            if (game.getName().equalsIgnoreCase(name)) {
                return game;
            }
        }

        return null;
    }

    public Game getGame(Player player) {
        for (Game game : games) {
            if (game.getPlayers().contains(player) || game.getSpectators().contains(player)) {
                return game;
            }
        }

        return null;
    }

    public boolean isPlayer(Player player) {
        for (Game game : games) {
            if (game.getPlayers().contains(player)) {
                return true;
            }
        }

        return false;
    }

    public boolean isSpectator(Player player) {
        for (Game game : games) {
            if (game.getSpectators().contains(player)) {
                return true;
            }
        }

        return false;
    }

    public int getGameCount() {
        return games.size();
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();

        return instance;
    }

}
