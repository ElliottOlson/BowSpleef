package com.bowspleef;

import org.bukkit.plugin.java.JavaPlugin;

public class BowSpleef extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("BowSpleef is enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BowSpleef is disabled.");
    }

}
