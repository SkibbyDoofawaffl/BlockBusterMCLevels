package com.yourserver.levels.listeners;

import com.yourserver.levels.ServerLevels;
import com.yourserver.levels.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlaytimeListener implements Listener {
    
    private final ServerLevels plugin;
    private final Map<UUID, BukkitRunnable> activeTasks;
    
    public PlaytimeListener(ServerLevels plugin) {
        this.plugin = plugin;
        this.activeTasks = new HashMap<>();
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        if (!plugin.getConfigManager().isPlaytimeEnabled()) {
            return;
        }
        
        startPlaytimeTracking(player);
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        stopPlaytimeTracking(player);
        
        // Save player data
        plugin.getDataStorage().savePlayerData(player.getUniqueId());
    }
    
    private void startPlaytimeTracking(Player player) {
        UUID uuid = player.getUniqueId();
        
        // Cancel existing task if any
        stopPlaytimeTracking(player);
        
        int minutes = plugin.getConfigManager().getPlaytimeMinutes();
        int xpReward = plugin.getConfigManager().getPlaytimeXP();
        
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline()) {
                    PlayerData data = plugin.getDataStorage().getPlayerData(uuid);
                    
                    // Check if max level
                    if (data.getLevel() >= plugin.getConfigManager().getMaxLevel()) {
                        return;
                    }
                    
                    // Give XP
                    plugin.getLevelManager().addXP(player, xpReward, "Playtime");
                }
            }
        };
        
        // Convert minutes to ticks (20 ticks = 1 second)
        long ticks = minutes * 60L * 20L;
        task.runTaskTimer(plugin, ticks, ticks);
        
        activeTasks.put(uuid, task);
    }
    
    private void stopPlaytimeTracking(Player player) {
        UUID uuid = player.getUniqueId();
        BukkitRunnable task = activeTasks.remove(uuid);
        
        if (task != null) {
            task.cancel();
        }
    }
}
