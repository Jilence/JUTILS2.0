package de.jilence.jutils.command;

import de.jilence.jutils.inventory.MainInventory;
import de.jilence.jutils.utils.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SettingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(Messages.CONFIG_USER);
            return true;
        }
        Player player = (Player) sender;

        if(!player.isOp()) {
            player.sendMessage(Messages.NO_PERMISSIONS);
            return true;
        }

        player.openInventory(MainInventory.getInventory());

        return false;
    }
}