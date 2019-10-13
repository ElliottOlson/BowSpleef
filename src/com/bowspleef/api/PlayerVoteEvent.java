package com.bowspleef.api;

import com.bowspleef.game.BowSpleefPlayer;
import com.bowspleef.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerVoteEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Game game;
    private BowSpleefPlayer player;

    public PlayerVoteEvent(Game game, BowSpleefPlayer player) {
        this.game = game;
        this.player = player;
    }

    public Game getGame() {
        return game;
    }

    public BowSpleefPlayer getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
