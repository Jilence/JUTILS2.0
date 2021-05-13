package de.jilence.jutils.inventory;

import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.InventoryBuilder;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GameSettingsInventory {

    public final static String INVENTORY_NAME = "§9Spieleinstellungen §8● ";

    public static Inventory getGameSettingsInventory() {
        Inventory inventory = Bukkit.createInventory(null, 4 * 9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());

        ItemStack enderdragonDead = new ItemBuilder(Material.DRAGON_HEAD).displayname("§7EnderDragon §cDeath").build();
        new LoreBuilder(enderdragonDead)
                .addSpace()
                .addLoreLine("§7Wenn der §cEnderDragon §7getötet wird, ist")
                .addLoreLine("§7die Challenge geschafft.")
                .addSpace()
                .addBoolSettingsWithDot(new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("enderdragon"), "§7Status")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 Schalte die Anwendung an/aus")
                .addSpace();

        inventory.setItem(1 + 9, enderdragonDead);

        ItemStack witherDead = new ItemBuilder(Material.WITHER_ROSE).displayname("§7Wither §cDeath").build();
        new LoreBuilder(witherDead)
                .addSpace()
                .addLoreLine("§7Wenn der §cWither §7getötet wird, ist")
                .addLoreLine("§7die Challenge geschafft.")
                .addSpace()
                .addBoolSettingsWithDot(new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("wither"), "§7Status")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 Schalte die Anwendung an/aus")
                .addSpace();

        inventory.setItem(2 + 9, witherDead);

        ItemStack playerDead = new ItemBuilder(Material.PLAYER_HEAD).displayname("§7Player §cDeath").build();
        new LoreBuilder(playerDead)
                .addSpace()
                .addLoreLine("§7Wenn ein §cSpieler §7stirbt, ist")
                .addLoreLine("§7die Challenge gescheitert.")
                .addSpace()
                .addBoolSettingsWithDot(new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("player"), "§7Status")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 Schalte die Anwendung an/aus")
                .addSpace();

        inventory.setItem(3 + 9, playerDead);

        ItemStack villageSpawn = new ItemBuilder(Material.VILLAGER_SPAWN_EGG).displayname("§cVillager §7Spawn").build();
        new LoreBuilder(villageSpawn)
                .addSpace()
                .addLoreLine("§7Wenn diese §9Einstellung §7aktiviert ist,")
                .addLoreLine("§7kriegst du immer ein §9Village §7als Spawn")
                .addSpace()
                .addBoolSettingsWithDot(new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("villageSpawn"), "§7Status")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 Schalte die Anwendung an/aus")
                .addSpace();

        inventory.setItem(4 + 9, villageSpawn);
        return inventory;
    }
}