package de.jilence.jutils.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static File file;
    private static YamlConfiguration CURRENT_CONFIG;

    public enum CONFIGS {

        CONFIG("./plugins/JUTILS2.0", "config.yml"),
        CHALLENGE_CONFIG("./plugins/JUTILS2.0", "challengeConfig.yml"),
        TIMER_CONFIG("./plugins/JUTILS2.0", "timerConfig.yml");

        String path, name;

        CONFIGS(String path, String name) {
            this.path = path;
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }
    }


    public ConfigManager(CONFIGS config) {
        File dir = new File(config.getPath());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(dir, config.getName());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        CURRENT_CONFIG = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigManager getInstance() {
        return this;
    }

    public boolean contains(String path) {
        return CURRENT_CONFIG.contains(path);
    }

    public void set(String path, Object value) {
        CURRENT_CONFIG.set(path, value);
        try {
            CURRENT_CONFIG.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object get(String path) {
        if (!contains(path)) {
            return null;
        }
        return CURRENT_CONFIG.get(path);
    }

    public boolean getBool(String path) {
        if (!contains(path)) {
            set(path, false);
        }
        return (boolean) CURRENT_CONFIG.get(path);
    }

    public void toggleBool(String path) {
        boolean bool = getBool(path);
        set(path, !bool);
    }

    public int getInt(String path) {
        if (!contains(path)) {
            set(path, 1);
        }
        return (int) CURRENT_CONFIG.get(path);
    }

    public int getInt(String path, int defaultInt) {
        if (!contains(path)) {
            set(path, defaultInt);
        }
        return (int) CURRENT_CONFIG.get(path);
    }
}