package com.bowspleef.api;

import com.bowspleef.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameSpectateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Game game;

    public GameSpectateEvent(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
