package de.jilence.jutils.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (player.isOp()) {
            event.setFormat("§4Operator §8§l| §r§4%1$s§8: §r§7%2$s");
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        } else {
            event.setFormat("§7Spieler §8§l| §r§7%1$s§8: §r§7%2$s");

        }
    }
}
