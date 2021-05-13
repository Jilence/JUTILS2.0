package de.jilence.jutils.challenges;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.InventoryBuilder;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class RandomBlockDrop extends Challenge implements Listener {

    private static final String RANDOM = "completelyRandomMob";
    private static final String INVENTORY_NAME = "§7 Random-Block-Drop §9Challenge";

    private static final HashMap<Material, Material> randomItemDrop = new HashMap<>();

    @Override
    public void onEnable() {

    }

    @Override
    public void onStart() {

        Bukkit.getPluginManager().registerEvents(new RandomBlockDrop(), Main.getPlugin(Main.class));

        for(Material materialBlock : Material.values()) {
            Random random = new Random();
            Material material = Material.AIR;

            while (material == Material.VOID_AIR || material == Material.AIR || material == Material.CAVE_AIR || material.isAir()) {
                material = Material.values()[random.nextInt((Material.values().length - 1))];
            }



            randomItemDrop.put(materialBlock, material);

        }

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {

        if(!new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getBool(RANDOM)) {

            Material material = randomItemDrop.get(event.getBlock().getType());

            event.getBlock().setType(Material.AIR);
            try {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemBuilder(material).build());
            } catch (IllegalArgumentException ignore) {
                //ignore
            }

        } else {
            Random random = new Random();
            Material material = Material.AIR;

            while (material == Material.VOID_AIR || material == Material.AIR || material == Material.CAVE_AIR || material.isAir()) {
                material = Material.values()[random.nextInt((Material.values().length - 1))];
            }

            event.getBlock().setType(Material.AIR);
            try {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemBuilder(material).build());
            } catch (IllegalArgumentException ignore) {
                //ignore
            }
        }

    }

    @Override
    public void onTick() {

    }



    @Override
    public Inventory getSettingsInventory() {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());

        ItemStack randomDropItem = new ItemBuilder(Material.REDSTONE_BLOCK).displayname("§7Random-Block-Drop §8● §9Komplett-Random").build();
        new LoreBuilder(randomDropItem)
                .addSpace()
                .addLoreLine("§9Komplett Random:")
                .addParagraph("§7Es wird immer ein anderes Item gedroppt,")
                .addParagraph("§cnicht §7immer das gleiche für ein Block")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 Umschalten")
                .addLoreLine(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + "§7 Umschalten")
                .addSpace()
                .addLoreLine("§7Komplett Random: §9" + (new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getBool(RANDOM) ? "§2aktiviert" : "§4deaktiviert"))
                .addSpace();

        inventory.setItem(4 + 9, randomDropItem);

        return inventory;
    }

    @Override
    public ItemStack getDisplayItem() {
        ItemStack itemStack = new ItemBuilder(Material.FARMLAND, 1).displayname("§7Random-Block-Drop §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Blöcke droppen andere Items als gewöhnlich")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.RANDOMBLOCKDROP), "§7Herausforderung")
                .addLoreLine("§7Komplett Random: §9" + (new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getBool(RANDOM) ? "§2aktiviert" : "§4deaktiviert"))
                .addParagraph("§7Komplett Random: Bei jeden Block wird ein anderes Item gedroppt")
                .addParagraph("§7Sonst wird immer das gleiche Item bei einen Block gedroppt")
                .addSpace();

        return itemStack;
    }

    @Override
    public String getInventoryName() {
        return INVENTORY_NAME;
    }

    @Override
    public void onInventoryClick(Player player, ClickType clickType, Material material, Inventory inventory) {
        if(material == Material.REDSTONE_BLOCK) {
            boolean bool = new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getBool(RANDOM);
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(RANDOM, !bool);
            player.openInventory(getSettingsInventory());
        }
    }
}
