package com.bowspleef.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigurationManager {

    private static File arenas = new File("plugins/BowSpleef", "arenas.yml");
    private static FileConfiguration arenaConfig = YamlConfiguration.loadConfiguration(arenas);

    private static File stats = new File("plugins/BowSpleef", "stats.yml");
    private static FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(arenas);

    private static File main = new File("plugins/BowSpleef", "config.yml");
    private static FileConfiguration mainConfig = YamlConfiguration.loadConfiguration(arenas);

    public static void loadConfig(){
        try {
            arenaConfig.load(arenas);
            statsConfig.load(stats);
            mainConfig.load(main);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveConfig(){
        try {
            arenaConfig.save(arenas);
            statsConfig.save(stats);
            mainConfig.save(main);

            if (!mainConfig.contains("update.status")){
                mainConfig.set("update.status", true);
                mainConfig.save(main);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static FileConfiguration getArenaConfig() {
        return arenaConfig;
    }

    public static FileConfiguration getStatsConfig() {
        return statsConfig;
    }

    public static FileConfiguration getMainConfig() {
        return mainConfig;
    }
}
