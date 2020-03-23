package com.bowspleef.manager;

import org.bukkit.entity.Player;

public class StatManager {

    private static StatManager instance;

    public int getStat(Player player, StatType statType) {
        if (ConfigurationManager.getStatsConfig().contains(player.getUniqueId().toString() + "." + statType.getName())) {
            return ConfigurationManager.getStatsConfig().getInt(player.getUniqueId().toString() + "." + statType.getName());
        }

        return 0;
    }

    public void increment(Player player, StatType statType) {
        int currentValue = getStat(player, statType);
        setStat(player, statType, currentValue+1);
    }

    public void setStat(Player player, StatType statType, int value) {
        ConfigurationManager.getStatsConfig().set(player.getUniqueId().toString() + "." + statType.getName(), value);
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
