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
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityAirChangeEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class AirBreath extends Challenge implements Listener {

    private static final String AIRBREATHTIME = "airBreathTime";
    private static final String AIRBREATHDAMAGE = "airBreathDamage";
    private static final String INVENTORY_NAME = "§7 Air-Breath §9Challenge";

    private static int DAMAGE;

    private static HashMap<UUID, Integer> playerBreathHashMap = new HashMap<>();
    private static HashMap<UUID, BossBar> playerBossBarHashMap = new HashMap<>();

    @Override
    public void onEnable() {

    }

    @Override
    public void onStart() {
        Bukkit.getPluginManager().registerEvents(new AirBreath(), Main.getPlugin(Main.class));
        DAMAGE = new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(AIRBREATHDAMAGE);

        for (Player players : Bukkit.getOnlinePlayers()) {
            playerBreathHashMap.put(players.getUniqueId(), 10);
            BossBar bossBar = Bukkit.createBossBar("§9Luft§8-§7Level", BarColor.GREEN, BarStyle.SEGMENTED_10);
            bossBar.addPlayer(players);
            playerBossBarHashMap.put(players.getUniqueId(), bossBar);
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {
        playerBossBarHashMap.forEach((uuid, bossBar) -> {
            bossBar.removePlayer(Objects.requireNonNull(Bukkit.getPlayer(uuid)));
        });
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityAirChange(EntityAirChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!player.isInWater()) {
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 255));
        }
    }

    @Override
    public void onTick() {

        for (Player players : Bukkit.getOnlinePlayers()) {
            if (playerBreathHashMap.get(players.getUniqueId()) == null) {
                return;
            }

            int breathLevel = playerBreathHashMap.get(players.getUniqueId());

            if (players.isInWater()) {
                if (!((breathLevel + 2) > 10)) {
                    playerBreathHashMap.put(players.getUniqueId(), (breathLevel + 2));

                } else if (!((breathLevel + 1) > 10)) {
                    playerBreathHashMap.put(players.getUniqueId(), (breathLevel + 1));
                }

            } else {
                if (!((breathLevel - 1) < 0)) {
                    playerBreathHashMap.put(players.getUniqueId(), (breathLevel - 1));
                }
            }

            breathLevel = playerBreathHashMap.get(players.getUniqueId());
            double breathLevelLong = breathLevel;
            BossBar bossBar = playerBossBarHashMap.get(players.getUniqueId());

            if ((breathLevelLong / 10) < 0) {
                bossBar.setProgress(0);
            } else if ((breathLevelLong / 10) > 1) {
                bossBar.setProgress(1);
            } else
                bossBar.setProgress((breathLevelLong / 10));

            switch (breathLevel) {

                case 10:
                case 9:
                case 8:
                case 7: {
                    bossBar.setColor(BarColor.GREEN);
                    break;
                }
                case 6:
                case 5:
                case 4: {
                    bossBar.setColor(BarColor.YELLOW);
                    break;
                }
                case 3:
                case 2:
                case 1: {
                    bossBar.setColor(BarColor.RED);
                    break;
                }
                case 0: {

                    if ((players.getHealth() - (DAMAGE * 2)) < 0) {
                        players.setHealth(0);
                        return;
                    }

                    players.setHealth((players.getHealth() - (DAMAGE * 2)));
                    break;
                }
            }
        }
    }

    @Override
    public Inventory getSettingsInventory() {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, Component.text(INVENTORY_NAME));
        InventoryBuilder.fillInventory(inventory, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayname("").build());

        ItemStack airBreathTime = new ItemBuilder(Material.CLOCK).displayname("§7Air-Breath-Time §8● §9Zeit-Einstellung").build();
        new LoreBuilder(airBreathTime)
                .addSpace()
                .addLoreLine("§9Bedeutung:")
                .addParagraph("§7Gib an, nach welcher §9Zeit,")
                .addParagraph("§7du §cSchaden §7bekommst.")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 +1 Sekunde")
                .addLoreLine(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + "§7 -1 Sekunde")
                .addSpace()
                .addLoreLine("§7Status: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(AIRBREATHTIME) + " Sekunde/n")
                .addSpace();

        inventory.setItem(3 + 9, airBreathTime);

        ItemStack airBreathDamage = new ItemBuilder(Material.DAMAGED_ANVIL).displayname("§7Air-Breath-Damage §8● §9Zeit-Einstellung").build();
        new LoreBuilder(airBreathDamage)
                .addSpace()
                .addLoreLine("§9Bedeutung:")
                .addParagraph("§7Gib an, nach wieviel §cSchaden,")
                .addParagraph("§7du bekommst.")
                .addSpace()
                .addLoreLine(LoreBuilder.MESSAGES.LEFTCLICK.getMessage() + "§7 +1 Sekunde")
                .addLoreLine(LoreBuilder.MESSAGES.RIGHTCLICK.getMessage() + "§7 -1 Sekunde")
                .addSpace()
                .addLoreLine("§7Status: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(AIRBREATHDAMAGE) + " Herz/en")
                .addSpace();

        inventory.setItem(5 + 9, airBreathDamage);
        return inventory;
    }

    @Override
    public ItemStack getDisplayItem() {
        ItemStack itemStack = new ItemBuilder(Material.WATER_BUCKET, 1).displayname("§7Air-Breath §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Du bekommst §cSchaden, wenn du eine längere§7")
                .addLoreLine("§9Zeit §7nicht im Wasser bist")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.AIRBREATH), "§7Herausforderung")
                .addLoreLine("§7Schaden: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(AIRBREATHDAMAGE) + " Herz/en")
                .addLoreLine("§7Damage-Zeit: §9" + new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(AIRBREATHTIME) + " Sekunde/n")
                .addParagraph("§7Die Zeit, nach der du Schaden bekommst.")
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
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(AIRBREATHTIME, InventoryBuilder.inventoryClickIntManagerWithoutShift(
                    new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(AIRBREATHTIME),
                    clickType, 1, 120, 1, 1));
            player.openInventory(getSettingsInventory());
        }
        if (material == Material.DAMAGED_ANVIL) {
            new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).set(AIRBREATHDAMAGE, InventoryBuilder.inventoryClickIntManagerWithoutShift(
                    new ConfigManager(ConfigManager.CONFIGS.CHALLENGE_CONFIG).getInt(AIRBREATHDAMAGE),
                    clickType, 1, 10, 1, 1));
            player.openInventory(getSettingsInventory());
        }
    }
}