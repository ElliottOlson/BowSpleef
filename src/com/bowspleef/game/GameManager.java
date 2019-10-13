package com.bowspleef.game;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static GameManager instance;
    private List<Game> games = new ArrayList<>();

    public Game getGame(String name) {
        for (Game game : games) {
            if (game.getName().equalsIgnoreCase(name)) {
                return game;
            }
        }

        return null;
    }

    public List getGames() {
        return games;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }

        return instance;
    }

}
