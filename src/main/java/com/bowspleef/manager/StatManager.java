package com.bowspleef.manager;

import org.bukkit.entity.Player;

import java.util.UUID;

public class StatManager {

    private static StatManager instance;

    public int getStat(UUID uuid, StatType statType) {
        if (ConfigurationManager.getStatsConfig().contains(uuid.toString() + "." + statType.getName())) {
            return ConfigurationManager.getStatsConfig().getInt(uuid.toString() + "." + statType.getName());
        }

        return 0;
    }

    public void increment(UUID uuid, StatType statType) {
        int currentValue = getStat(uuid, statType);
        setStat(uuid, statType, currentValue+1);
    }

    public void setStat(UUID uuid, StatType statType, int value) {
        ConfigurationManager.getStatsConfig().set(uuid.toString() + "." + statType.getName(), value);
    }

    public static StatManager getInstance() {
        if (instance == null)
            instance = new StatManager();

        return instance;
    }

    public enum StatType {
        ARROW_SHOT("arrows_shot"),
        GAMES_WON("games_won"),
        GAMES_PLAYED("games_played");

        private String name;

        StatType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
