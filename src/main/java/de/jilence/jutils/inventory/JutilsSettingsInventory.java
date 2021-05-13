package de.jilence.jutils.inventory;

import de.jilence.jutils.utils.InventoryBuilder;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class JutilsSettingsInventory {

    public final static String INVENTORY_NAME = "§9Jutils-Einstellungen §8● ";

    public static Inventory getJutilsSettingsInventory() {

        Inventory inventory = Bukkit.createInventory(null, 4*9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());

        ItemStack timerItem = new ItemBuilder(Material.CLOCK).displayname("§8● §7Timer Einstellungen").build();
        new LoreBuilder(timerItem)
                .addSpace()
                .addLoreLine("§7Stelle den §9Timer §7ein.")
                .addLoreLine("§7Hier kannst du §9einstellen§7: ")
                .addSpace()
                .addParagraph("§7ob der §9Timer §7pausiert ist")
                .addParagraph("§7in welche §9Richtung §7der §9Timer §7laufen soll")
                .addParagraph("§7Timer §czurücksetzen")
                .addParagraph("§7stelle den §9Timer §7auf eine bestimme §9Zeit")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace();

        inventory.setItem(1+9, timerItem);

        return inventory;

    }
}
