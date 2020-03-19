package com.bowspleef;

import com.bowspleef.command.*;
import com.bowspleef.event.*;
import com.bowspleef.game.GameManager;
import com.bowspleef.kit.ClassicKit;
import com.bowspleef.kit.KitManager;
import com.bowspleef.manager.ConfigurationManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BowSpleef extends JavaPlugin {

    private static BowSpleef instance;

    @Override
    public void onEnable() {

        instance = this;

        ConfigurationManager.saveConfig();
        ConfigurationManager.loadConfig();

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
        getServer().getPluginManager().registerEvents(new SignEvent(), this);

        GameManager.getInstance().loadGames();

        KitManager.getInstance().getKits().add(new ClassicKit());

        getLogger().info("BowSpleef is enabled.");
    }

    @Override
    public void onDisable() {

        GameManager.getInstance().saveGames();
        ConfigurationManager.saveConfig();

        getLogger().info("BowSpleef is disabled.");
    }

    public static BowSpleef getInstance() {
        if (instance == null)
            instance = new BowSpleef();

        return instance;
    }
}
