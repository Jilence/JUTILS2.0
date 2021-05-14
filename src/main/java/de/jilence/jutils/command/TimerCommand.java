package de.jilence.jutils.command;

import de.jilence.jutils.Main;
import de.jilence.jutils.challenge.ChallengeSystem;
import de.jilence.jutils.inventory.TimerInventory;
import de.jilence.jutils.timer.Timer;
import de.jilence.jutils.timer.TimerManager;
import de.jilence.jutils.utils.Messages;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TimerCommand implements TabExecutor {
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

        if (!(args.length == 1)) {
            player.sendMessage(Messages.NO_COMMAND_FOUND);
            return true;
        }

        if (!TimerManager.isTimerEnabled()) {
            player.sendMessage(Main.getError() + "§cDer Timer ist disabled");
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "resume":
                if (Timer.isResumed()) {
                    player.sendMessage(Main.getError() + "§cDer Timer läuft schon weiter");
                    return true;
                }
                Timer.setPause(false);
                ChallengeSystem.startChallenges();
                player.sendMessage(Main.getPrefix() + "§7Der §9Timer §7läuft nun weiter");
                break;
            case "pause":
                if (Timer.isPause()) {
                    player.sendMessage(Main.getError() + "§cDer Timer ist schon pausiert");
                    return true;
                }
                Timer.setPause(true);
                player.sendMessage(Main.getPrefix() + "§7Der §9Timer §7wurde pausiert");
                break;
            case "settings":
                player.openInventory(TimerInventory.getTimerInventory());
                break;
            case "reset":
                Timer.resetTimer();
                player.sendMessage(Main.getPrefix() + "§7Der §9Timer §7wurde resetet");
                break;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        List<String> tabCompleter = new ArrayList<>();

        tabCompleter.add("pause");
        tabCompleter.add("resume");
        tabCompleter.add("settings");
        tabCompleter.add("reset");

        return tabCompleter;
    }
}