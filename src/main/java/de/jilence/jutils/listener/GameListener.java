package de.jilence.jutils.listener;

import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.timer.Timer;
import de.jilence.jutils.utils.ConfigManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;


public class GameListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if(Timer.isPause()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(Timer.isPause()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {

        if(Timer.isPause()) {

            if(event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }

            return;
        }



        if(event.getEntity().getType() == EntityType.ENDER_DRAGON) {
            if(new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("enderdragon")) {

                if(event.getEntity().getKiller() == null) {
                    return;
                }

                ChallengeManager.winChallenge(event.getEntity().getKiller(), "§6§l%player §7den §6EnderDragon §7getötet hat");
            }
        }

        if(event.getEntity().getType() == EntityType.WITHER) {
            if(new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("wither")) {

                if(event.getEntity().getKiller() == null) {
                    return;
                }

                ChallengeManager.winChallenge(event.getEntity().getKiller(), "§6§l%player §7den §6Wither §7getötet hat");
            }
        }

        if(event.getEntity() instanceof Player) {
            if(new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("player")) {
                ChallengeManager.loseChallenge((Player) event.getEntity(), "§6§l%player §7gestorben ist.");
            }
        }

    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {

        if(Timer.isPause()) {
            event.setCancelled(true);
        }

    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if(Timer.isPause()) {
            event.setCancelled(true);
        }
    }
}
