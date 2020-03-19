package com.bowspleef.kit;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitManager {

    private static KitManager instance;

    private List<Kit> kits = new ArrayList<>();
    private HashMap<Player, Kit> kitSelection = new HashMap<>();

    public Kit getKit(Player player) {
        if (kitSelection.containsKey(player)) {
            return kitSelection.get(player);
        }

        return null;
    }

    public Kit getKit(String name) {
        for (Kit kit : getKits()) {
            if (kit.getName().equalsIgnoreCase(name)) {
                return kit;
            }
        }

        return null;
    }

    public boolean setKit(Player player, Kit kit) {
        if (player == null || kit == null)
            return false;

        if (kitSelection.containsKey(player)) {
            kitSelection.remove(player);
        }

        kitSelection.put(player, kit);
        return true;
    }

    public List<Kit> getKits() {
        return kits;
    }

    public static KitManager getInstance() {
        if (instance == null)
            instance = new KitManager();

        return instance;
    }
}
