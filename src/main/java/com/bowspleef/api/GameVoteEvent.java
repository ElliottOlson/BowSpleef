package com.bowspleef.api;

import com.bowspleef.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class GameVoteEvent extends org.bukkit.event.Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Game game;

    public GameVoteEvent(Player player, Game game) {
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
