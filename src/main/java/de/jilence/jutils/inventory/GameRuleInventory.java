package de.jilence.jutils.inventory;

import de.jilence.jutils.utils.GameRuleManager;
import de.jilence.jutils.utils.InventoryBuilder;
import de.jilence.jutils.utils.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class GameRuleInventory {

    public final static String INVENTORY_NAME = "§9Spielregeln §8● ";

    public static Inventory getGameRuleInventory() {
        Inventory inventory = Bukkit.createInventory(null, 4*9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());
        GameRuleManager.setGameRuleInventory(inventory);
        return inventory;
    }

}
