package de.jilence.jutils.challenges;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class NoBlockPlace extends Challenge implements Listener {
    @Override
    public void onEnable() {

    }

    @Override
    public void onStart() {
        Bukkit.getPluginManager().registerEvents(new NoBlockPlace(), Main.getPlugin(Main.class));
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {
        Bukkit.broadcast(Component.text("TICK"), "bukkit.broadcast");
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        ChallengeManager.loseChallenge(event.getPlayer(), "§7der Spieler §9%player §7ein Block abgebaut hat");
    }

    @Override
    public Inventory getSettingsInventory() {
        return null;
    }

    @Override
    public ItemStack getDisplayItem() {
        ItemStack itemStack = new ItemBuilder(Material.GRASS_BLOCK, 1).displayname("§7No-Block-Place §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Bei Dieser §9Herausforderung §7darf kein Spieler,")
                .addLoreLine("§7einen Block platzieren")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.NOBLOCKPLACE), "§7Herausforderung")
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
