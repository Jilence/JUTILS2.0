package de.jilence.jutils.listener;

import de.jilence.jutils.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.joinMessage(Component.text(Main.getPrefix() + "§7Der Spieler §9" + player.getName() + "§7 hat das Spiel §abetreten"));

        if (player.getBedSpawnLocation() == null) {
            player.setBedSpawnLocation(player.getLocation());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.quitMessage(Component.text(Main.getPrefix() + "§7Der Spieler §9" + player.getName() + "§7 hat das Spiel §cverlassen"));
    }
}