package de.jilence.jutils.utils;

import de.jilence.jutils.inventory.GameRuleInventory;
import de.jilence.jutils.inventory.MainInventory;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GameRuleManager {

    public enum GAMERULES {

        DO_DAYLIGHT_CIRCLE("§7Do §9Daylight Circle", GameRule.DO_DAYLIGHT_CYCLE, Material.DAYLIGHT_DETECTOR),
        DO_ENTITY_DROPS("§7Do §9Entity Drops", GameRule.DO_ENTITY_DROPS, Material.ZOMBIE_HEAD);

        String name;
        GameRule<Boolean> gameRule;
        Material material;

        GAMERULES(String name, GameRule<Boolean> gameRule, Material material) {
            this.name = name;
            this.gameRule = gameRule;
            this.material = material;
        }

        public String getName() {
            return name;
        }

        public GameRule<Boolean> getGameRule() {
            return gameRule;
        }

        public Material getMaterial() {
            return material;
        }
    }

    public static void setGameRuleInventory(Inventory inventory) {

        for (int i = 0; i < GAMERULES.values().length; i++) {

            GAMERULES gamerules = GAMERULES.values()[i];
            int slot = i + 10;

            ItemStack gameRuleItem = new ItemBuilder(gamerules.getMaterial()).displayname(gamerules.getName()).build();
            new LoreBuilder(gameRuleItem)
                    .addSpace()
                    .addLoreLine("§6GameRule §7Status:")
                    .addBoolSettingsWithDot(Bukkit.getWorld("world").getGameRuleValue(gamerules.getGameRule()), "§9Status")
                    .addSpace();

            inventory.setItem(slot, gameRuleItem);

        }
    }

    public static void toggleGameRule(GameRule<Boolean> gameRule) {
        Bukkit.getWorld("world").setGameRule(gameRule, !Bukkit.getWorld("world").getGameRuleValue(gameRule));
        Bukkit.getWorld("world_nether").setGameRule(gameRule, !Bukkit.getWorld("world_nether").getGameRuleValue(gameRule));
        Bukkit.getWorld("world_the_end").setGameRule(gameRule, !Bukkit.getWorld("world_the_end").getGameRuleValue(gameRule));
    }

    public static void onInventoryClick(Material material, Player player) {

        if (material == Material.BLACK_STAINED_GLASS_PANE) {
            player.openInventory(MainInventory.getInventory());
            return;
        }

        for (GAMERULES gamerules : GAMERULES.values()) {

            if (gamerules.getMaterial() == material) {
                toggleGameRule(gamerules.getGameRule());
                player.openInventory(GameRuleInventory.getGameRuleInventory());
                return;
            }
        }
    }
}