package com.github.ms5984.soilsolace;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Conf {
    private static Conf instance;
    private final File configFile;
    private final YamlConfiguration conf;

    public Conf(SoilSolace soilSolace) {
        instance = this;
        this.configFile = new File(soilSolace.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            final InputStream resource = soilSolace.getResource("config.yml");
            if (resource != null) {
                this.conf = YamlConfiguration.loadConfiguration(new InputStreamReader(resource));
                soilSolace.saveResource("config.yml", false);
                return;
            }
        } else {
            this.conf = YamlConfiguration.loadConfiguration(configFile);
            return;
        }
        this.conf = new YamlConfiguration();
    }

    private void reloadFromFile() {
        try {
            conf.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void saveConfigToFile() {
        try {
            conf.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public static String getString(String node) {
        if (node == null) {
            return "";
        }
        final String confString = instance.conf.getString(node);
        return (confString != null) ? confString : "";
    }

    @NotNull
    public static int getInt(String node) {
        if (node == null) {
            return 0;
        }
        return instance.conf.getInt(node);
    }
}
