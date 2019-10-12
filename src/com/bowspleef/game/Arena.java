package com.bowspleef.game;

import com.bowspleef.BowSpleef;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Vector;

public class Arena {

    private String name;

    private Location lobby;
    private Location spawn;
    private Location pos1;
    private Location pos2;

    public Arena(String name) {
        this.name = name;
    }

    public void setLobby(Location lobby) {
        FileConfiguration arenaFileConfiguration = BowSpleef.arenaFileConfiguration;
        arenaFileConfiguration.set("arena." + name + ".lobby.world", lobby.getWorld().getName());
        arenaFileConfiguration.set("arena." + name + ".lobby.x", lobby.getX());
        arenaFileConfiguration.set("arena." + name + ".lobby.y", lobby.getY());
        arenaFileConfiguration.set("arena." + name + ".lobby.z", lobby.getZ());
        BowSpleef.saveConfigurationFiles();

        this.lobby = lobby;
    }

    public Location getLobby() {
        if (lobby == null) {
            FileConfiguration arenaFileConfiguration = BowSpleef.arenaFileConfiguration;
            String world = arenaFileConfiguration.getString("arena." + name + ".lobby.world");
            double x = arenaFileConfiguration.getDouble("arena." + name + ".lobby.x");
            double y = arenaFileConfiguration.getDouble("arena." + name + ".lobby.y");
            double z = arenaFileConfiguration.getDouble("arena." + name + ".lobby.z");

            Location location = new Location(Bukkit.getServer().getWorld(world), x, y, z);
            this.lobby = location;
            return lobby;
        }

        return lobby;
    }

    public void setSpawn(Location spawn) {
        FileConfiguration arenaFileConfiguration = BowSpleef.arenaFileConfiguration;
        arenaFileConfiguration.set("arena." + name + ".spawn.world", spawn.getWorld().getName());
        arenaFileConfiguration.set("arena." + name + ".spawn.x", spawn.getX());
        arenaFileConfiguration.set("arena." + name + ".spawn.y", spawn.getY());
        arenaFileConfiguration.set("arena." + name + ".spawn.z", spawn.getZ());
        BowSpleef.saveConfigurationFiles();

        this.spawn = spawn;
    }

    public Location getSpawn() {
        if (spawn == null) {
            FileConfiguration arenaFileConfiguration = BowSpleef.arenaFileConfiguration;
            String world = arenaFileConfiguration.getString("arena." + name + ".spawn.world");
            double x = arenaFileConfiguration.getDouble("arena." + name + ".spawn.x");
            double y = arenaFileConfiguration.getDouble("arena." + name + ".spawn.y");
            double z = arenaFileConfiguration.getDouble("arena." + name + ".spawn.z");

            Location location = new Location(Bukkit.getServer().getWorld(world), x, y, z);
            this.spawn = location;
            return spawn;
        }

        return spawn;
    }

    public void setPos1(Location pos1) {
        FileConfiguration arenaFileConfiguration = BowSpleef.arenaFileConfiguration;
        arenaFileConfiguration.set("arena." + name + ".pos1.world", pos1.getWorld().getName());
        arenaFileConfiguration.set("arena." + name + ".pos1.x", pos1.getX());
        arenaFileConfiguration.set("arena." + name + ".pos1.y", pos1.getY());
        arenaFileConfiguration.set("arena." + name + ".pos1.z", pos1.getZ());
        BowSpleef.saveConfigurationFiles();

        this.pos1 = pos1;
    }

    public Location getPos1() {
        if (pos1 == null) {
            FileConfiguration arenaFileConfiguration = BowSpleef.arenaFileConfiguration;
            String world = arenaFileConfiguration.getString("arena." + name + ".pos1.world");
            double x = arenaFileConfiguration.getDouble("arena." + name + ".pos1.x");
            double y = arenaFileConfiguration.getDouble("arena." + name + ".pos1.y");
            double z = arenaFileConfiguration.getDouble("arena." + name + ".pos1.z");

            Location location = new Location(Bukkit.getServer().getWorld(world), x, y, z);
            this.pos1 = location;
            return pos1;
        }

        return pos1;
    }

    public void setPos2(Location pos2) {
        FileConfiguration arenaFileConfiguration = BowSpleef.arenaFileConfiguration;
        arenaFileConfiguration.set("arena." + name + ".pos2.world", pos2.getWorld().getName());
        arenaFileConfiguration.set("arena." + name + ".pos2.x", pos2.getX());
        arenaFileConfiguration.set("arena." + name + ".pos2.y", pos2.getY());
        arenaFileConfiguration.set("arena." + name + ".pos2.z", pos2.getZ());
        BowSpleef.saveConfigurationFiles();

        this.pos2 = pos2;
    }

    public Location getPos2() {
        if (pos2 == null) {
            FileConfiguration arenaFileConfiguration = BowSpleef.arenaFileConfiguration;
            String world = arenaFileConfiguration.getString("arena." + name + ".pos2.world");
            double x = arenaFileConfiguration.getDouble("arena." + name + ".pos2.x");
            double y = arenaFileConfiguration.getDouble("arena." + name + ".pos2.y");
            double z = arenaFileConfiguration.getDouble("arena." + name + ".pos2.z");

            Location location = new Location(Bukkit.getServer().getWorld(world), x, y, z);
            this.pos2 = location;
            return pos2;
        }

        return pos2;
    }

    public void regen() {
        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());

        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {

                    Block block = pos1.getWorld().getBlockAt(x, y, z);

                    if (block.getType() == Material.AIR) {
                        block.setType(Material.TNT);
                    }

                }
            }
        }
    }

}
