package de.jilence.jutils.listener;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.challenge.challenge.NoBlockBreak;
import de.jilence.jutils.inventory.*;
import de.jilence.jutils.timer.Timer;
import de.jilence.jutils.timer.TimerManager;
import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.GameRuleManager;
import de.jilence.jutils.utils.InventoryBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        switch (event.getView().getTitle()) {

            case "§9Herausforderungen §8● ": {
                event.setCancelled(true);
                challengeInventoryClickEvent(event.getCurrentItem().getType(), (Player) event.getWhoClicked(), event.getClick(), event.getSlot());
                break;
            }

            case "§9Spielregeln §8● ": {
                event.setCancelled(true);
                GameRuleManager.onInventoryClick(event.getCurrentItem().getType(), (Player) event.getWhoClicked());
                break;
            }

            case "§9Hauptmenü §8● ": {
                event.setCancelled(true);
                mainInventoryClickEvent(event.getCurrentItem().getType(), (Player) event.getWhoClicked());
                break;
            }
            case "§9Spieleinstellungen §8● ": {
                event.setCancelled(true);
                gameSettingsInventoryClickEvent(event.getCurrentItem().getType(), (Player) event.getWhoClicked());
                break;
            }

            case "§9Jutils-Einstellungen §8● ": {
                event.setCancelled(true);
                jutilsSettingsInventoryClickEvent(event.getCurrentItem().getType(), (Player) event.getWhoClicked());
                break;
            }

            case "§9Timer-Einstellungen §8● ": {
                event.setCancelled(true);
                timerInventoryClickEvent(event.getCurrentItem().getType(), (Player) event.getWhoClicked(), event.getClick());
                break;
            }

        }

        for (ChallengeManager.Challenges challenge : ChallengeManager.Challenges.values()) {

            if(challenge.getChallenge().getInventoryName() == null) {
                continue;
            }

            String title = String.valueOf(event.getView().title());

            if(title.contains(challenge.getChallenge().getInventoryName())) {
                event.setCancelled(true);

                if(event.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE) {
                    int playerSide = ChallengeInventory.playerSides.get(player.getUniqueId());
                    for (ChallengeInventory.SIDE side : ChallengeInventory.SIDE.values()) {
                        if(side.getSide() == playerSide) {
                            player.openInventory(ChallengeInventory.getChallengeInventory(side, player));
                        }
                    }
                    return;
                }

                challenge.getChallenge().onInventoryClick((Player) event.getWhoClicked(), event.getClick(), event.getCurrentItem().getType(), event.getClickedInventory());
            }

        }

    }

    private static void gameSettingsInventoryClickEvent(Material material, Player player) {

        switch (material) {

            case DRAGON_HEAD: {
                new ConfigManager(ConfigManager.CONFIGS.CONFIG).toggleBool("enderdragon");
                player.openInventory(GameSettingsInventory.getGameSettingsInventory());
                break;
            }

            case WITHER_ROSE: {
                new ConfigManager(ConfigManager.CONFIGS.CONFIG).toggleBool("wither");
                player.openInventory(GameSettingsInventory.getGameSettingsInventory());
                break;
            }
            case PLAYER_HEAD: {
                new ConfigManager(ConfigManager.CONFIGS.CONFIG).toggleBool("player");
                player.openInventory(GameSettingsInventory.getGameSettingsInventory());
                break;
            }
            case VILLAGER_SPAWN_EGG: {
                new ConfigManager(ConfigManager.CONFIGS.CONFIG).toggleBool("villageSpawn");
                player.openInventory(GameSettingsInventory.getGameSettingsInventory());
                break;
            }
            case BLACK_STAINED_GLASS_PANE: {
                player.openInventory(MainInventory.getInventory());
                break;
            }

        }
    }

    private static void challengeInventoryClickEvent(Material material, Player player, ClickType clickType, int slot) {

        if(material == Material.PLAYER_HEAD) {

            if(slot == 44) {

                int nextSide = ChallengeInventory.playerSides.get(player.getUniqueId()) + 1;

                for (ChallengeInventory.SIDE side : ChallengeInventory.SIDE.values()) {
                    if(side.getSide() == nextSide) {
                        player.openInventory(ChallengeInventory.getChallengeInventory(side, player));
                        return;
                    }
                }

            }
            if(slot == 36) {
                int previousSide = ChallengeInventory.playerSides.get(player.getUniqueId()) - 1;

                for (ChallengeInventory.SIDE side : ChallengeInventory.SIDE.values()) {
                    if(side.getSide() == previousSide) {
                        player.openInventory(ChallengeInventory.getChallengeInventory(side, player));
                        return;
                    }
                }
            }

        }

        for(int i = 0; i < ChallengeManager.Challenges.values().length; i++) {

            if(!clickType.isLeftClick()) continue;

            ChallengeManager.Challenges challenge = ChallengeManager.Challenges.values()[i];

            if(material == Material.BLACK_STAINED_GLASS_PANE) {
                player.openInventory(MainInventory.getInventory());
            }

            if(material == challenge.getChallenge().getDisplayItem().getType()) {
                ChallengeManager.toggleChallenge(challenge);

                if(!ChallengeManager.isChallengeEnabled(challenge)) {
                    challenge.getChallenge().onDisable();
                }

                int playerSide = ChallengeInventory.playerSides.get(player.getUniqueId());

                for (ChallengeInventory.SIDE side : ChallengeInventory.SIDE.values()) {
                    if(side.getSide() == playerSide) {

                        player.openInventory(ChallengeInventory.getChallengeInventory(side, player));
                    }
                }


            }

        }

        for(ChallengeManager.Challenges challenges : ChallengeManager.Challenges.values()) {
            if(challenges.getChallenge().getDisplayItem().getType() == material) {

                if(!clickType.isRightClick()) continue;

                if(challenges.getChallenge().getSettingsInventory() == null) {
                    player.sendMessage(Main.getError() + "§cFür diese Challenge gibt es keine weiteren Einstellungen");
                    continue;
                }

                player.openInventory(challenges.getChallenge().getSettingsInventory());
            }
        }

    }

    private static void timerInventoryClickEvent(Material material, Player player, ClickType clickType) {

        switch (material) {

            case BLACK_STAINED_GLASS_PANE: {
                player.openInventory(JutilsSettingsInventory.getJutilsSettingsInventory());
                break;
            }

            case ANVIL: {
                TimerManager.toggleDirection();
                player.openInventory(TimerInventory.getTimerInventory());
                Timer.restartTimer();
                break;
            }

            case LIME_DYE: case GRAY_DYE: {
                TimerManager.toggleTimerActivity();
                player.openInventory(TimerInventory.getTimerInventory());
                Timer.setPause(true);
                Timer.restartTimer();
                break;
            }

            case RED_CONCRETE: {
                Timer.resetTimer();
                break;
            }

            case CLOCK: {
                TimerManager.setCountdownTime(InventoryBuilder.inventoryClickIntManager(TimerManager.getCountdownTime(), clickType, 0, 1000000, 60, 60, 3600, 3600));
                player.openInventory(TimerInventory.getTimerInventory());
                Timer.restartTimer();
                break;
            }

        }

    }

    private static void jutilsSettingsInventoryClickEvent(Material material, Player player) {

        switch (material) {

            case CLOCK: {
                player.openInventory(TimerInventory.getTimerInventory());
                break;
            }

            case BLACK_STAINED_GLASS_PANE: {
                player.openInventory(MainInventory.getInventory());
            }

        }

    }

    private static void mainInventoryClickEvent(Material material, Player player) {

        if(ChallengeInventory.getChallengeInventory(ChallengeInventory.SIDE.SIDE1, player) == null) {
            player.sendMessage(Component.text(Main.getError() + "§cEs konnten keine Challenges geladen werden."));
            return;
        }

        switch (material) {


            case GRASS_BLOCK: {
                player.openInventory(ChallengeInventory.getChallengeInventory(ChallengeInventory.SIDE.SIDE1, player));
                break;
            }

            case CLOCK: {
                player.openInventory(GameRuleInventory.getGameRuleInventory());
                break;
            }

            case REDSTONE: {
                player.openInventory(JutilsSettingsInventory.getJutilsSettingsInventory());
                break;
            }

            case DRAGON_HEAD: {
                player.openInventory(GameSettingsInventory.getGameSettingsInventory());
                break;
            }

        }

    }

}
