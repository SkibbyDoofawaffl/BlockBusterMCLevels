package com.yourserver.levels.storage;

import com.yourserver.levels.ServerLevels;
import com.yourserver.levels.data.PlayerData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataStorage {
    
    private final ServerLevels plugin;
    private final File dataFolder;
    private final Map<UUID, PlayerData> playerDataMap;
    
    public DataStorage(ServerLevels plugin) {
        this.plugin = plugin;
        this.dataFolder = new File(plugin.getDataFolder(), "playerdata");
        this.playerDataMap = new HashMap<>();
        
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
    }
    
    public PlayerData getPlayerData(UUID uuid) {
        return playerDataMap.computeIfAbsent(uuid, this::loadPlayerData);
    }
    
    private PlayerData loadPlayerData(UUID uuid) {
        File playerFile = new File(dataFolder, uuid.toString() + ".yml");
        
        if (!playerFile.exists()) {
            return new PlayerData(uuid);
        }
        
        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);
        int level = config.getInt("level", 0);
        int xp = config.getInt("xp", 0);
        long lastPlaytime = config.getLong("last-playtime-check", System.currentTimeMillis());
        
        return new PlayerData(uuid, level, xp, lastPlaytime);
    }
    
    public void savePlayerData(UUID uuid) {
        PlayerData data = playerDataMap.get(uuid);
        if (data == null) return;
        
        File playerFile = new File(dataFolder, uuid.toString() + ".yml");
        FileConfiguration config = new YamlConfiguration();
        
        config.set("level", data.getLevel());
        config.set("xp", data.getXp());
        config.set("last-playtime-check", data.getLastPlaytimeCheck());
        
        try {
            config.save(playerFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save data for " + uuid + ": " + e.getMessage());
        }
    }
    
    public void saveAllPlayers() {
        for (UUID uuid : playerDataMap.keySet()) {
            savePlayerData(uuid);
        }
    }
    
    public void loadAllPlayers() {
        if (!dataFolder.exists()) return;
        
        File[] files = dataFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;
        
        for (File file : files) {
            try {
                String uuidString = file.getName().replace(".yml", "");
                UUID uuid = UUID.fromString(uuidString);
                loadPlayerData(uuid);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Invalid player data file: " + file.getName());
            }
        }
    }
    
    public void removePlayerData(UUID uuid) {
        playerDataMap.remove(uuid);
    }
}
