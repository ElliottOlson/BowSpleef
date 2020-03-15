package com.bowspleef;

import com.bowspleef.command.CommandProcessor;
import com.bowspleef.command.Commands;
import com.bowspleef.command.HelpCommand;
import com.bowspleef.game.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;

public class BowSpleef extends JavaPlugin {

    public static final String PATH = "plugins/BowSpleef";
    public static File arenaFile = new File(PATH + "/arena.yml");
    public static FileConfiguration arenaFileConfiguration = YamlConfiguration.loadConfiguration(arenaFile);
    public static File playerFile = new File(PATH + "/players.yml");
    public static FileConfiguration playerFileConfiguration = YamlConfiguration.loadConfiguration(arenaFile);

    @Override
    public void onEnable() {

        saveConfigurationFiles();
        loadConfigurationFiles();

        getCommand("bs").setExecutor(new CommandProcessor());
        Commands.getCommandList().add(new HelpCommand());

        //getServer().getPluginManager().registerEvents(new ArenaListener(), this);

        GameManager.getInstance().loadGames();

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
            playerFileConfiguration.save(playerFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadConfigurationFiles() {
        try {

            if (!arenaFile.exists()) {
                arenaFile.createNewFile();
                playerFile.createNewFile();
            }

            arenaFileConfiguration.load(arenaFile);
            playerFileConfiguration.load(playerFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
