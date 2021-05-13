package de.jilence.jutils.challenge.challenge;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.Challenge;
import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.utils.ItemBuilder;
import de.jilence.jutils.utils.LoreBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class RandomEffectOnDamage extends Challenge implements Listener {

    @Override
    public void onEnable() {

    }

    @Override
    public void onStart() {
        Bukkit.getPluginManager().registerEvents(new RandomEffectOnDamage(), Main.getPlugin(Main.class));
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDisable() {

    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player) {

            Random random = new Random();
            int i = random.nextInt(((PotionEffectType.values().length - 1)) + 1);
            PotionEffectType effect = PotionEffectType.values()[i];

            Bukkit.getOnlinePlayers().forEach(player -> player.addPotionEffect(new PotionEffect(effect, 99999, 1)));

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
        ItemStack itemStack = new ItemBuilder(Material.POTION, 1).displayname("§7Effect on Damage §9Challenge").build();
        new LoreBuilder(itemStack)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.HEADER)
                .addSpace()
                .addLoreLine("§7Wenn ein §9Spieler §7Schaden bekommt,")
                .addLoreLine("§7bekommen alle §9Spieler §7einen random Effect.")
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.RIGHT_LEFT_CLICK)
                .addSpace()
                .addDescription(LoreBuilder.DESCRIPTIONS.SETTINGS)
                .addBoolSettings(ChallengeManager.isChallengeEnabled(ChallengeManager.Challenges.RANDOMEFFECTONDAMAGE), "§7Herausforderung")
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
