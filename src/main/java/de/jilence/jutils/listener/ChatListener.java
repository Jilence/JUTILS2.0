package de.jilence.jutils.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onAsyncChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (player.isOp()) {
            event.message(Component.text("§4Operator §8§l| §r§4" + player.getName() + ": §r§7" + ChatColor.translateAlternateColorCodes('&', event.message().toString())));
        } else {
            event.message(Component.text("§7Spieler §8§l| §r§7" + player.getName() + ": §r§7" + event.message()));
        }
    }
}