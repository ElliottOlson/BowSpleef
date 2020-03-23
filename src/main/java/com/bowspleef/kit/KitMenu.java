package com.bowspleef.kit;

import org.bukkit.entity.Player;

public class KitMenu {

    private static KitMenu instance;

    public void showMenu(Player player) {
        
    }

    public static KitMenu getInstance() {
        if (instance == null)
            instance = new KitMenu();

        return instance;
    }
}
