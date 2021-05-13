package de.jilence.jutils;

import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.command.ResetCommand;
import de.jilence.jutils.command.SettingCommand;
import de.jilence.jutils.command.TimerCommand;
import de.jilence.jutils.listener.ChatListener;
import de.jilence.jutils.listener.GameListener;
import de.jilence.jutils.listener.InventoryListener;
import de.jilence.jutils.listener.JoinQuitListener;
import de.jilence.jutils.timer.Timer;
import de.jilence.jutils.utils.ConfigManager;
import de.jilence.jutils.utils.Messages;
import de.jilence.jutils.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static String prefix = ChatColor.BLUE + "JUTILS " + ChatColor.DARK_GRAY +"● ";
    private static String error =  ChatColor.RED + "Error " + ChatColor.DARK_GRAY + "● ";


    public static String getPrefix() {
        return prefix;
    }

    public static String getError() {
        return error;
    }

    public void onLoad() {
        boolean reset = new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("reset");
        if (reset) {
            Utils.deleteFolder("world");
            Utils.deleteFolder("world_nether");
            Utils.deleteFolder("world_the_end");
            new ConfigManager(ConfigManager.CONFIGS.CONFIG).set("reset", false);
        }
    }

    @Override
    public void onEnable() {

        commandRegistration();
        listenerRegistration(Bukkit.getPluginManager());

        Utils.villageSpawn();
        Utils.createPlayerDateFolder();

        Timer.setPause(true);
        Timer.startTimer();
        Messages.startupMessage();

        for(int i = 0; i < ChallengeManager.Challenges.values().length; i++) {
            ChallengeManager.Challenges challenges = ChallengeManager.Challenges.values()[i];

            if(ChallengeManager.isChallengeEnabled(challenges)) {
                challenges.getChallenge().onEnable();
            }

        }

    }

    @Override
    public void onDisable() {
    }

    public void commandRegistration() {
        getCommand("settings").setExecutor(new SettingCommand());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("reset").setExecutor(new ResetCommand());
    }
    public void listenerRegistration(PluginManager pluginManager) {
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);
        pluginManager.registerEvents(new GameListener(), this);
    }




}
