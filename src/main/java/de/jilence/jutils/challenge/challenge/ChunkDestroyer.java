package de.jilence.jutils.challenge.challenge;

import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.InventoryBuilder;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChunkDestroyer extends Challenge {

    private static String INVENTORY_NAME = "§7 ChunkDestroyer §9Challenge";

    private static int duration;
    private static int tick;

    @Override
    public void onEnable() {

    }

    @Override
    public void onStart() {
        duration = new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt("chunkDestroyerTime");
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onTick() {

        if (duration == tick) {
            tick = 0;

            for (Player player : Bukkit.getOnlinePlayers()) {

                if (player.getGameMode() == GameMode.SPECTATOR) continue;


                for (Player players : Bukkit.getOnlinePlayers()) {
                    for (int i = 0; i < 16; i++) {
                        for (int j = 0; j < 16; j++) {
                            int loc_x = players.getLocation().getChunk().getX() * 16 + i;
                            int loc_z = players.getLocation().getChunk().getZ() * 16 + j;

                            Block block = players.getWorld().getHighestBlockAt(loc_x, loc_z);

                            while (block.getType() == Material.WATER) {
                                block.setType(Material.AIR);
                                block = players.getWorld().getHighestBlockAt(loc_x, loc_z);
                            }

                            while (!block.getType().isBlock()) {
                                block.setType(Material.AIR);
                                block = players.getWorld().getHighestBlockAt(loc_x, loc_z);
                            }

                            players.getWorld().getHighestBlockAt(loc_x, loc_z).setType(Material.AIR);
                        }
                    }
                }

            }


        }

        tick++;
    }

    @Override
    public Inventory getSettingsInventory() {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());

        ItemStack chunkDestroyerTimeItem = new ItemBuilder(Material.CLOCK).displayname("§7ChunkDestroyer §8● §9Zeit-Einstellung").build();
        new LoreBuilder(chunkDestroyerTimeItem)
                .addSpace()
                .addLoreLine("§9Bedeutung:")
                .addParagraph("§7Gib an, in welchen Abstand, die oberste")
                .addParagraph("§9Chunk-Fläche §centfernt §7wird.")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 +1 Sekunde")
                .addLoreLine(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + "§7 -1 Sekunde")
                .addSpace()
                .addLoreLine("§7Status: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt("chunkDestroyerTime") + " Sekunde/n")
                .addSpace();

        inventory.setItem(4 + 9, chunkDestroyerTimeItem);

        return inventory;
    }

    @Override
    public ItemStack getDisplayItem() {
        ItemStack itemStack = new ItemBuilder(Material.RED_CONCRETE, 1).displayname("§7Chunk-Destroyer §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Bei Dieser §9Herausforderung §7werden in einen bestimmen,")
                .addLoreLine("§7Abstand, die oberste §9Blockschicht §7aus dem Chunk §cgelöscht")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.CHUNKDESTROYER), "§7Herausforderung")
                .addLoreLine("§7Abstand: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt("chunkDestroyerTime") + " Sekunde/n")
                .addSpace();

        return itemStack;
    }

    @Override
    public String getInventoryName() {
        return INVENTORY_NAME;
    }

    @Override
    public void onInventoryClick(Player player, ClickType clickType, Material material, Inventory inventory) {
        if (material == Material.CLOCK) {
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set("chunkDestroyerTime",
                    InventoryBuilder.inventoryClickIntManagerWithoutShift(new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt("chunkDestroyerTime")
                            , clickType, 1, 600, 1, 1));
            player.openInventory(getSettingsInventory());
        }

    }

}
