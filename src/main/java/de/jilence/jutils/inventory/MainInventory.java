package de.jilence.jutils.inventory;

import de.jilence.jutils.utils.InventoryBuilder;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MainInventory {

    public final static String INVENTORY_NAME = "§9Hauptmenü §8● ";

    public static Inventory getInventory() {

        Inventory inventory = Bukkit.createInventory(null, 3*9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).displayname("").build());

        inventory.setItem(2+9, new ItemBuilder(Material.CLOCK, 1).displayname("§7Hauptmenü §8● §9GameRules")
                .lore("", "§7Ändere die Einstellungen des §cSpieles§7,", "§7indem du dieses §cInventory §6öffnest.", "", "§8[Linksklick] §7Inventar §aöffnen", "").build());

        inventory.setItem(3+9, new ItemBuilder(Material.GRASS_BLOCK, 1).displayname("§7Hauptmenü §8● §cHerausforderungenn")
                .lore( "","§7Schalte §9Herausforderungen §7an und aus.", "§7Du kannst mehr als eine §9Challenge aktivieren§7!", "", "§8[Linksklick] §7Inventar §aöffnen"
                , "", "§8Copyright ©", "§7Alle Challenges wurden von §7Jilence programmiert.", "").build());

        ItemStack settingItem = new ItemBuilder(Material.REDSTONE).displayname("§7Hauptmenü §8● §9Jutils-Einstellungen").build();
        new LoreBuilder(settingItem)
                .addSpace()
                .addLoreLine("§7Stelle weitere §9Einstellungen §7ein,")
                .addLoreLine("§7wie zum Beispiel: ")
                .addSpace()
                .addParagraph("§7Timer")
                .addParagraph("soon..")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + " §7Öffne das Inventory")
                .addSpace();

        inventory.setItem(5+9, settingItem);

        ItemStack gameSettingsItem = new ItemBuilder(Material.DRAGON_HEAD).displayname("§7Hauptmenü §8● §9Spiel-Einstellungen").build();
        new LoreBuilder(gameSettingsItem)
                .addSpace()
                .addLoreLine("§7Stelle dein §9Spiel ein. §7Was passieren soll,")
                .addLoreLine("§7wenn du etwas machst. ")
                .addSpace()
                .addLoreLine("§cFunktionen:")
                .addParagraph("§7Challenge bei Tot beenden")
                .addParagraph("§7Challenge geschafft beim EnderDragon tot")
                .addParagraph("§7Challenge geschafft beim Wither tot")
                .addParagraph("§7Village Spawn")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + " §7Öffne das Inventory")
                .addSpace();

        inventory.setItem(6+9, gameSettingsItem);

        return inventory;
    }

}
