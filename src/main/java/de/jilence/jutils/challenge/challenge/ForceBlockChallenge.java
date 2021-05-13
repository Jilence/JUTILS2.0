package de.jilence.jutils.challenge.challenge;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.utils.*;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class ForceBlockChallenge extends Challenge {

    private static String MIN = "ForceBlockChallengeMin";
    private static String MAX = "ForceBlockChallengeMax";
    private static String INVENTORY_NAME = "§7 ForceBlockChallenge §9Challenge";

    private static int delay;
    private static int time;

    private static HashMap<UUID, BossBar> bossBarList = new HashMap<>();

    private static boolean task;

    private static Material nextMaterial;

    @Override
    public void onEnable() {

    }

    @Override
    public void onStart() {

        task = false;

        for (Player players : Bukkit.getOnlinePlayers()) {
            BossBar bossBar = Bukkit.createBossBar("§7Warten auf neue §9Anweisung...", BarColor.BLUE, BarStyle.SOLID);
            bossBar.addPlayer(players);
            bossBarList.put(players.getUniqueId(), bossBar);
        }

        delay = getDelayInt();

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {

        bossBarList.forEach((uuid, bossBar) -> bossBar.removePlayer(Objects.requireNonNull(Bukkit.getPlayer(uuid))));

    }

    @Override
    public void onTick() {

        if(!task) {
            delay--;
        }
        if(delay == 0 && !task) {
            getNewTask();
        }

        if(task) {
            bossBarList.forEach((uuid, bossBar) -> {
                bossBar.setTitle("§7Stehe in §9" + Messages.timerWithoutHours(time) + " §7auf §9" + String.valueOf(nextMaterial).replace("_", ""));
            });
            time--;
        }

        if(time == 0 && task) {
            if(check() == null) {
                Bukkit.broadcast(Component.text(Main.getPrefix() + "§7Alle Spieler haben es §ageschafft."), "bukkit.broadcast");
                bossBarList.forEach((uuid, bossBar) -> {
                    bossBar.setTitle("§7Warten auf nächste §9Anweisungen...");
                });
                task = false;
                delay = getDelayInt();
            } else {
                ChallengeManager.loseChallenge(check(), "§9%player §7nicht auf §9" + String.valueOf(nextMaterial).replace("_", "") + "§7 stand");
            }
        }

    }

    private static int getDelayInt() {
        int min = new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(MIN);
        int max = new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(MAX);

        return ThreadLocalRandom.current().nextInt(min, max + 1) * 60;
    }

    private static Player check() {

        Player check = null;
        for (Player players : Bukkit.getOnlinePlayers()) {
            if(players.getLocation().clone().add(0, -1, 0).getBlock().getType() == nextMaterial
            || players.getLocation().clone().add(0, -0.5, 0).getBlock().getType() == nextMaterial
            || players.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == nextMaterial
            || players.getLocation().clone().add(0, 0, 0).getBlock().getType() == nextMaterial
            || players.getLocation().clone().add(0, -0.25, 0).getBlock().getType() == nextMaterial) {
            } else
                check = players;
        }

        return check;
    }

    private static void getNewTask() {

        nextMaterial = Material.values()[new Random().nextInt(Material.values().length - 1)];
        time = ThreadLocalRandom.current().nextInt(3, 6 + 1) * 60;
        task = true;

        bossBarList.forEach((uuid, bossBar) -> {
            bossBar.setTitle("§7Stehe in §9" + Messages.timerWithoutHours(time) + " §7auf §9" + String.valueOf(nextMaterial).replace("_", ""));
        });

    }

    @Override
    public Inventory getSettingsInventory() {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());

        ItemStack forceBlockMin = new ItemBuilder(Material.LIME_BED).displayname("§7ForceBlockChallenge §8● §9Min. Zeit").build();
        new LoreBuilder(forceBlockMin)
                .addSpace()
                .addLoreLine("§9Bedeutung:")
                .addParagraph("§7Gib an die §9Minuten §7an, die mindestens")
                .addParagraph("§7vorbei sein müssen, bis das nächste §9Item §7kommt")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 +1 Minute")
                .addLoreLine(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + "§7 -1 Minute")
                .addSpace()
                .addLoreLine("§7Status: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(MIN, 1) + " Minute/n")
                .addSpace();

        inventory.setItem(3 + 9, forceBlockMin);

        ItemStack forceBlockMax = new ItemBuilder(Material.RED_BED).displayname("§7ForceBlockChallenge §8● §9Max. Zeit").build();
        new LoreBuilder(forceBlockMax)
                .addSpace()
                .addLoreLine("§9Bedeutung:")
                .addParagraph("§7Gib an die §9Minuten §7an, die maximal")
                .addParagraph("§7vorbei sein müssen, bis das nächste §9Item §7kommt")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 +1 Minute")
                .addLoreLine(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + "§7 -1 Minute")
                .addSpace()
                .addLoreLine("§7Status: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(MAX, 4) + " Minute/n")
                .addSpace();

        inventory.setItem(5 + 9, forceBlockMax);

        return inventory;
    }

    @Override
    public ItemStack getDisplayItem() {
        ItemStack itemStack = new ItemBuilder(Material.BRAIN_CORAL, 1).displayname("§7Force-Block-Challenge §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Du musst in einer bestimmten §9Zeit,")
                .addLoreLine("§7auf einen bestimmen §9Block §7stehen")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.FORCEBLOCKCHALLENGE), "§7Herausforderung")
                .addLoreLine("§7Min. Zeit: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(MIN, 1) + " Minute/n")
                .addLoreLine("§7Max. Zeit: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(MAX, 4) + " Minute/n")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.UNDER_CONSTRUCTION.getMessage())
                .addSpace();

        return itemStack;
    }

    @Override
    public String getInventoryName() {
        return INVENTORY_NAME;
    }

    @Override
    public void onInventoryClick(Player player, ClickType clickType, Material material, Inventory inventory) {

        int min = new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(MIN);
        int max = new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(MAX);

        if(material == Material.LIME_BED) {
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(MIN,
                    InventoryBuilder.inventoryClickIntManagerWithoutShift(min, clickType, 1, (max - 1), 1, 1));
            player.openInventory(getSettingsInventory());
        }
        if(material == Material.RED_BED) {
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(MAX,
                    InventoryBuilder.inventoryClickIntManagerWithoutShift(max, clickType, (min + 1), 60, 1, 1));
            player.openInventory(getSettingsInventory());
        }

    }
}
