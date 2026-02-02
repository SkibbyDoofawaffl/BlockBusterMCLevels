package com.yourserver.levels.commands;

import com.yourserver.levels.ServerLevels;
import com.yourserver.levels.managers.LevelManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand implements CommandExecutor {
    
    private final ServerLevels plugin;
    
    public LevelCommand(ServerLevels plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        
        Player player = (Player) sender;
        LevelManager levelManager = plugin.getLevelManager();
        
        int level = levelManager.getPlayerLevel(player.getUniqueId());
        int xp = levelManager.getPlayerXP(player.getUniqueId());
        int xpToNext = levelManager.getXPToNextLevel(player.getUniqueId());
        double progress = levelManager.getProgressPercentage(player.getUniqueId());
        int maxLevel = plugin.getConfigManager().getMaxLevel();
        
        // Send level info
        player.sendMessage("§8§m                                            ");
        player.sendMessage("§c§lYOUR LEVEL PROGRESS");
        player.sendMessage("");
        player.sendMessage("  §7Current Level: §a" + level + " §8/ §f" + maxLevel);
        player.sendMessage("  §7Current XP: §b" + xp + "XP");
        
        if (level < maxLevel) {
            int nextLevel = level + 1;
            int xpRequired = plugin.getConfigManager().getXPRequired(nextLevel);
            player.sendMessage("  §7Next Level: §e" + nextLevel);
            player.sendMessage("  §7XP Required: §e" + xpRequired + "XP");
            player.sendMessage("  §7XP Needed: §c" + xpToNext + "XP");
            player.sendMessage("");
            player.sendMessage("  §7Progress: " + getProgressBar(progress) + " §f" + String.format("%.1f", progress) + "%");
        } else {
            player.sendMessage("");
            player.sendMessage("  §6§lMAX LEVEL REACHED!");
        }
        
        player.sendMessage("");
        player.sendMessage("  §7XP Sources:");
        player.sendMessage("  §8▪ §fPlaytime: §a" + plugin.getConfigManager().getPlaytimeMinutes() + " mins §8→ §b" + plugin.getConfigManager().getPlaytimeXP() + "XP");
        player.sendMessage("  §8▪ §fVoting: §a1 vote §8→ §b" + plugin.getConfigManager().getVotingXP() + "XP");
        player.sendMessage("  §8▪ §fDaily Quests: §a1 quest §8→ §b" + plugin.getConfigManager().getDailyQuestXP() + "XP");
        player.sendMessage("§8§m                                            ");
        
        return true;
    }
    
    private String getProgressBar(double percentage) {
        int totalBars = 20;
        int filledBars = (int) (totalBars * (percentage / 100.0));
        
        StringBuilder bar = new StringBuilder("§a");
        for (int i = 0; i < totalBars; i++) {
            if (i < filledBars) {
                bar.append("█");
            } else {
                if (i == filledBars) {
                    bar.append("§7");
                }
                bar.append("█");
            }
        }
        
        return bar.toString();
    }
}
