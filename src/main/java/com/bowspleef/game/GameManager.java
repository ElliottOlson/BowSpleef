package com.bowspleef.game;

import com.bowspleef.BowSpleef;
import com.bowspleef.manager.ConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public static GameManager instance;
    private ArrayList<Game> games = new ArrayList<>();
    private FileConfiguration arenaFile = ConfigurationManager.getArenaConfig();

    public void loadGames() {
        games.clear();

        if (arenaFile != null && arenaFile.contains("list.games")) {
            for (String name : arenaFile.getStringList("list.games")) {
                Game game = new Game(name);
                games.add(game);
            }
        }
    }

    public void saveGames() {
        ConfigurationManager.saveConfig();

        List<String> names = new ArrayList<>();
        for (Game game : games) {
            names.add(game.getName());
        }

        arenaFile.set("list.games", names);

        for (Game game : games) {
            game.save();
        }
    }

    public boolean createGame(String name) {

        if (getGame(name) != null)
            return false;

        Game game = new Game(name);
        games.add(game);

        return true;
    }

    public boolean deleteGame(String name) {

        if (getGame(name) == null)
            return false;

        Game game = getGame(name);
        game.disable();

        if (arenaFile.getStringList("list.games").contains(name)) {
            List<String> gameNames = new ArrayList<>();

            for (Game g : games) {
                if (g != game) {
                    gameNames.add(g.getName());
                }
            }

            arenaFile.set("list.games", gameNames);
        }

        if (arenaFile.contains("arenas." + name)) {
            arenaFile.set("arenas." + name, null);
        }

        games.remove(game);
        ConfigurationManager.saveConfig();

        return true;
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

    public ArrayList<Game> getGames() {
        return games;
    }

    public static GameManager getInstance() {
        if (instance == null)
            instance = new GameManager();

        return instance;
    }

}
