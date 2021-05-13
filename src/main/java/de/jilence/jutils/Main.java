package de.jilence.jutils;

import de.jilence.jutils.challenge.ChallengeManager;
import de.jilence.jutils.challenge.ChallengeSystem;
import de.jilence.jutils.command.ResetCommand;
import de.jilence.jutils.command.SettingCommand;
import de.jilence.jutils.command.TimerCommand;
import de.jilence.jutils.listener.ChatListener;
import de.jilence.jutils.listener.GameListener;
import de.jilence.jutils.listener.InventoryListener;
import de.jilence.jutils.listener.JoinQuitListener;
import de.jilence.jutils.timer.Timer;
import de.jilence.jutils.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.StructureType;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;


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
            deleteFolder("world");
            deleteFolder("world_nether");
            deleteFolder("world_the_end");
            new ConfigManager(ConfigManager.CONFIGS.CONFIG).set("reset", false);
        }

    }

    @Override
    public void onEnable() {

        commandRegistration();
        listenerRegistration(Bukkit.getPluginManager());

        villageSpawn();

        Timer.setPause(true);
        Timer.startTimer();

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

    private void deleteFolder(String folder) {
        if(Files.exists(Paths.get(folder))) {
            try {
                Files.walk(Paths.get(folder)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void villageSpawn() {
        if(new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("villageSpawn")) {
            Location village = Bukkit.getWorld("world").locateNearestStructure(Bukkit.getWorld("world").getSpawnLocation(), StructureType.VILLAGE, 5000, true);
            Bukkit.getWorld("world").setSpawnLocation(village);
        }
    }

}
