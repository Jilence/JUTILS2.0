package de.jilence.jutils.challenges;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NoBlockBreak extends Challenge implements Listener {

    private static NoBlockBreak noBlockBreak;

    @Override
    public void onEnable() {
        noBlockBreak = new NoBlockBreak();
    }

    @Override
    public void onStart() {
        Bukkit.getPluginManager().registerEvents(new NoBlockBreak(), Main.getPlugin(Main.class));
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {

    }

    @Override
    public void onTick() {

    }

    @Override
    public Inventory getSettingsInventory() {
        return null;
    }

    @Override
    public ItemStack getDisplayItem() {
        ItemStack itemStack = new ItemBuilder(Material.IRON_PICKAXE, 1).displayname("§7No-Block-Break §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Bei Dieser §9Herausforderung §7darf kein Spieler,")
                .addLoreLine("§7einen Block abbauen")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.NOBLOCKBREAK), "§7Herausforderung")
                .addSpace();

        return itemStack;
    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public void onInventoryClick(Player player, ClickType clickType, Material material, Inventory inventory) {

    }
}