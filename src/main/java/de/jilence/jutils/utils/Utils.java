package de.jilence.jutils.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.StructureType;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class Utils {

    public static void deleteFolder(String folder) {
        if (Files.exists(Paths.get(folder))) {
            try {
                Files.walk(Paths.get(folder)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void createPlayerDateFolder() {
        File world = new File("world/playerdata");
        if (!world.exists()) {
            world.mkdirs();
        }
        File nether = new File("world_nether/playerdata");
        if (!nether.exists()) {
            nether.mkdirs();
        }
        File end = new File("world_the_nether/playerdata");
        if (!end.exists()) {
            end.mkdirs();
        }

    }

    public static void villageSpawn() {
        if (new ConfigManager(ConfigManager.CONFIGS.CONFIG).getBool("villageSpawn")) {
            Location villageLocation = Bukkit.getWorld("world").locateNearestStructure(Bukkit.getWorld("world").getSpawnLocation(), StructureType.VILLAGE, 5000, true);
            if (villageLocation == null) return;
            Bukkit.getWorld("world").setSpawnLocation(villageLocation);
        }
    }
}