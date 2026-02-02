package com.yourserver.levels.managers;

import com.yourserver.levels.ServerLevels;
import com.yourserver.levels.config.ConfigManager;
import com.yourserver.levels.data.PlayerData;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LevelManager {
    
    private final ServerLevels plugin;
    private final ConfigManager config;
    private LuckPerms luckPerms;
    
    public LevelManager(ServerLevels plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfigManager();
        
        try {
            this.luckPerms = LuckPermsProvider.get();
        } catch (Exception e) {
            plugin.getLogger().severe("LuckPerms not found!");
        }
    }
    
    public void addXP(Player player, int amount, String source) {
        UUID uuid = player.getUniqueId();
        PlayerData data = plugin.getDataStorage().getPlayerData(uuid);
        
        int oldLevel = data.getLevel();
        data.addXp(amount);
        
        // Send XP gain message
        String message = config.getMessage("xp-gain")
                .replace("%xp%", String.valueOf(amount))
                .replace("%source%", source);
        player.sendMessage(config.getPrefix() + " " + message);
        
        // Check for level up
        checkLevelUp(player, data, oldLevel);
        
        // Save data
        plugin.getDataStorage().savePlayerData(uuid);
    }
    
    private void checkLevelUp(Player player, PlayerData data, int oldLevel) {
        int currentLevel = data.getLevel();
        int maxLevel = config.getMaxLevel();
        
        // Keep checking if player can level up multiple times
        while (currentLevel < maxLevel) {
            int nextLevel = currentLevel + 1;
            int xpRequired = config.getXPRequired(nextLevel);
            
            if (data.getXp() >= xpRequired) {
                // Level up!
                data.setLevel(nextLevel);
                data.removeXp(xpRequired);
                currentLevel = nextLevel;
                
                // Update LuckPerms meta
                updateLuckPermsMeta(player.getUniqueId(), nextLevel);
                
                // Send level up message
                String message = config.getMessage("level-up")
                        .replace("%level%", String.valueOf(nextLevel));
                player.sendMessage(config.getPrefix() + " " + message);
                
                // Give rewards
                giveRewards(player, nextLevel);
            } else {
                break;
            }
        }
    }
    
    private void giveRewards(Player player, int level) {
        ConfigManager.LevelReward reward = config.getReward(level);
        if (reward == null) return;
        
        // Execute commands
        for (String command : reward.getCommands()) {
            String processedCommand = command.replace("%player%", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), processedCommand);
        }
        
        // Broadcast
        String broadcast = reward.getBroadcast().replace("%player%", player.getName());
        if (!broadcast.isEmpty()) {
            Bukkit.broadcastMessage(broadcast);
        }
    }
    
    private void updateLuckPermsMeta(UUID uuid, int level) {
        if (luckPerms == null) return;
        
        User user = luckPerms.getUserManager().getUser(uuid);
        if (user == null) return;
        
        // Remove old level meta
        user.data().clear(node -> node instanceof MetaNode && ((MetaNode) node).getMetaKey().equals("level"));
        
        // Add new level meta
        MetaNode node = MetaNode.builder("level", String.valueOf(level)).build();
        user.data().add(node);
        
        luckPerms.getUserManager().saveUser(user);
    }
    
    public int getPlayerLevel(UUID uuid) {
        return plugin.getDataStorage().getPlayerData(uuid).getLevel();
    }
    
    public int getPlayerXP(UUID uuid) {
        return plugin.getDataStorage().getPlayerData(uuid).getXp();
    }
    
    public void setPlayerLevel(UUID uuid, int level) {
        PlayerData data = plugin.getDataStorage().getPlayerData(uuid);
        data.setLevel(level);
        data.setXp(0);
        updateLuckPermsMeta(uuid, level);
        plugin.getDataStorage().savePlayerData(uuid);
    }
    
    public void setPlayerXP(UUID uuid, int xp) {
        PlayerData data = plugin.getDataStorage().getPlayerData(uuid);
        data.setXp(xp);
        plugin.getDataStorage().savePlayerData(uuid);
    }
    
    public void resetPlayer(UUID uuid) {
        PlayerData data = plugin.getDataStorage().getPlayerData(uuid);
        data.setLevel(0);
        data.setXp(0);
        updateLuckPermsMeta(uuid, 0);
        plugin.getDataStorage().savePlayerData(uuid);
    }
    
    public int getXPToNextLevel(UUID uuid) {
        PlayerData data = plugin.getDataStorage().getPlayerData(uuid);
        int nextLevel = data.getLevel() + 1;
        
        if (nextLevel > config.getMaxLevel()) {
            return 0; // Max level reached
        }
        
        return config.getXPRequired(nextLevel) - data.getXp();
    }
    
    public double getProgressPercentage(UUID uuid) {
        PlayerData data = plugin.getDataStorage().getPlayerData(uuid);
        int nextLevel = data.getLevel() + 1;
        
        if (nextLevel > config.getMaxLevel()) {
            return 100.0;
        }
        
        int required = config.getXPRequired(nextLevel);
        return (double) data.getXp() / required * 100.0;
    }
}
