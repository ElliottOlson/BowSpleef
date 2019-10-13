package com.bowspleef.api;

import com.bowspleef.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameDeleteEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Game game;

    public GameDeleteEvent(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
