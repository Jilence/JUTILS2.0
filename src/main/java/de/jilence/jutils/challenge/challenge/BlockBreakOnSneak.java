package de.jilence.jutils.challenge.challenge;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockBreakOnSneak extends Challenge implements Listener {
    @Override
    public void onEnable() {

    }

    @Override
    public void onStart() {
        Bukkit.getPluginManager().registerEvents(new BlockBreakOnSneak(), Main.getPlugin(Main.class));
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {

        if(event.isSneaking()) {

            Block block = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
            block.setType(Material.AIR);

            for(int i = 0; i < block.getLocation().getBlockY(); i++) {
                event.getPlayer().getWorld().getBlockAt(block.getX(), i, block.getZ()).setType(Material.AIR);
            }

        }

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
        ItemStack itemStack = new ItemBuilder(Material.LEATHER_BOOTS, 1).displayname("§7Block-Break-on-Sneak §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Wenn ein §9Spieler sneakt,")
                .addLoreLine("§7wird der Block auf den er steht bis zum Bedrock §cabbgebaut")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.BLOCKBREAKONSNEAK), "§7Herausforderung")
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
