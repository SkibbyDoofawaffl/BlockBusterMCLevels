package com.yourserver.levels.config;

import com.yourserver.levels.ServerLevels;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigManager {
    
    private final ServerLevels plugin;
    private FileConfiguration config;
    private Map<Integer, Integer> xpRequirements;
    private Map<Integer, LevelReward> rewards;
    
    public ConfigManager(ServerLevels plugin) {
        this.plugin = plugin;
        this.xpRequirements = new HashMap<>();
        this.rewards = new HashMap<>();
        loadConfig();
    }
    
    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
        
        loadXPRequirements();
        loadRewards();
    }
    
    private void loadXPRequirements() {
        xpRequirements.clear();
        for (int level = 1; level <= getMaxLevel(); level++) {
            int xp = config.getInt("level-xp-requirements." + level, 100);
            xpRequirements.put(level, xp);
        }
    }
    
    private void loadRewards() {
        rewards.clear();
        for (int level = 1; level <= getMaxLevel(); level++) {
            String path = "level-rewards." + level;
            if (config.contains(path)) {
                List<String> commands = config.getStringList(path + ".commands");
                String broadcast = config.getString(path + ".broadcast", "");
                rewards.put(level, new LevelReward(commands, broadcast));
            }
        }
    }
    
    public int getXPRequired(int level) {
        return xpRequirements.getOrDefault(level, 100);
    }
    
    public LevelReward getReward(int level) {
        return rewards.get(level);
    }
    
    public int getMaxLevel() {
        return config.getInt("level-settings.max-level", 24);
    }
    
    public int getPlaytimeXP() {
        return config.getInt("xp-sources.playtime.xp-reward", 1);
    }
    
    public int getPlaytimeMinutes() {
        return config.getInt("xp-sources.playtime.minutes", 5);
    }
    
    public int getVotingXP() {
        return config.getInt("xp-sources.voting.xp-reward", 10);
    }
    
    public int getDailyQuestXP() {
        return config.getInt("xp-sources.daily-quests.xp-reward", 5);
    }
    
    public boolean isPlaytimeEnabled() {
        return config.getBoolean("xp-sources.playtime.enabled", true);
    }
    
    public boolean isVotingEnabled() {
        return config.getBoolean("xp-sources.voting.enabled", true);
    }
    
    public boolean isDailyQuestsEnabled() {
        return config.getBoolean("xp-sources.daily-quests.enabled", true);
    }
    
    public String getMessage(String key) {
        return config.getString("messages." + key, "&cMessage not found: " + key)
                .replace("&", "§");
    }
    
    public String getPrefix() {
        return getMessage("prefix");
    }
    
    public static class LevelReward {
        private final List<String> commands;
        private final String broadcast;
        
        public LevelReward(List<String> commands, String broadcast) {
            this.commands = commands;
            this.broadcast = broadcast;
        }
        
        public List<String> getCommands() {
            return commands;
        }
        
        public String getBroadcast() {
            return broadcast.replace("&", "§");
        }
    }
}
