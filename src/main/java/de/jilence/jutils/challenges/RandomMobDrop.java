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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class RandomMobDrop extends Challenge implements Listener {

    private static String RANDOM = "completelyRandom";
    private static String INVENTORY_NAME = "§7 Random-Mob-Drop §9Challenge";

    private static final HashMap<EntityType, Material> randomItemDrop = new HashMap<>();

    @Override
    public void onEnable() {

    }

    @Override
    public void onStart() {

        Bukkit.getPluginManager().registerEvents(new RandomMobDrop(), Main.getPlugin(Main.class));

        for(EntityType entity : EntityType.values()) {
            Random random = new Random();
            Material material = Material.values()[random.nextInt((Material.values().length - 1))];

            if(material == Material.AIR || material ==  Material.CAVE_AIR || material == Material.VOID_AIR) {
                material = Material.STONE;
            }

            randomItemDrop.put(entity, material);

        }


    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {


        if((event.getEntity() instanceof Player)) {
            return;
        }

        if(!new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getBool(RANDOM)) {
            event.getDrops().clear();
            ItemStack drops = new ItemBuilder(randomItemDrop.get(event.getEntity().getType())).build();
            try {
                event.getDrops().add(drops);
            } catch (IllegalArgumentException ignore) {
                //ignore
            }
        } else {
            Random random = new Random();
            Material material = Material.values()[random.nextInt((Material.values().length - 1))];

            if(material == Material.AIR || material ==  Material.CAVE_AIR || material == Material.VOID_AIR) {
                material = Material.STONE;
            }

            ItemStack drops = new ItemBuilder(material).build();
            event.getDrops().clear();
            try {
                event.getDrops().add(drops);
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

        ItemStack chunkDestroyerTimeItem = new ItemBuilder(Material.ZOMBIE_HEAD).displayname("§7Random-Mod-Drop §8● §9Komplett-Random").build();
        new LoreBuilder(chunkDestroyerTimeItem)
                .addSpace()
                .addLoreLine("§9Komplett Random:")
                .addParagraph("§7Es wird immer ein anderes Item gedroppt,")
                .addParagraph("§cnicht §7immer das gleiche für ein Mob")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 Umschalten")
                .addLoreLine(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + "§7 Umschalten")
                .addSpace()
                .addLoreLine("§7Komplett Random: §9" + (new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getBool(RANDOM) ? "§2aktiviert" : "§4deaktiviert"))
                .addSpace();

        inventory.setItem(4 + 9, chunkDestroyerTimeItem);

        return inventory;
    }

    @Override
    public ItemStack getDisplayItem() {
        ItemStack itemStack = new ItemBuilder(Material.ZOMBIE_HEAD, 1).displayname("§7Random-Mob-Drop §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Monster droppen andere Items als gewöhnlich")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.RANDOMMOBDROP), "§7Herausforderung")
                .addLoreLine("§7Komplett Random: §9" + (new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getBool(RANDOM) ? "§2aktiviert" : "§4deaktiviert"))
                .addParagraph("§7Komplett Random: Bei jeden Mob wird ein anderes Item gedroppt")
                .addParagraph("§7Sonst wird immer das gleiche Item bei einen Mob gedroppt")
                .addSpace();

        return itemStack;
    }

    @Override
    public String getInventoryName() {
        return INVENTORY_NAME;
    }

    @Override
    public void onInventoryClick(Player player, ClickType clickType, Material material, Inventory inventory) {

        if(material == Material.ZOMBIE_HEAD) {
            boolean bool = new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getBool(RANDOM);
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(RANDOM, !bool);
            player.openInventory(getSettingsInventory());
        }

    }
}
