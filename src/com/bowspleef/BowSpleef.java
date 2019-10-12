package com.bowspleef;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BowSpleef extends JavaPlugin {

    public static File arenaFile = new File("arena.yml");
    public static FileConfiguration arenaFileConfiguration = YamlConfiguration.loadConfiguration(arenaFile);

    @Override
    public void onEnable() {

        saveDefaultConfig();
        loadConfigurationFiles();

        getLogger().info("BowSpleef is enabled.");
    }

    @Override
    public void onDisable() {

        saveConfigurationFiles();

        getLogger().info("BowSpleef is disabled.");
    }

    public static void saveConfigurationFiles() {
        try {
            arenaFileConfiguration.save(arenaFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadConfigurationFiles() {
        try {
            arenaFileConfiguration.load(arenaFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
