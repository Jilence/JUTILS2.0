package de.jilence.jutils.command;

import de.jilence.jutils.Main;
import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.Messages;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.CONFIG_USER);
            return true;
        }
        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage(Messages.NO_PERMISSIONS);
            return true;
        }

        if(!(args.length == 1)) {
            player.sendMessage(Main.getError() + "§cBitte benutze \"/reset confirm\"");
            return true;
        }

        player.sendMessage(Main.getPrefix() + "§9Reset §7wird ausgeführt");
        new ConfigManager(ConfigManager.CONFIGS.CONFIG).set("reset", true);

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.kick(Component.text("§cReset §7wurde von §9" + player.getName() + "§7 ausgeführt."));
        }

        Bukkit.getServer().shutdown();

        return false;
    }
}