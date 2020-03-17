package com.bowspleef;

import com.bowspleef.command.*;
import com.bowspleef.event.*;
import com.bowspleef.game.GameManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BowSpleef extends JavaPlugin {

    private static BowSpleef instance;

    public static final String PATH = "plugins/BowSpleef";
    public static File arenaFile = new File(PATH + "/arena.yml");
    public static FileConfiguration arenaFileConfiguration = YamlConfiguration.loadConfiguration(arenaFile);
    public static File playerFile = new File(PATH + "/players.yml");
    public static FileConfiguration playerFileConfiguration = YamlConfiguration.loadConfiguration(arenaFile);

    @Override
    public void onEnable() {

        instance = this;

        saveConfigurationFiles();
        loadConfigurationFiles();

        getCommand("bs").setExecutor(new CommandProcessor());
        Commands.getCommandList().add(new HelpCommand());
        Commands.getCommandList().add(new CreateCommand());
        Commands.getCommandList().add(new DebugCommand());
        Commands.getCommandList().add(new SetCommand());
        Commands.getCommandList().add(new RegenCommand());
        Commands.getCommandList().add(new JoinCommand());
        Commands.getCommandList().add(new DeleteCommand());
        Commands.getCommandList().add(new LeaveCommand());
        Commands.getCommandList().add(new VoteCommand());
        Commands.getCommandList().add(new ListCommand());

        getServer().getPluginManager().registerEvents(new BreakEvent(), this);
        getServer().getPluginManager().registerEvents(new DamageEvent(), this);
        getServer().getPluginManager().registerEvents(new DropEvent(), this);
        getServer().getPluginManager().registerEvents(new FoodEvent(), this);
        getServer().getPluginManager().registerEvents(new MoveEvent(), this);
        getServer().getPluginManager().registerEvents(new PickupEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);

        GameManager.getInstance().loadGames();

        getLogger().info("BowSpleef is enabled.");
    }

    @Override
    public void onDisable() {

        GameManager.getInstance().saveGames();
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

    public static BowSpleef getInstance() {
        if (instance == null)
            instance = new BowSpleef();

        return instance;
    }
}
