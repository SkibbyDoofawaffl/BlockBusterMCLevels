package com.yourserver.levels.commands;

import com.yourserver.levels.ServerLevels;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class LevelAdminCommand implements CommandExecutor {
    
    private final ServerLevels plugin;
    
    public LevelAdminCommand(ServerLevels plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("serverlevels.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }
        
        if (args.length < 2) {
            sendHelp(sender);
            return true;
        }
        
        String action = args[0].toLowerCase();
        String targetName = args[1];
        Player target = Bukkit.getPlayer(targetName);
        
        if (target == null) {
            sender.sendMessage(plugin.getConfigManager().getPrefix() + " " + 
                    plugin.getConfigManager().getMessage("player-not-found"));
            return true;
        }
        
        UUID targetUUID = target.getUniqueId();
        
        switch (action) {
            case "give":
                if (args.length < 3) {
                    sender.sendMessage("§cUsage: /leveladmin give <player> <amount>");
                    return true;
                }
                try {
                    int amount = Integer.parseInt(args[2]);
                    plugin.getLevelManager().addXP(target, amount, "Admin");
                    String giveMsg = plugin.getConfigManager().getMessage("admin-give-xp")
                            .replace("%amount%", String.valueOf(amount))
                            .replace("%player%", target.getName());
                    sender.sendMessage(plugin.getConfigManager().getPrefix() + " " + giveMsg);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid amount!");
                }
                break;
                
            case "take":
                if (args.length < 3) {
                    sender.sendMessage("§cUsage: /leveladmin take <player> <amount>");
                    return true;
                }
                try {
                    int amount = Integer.parseInt(args[2]);
                    int currentXP = plugin.getLevelManager().getPlayerXP(targetUUID);
                    plugin.getLevelManager().setPlayerXP(targetUUID, Math.max(0, currentXP - amount));
                    String takeMsg = plugin.getConfigManager().getMessage("admin-take-xp")
                            .replace("%amount%", String.valueOf(amount))
                            .replace("%player%", target.getName());
                    sender.sendMessage(plugin.getConfigManager().getPrefix() + " " + takeMsg);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid amount!");
                }
                break;
                
            case "set":
                if (args.length < 3) {
                    sender.sendMessage("§cUsage: /leveladmin set <player> <level>");
                    return true;
                }
                try {
                    int level = Integer.parseInt(args[2]);
                    if (level < 0 || level > plugin.getConfigManager().getMaxLevel()) {
                        sender.sendMessage("§cLevel must be between 0 and " + plugin.getConfigManager().getMaxLevel());
                        return true;
                    }
                    plugin.getLevelManager().setPlayerLevel(targetUUID, level);
                    String setMsg = plugin.getConfigManager().getMessage("admin-set-level")
                            .replace("%level%", String.valueOf(level))
                            .replace("%player%", target.getName());
                    sender.sendMessage(plugin.getConfigManager().getPrefix() + " " + setMsg);
                } catch (NumberFormatException e) {
                    sender.sendMessage("§cInvalid level!");
                }
                break;
                
            case "reset":
                plugin.getLevelManager().resetPlayer(targetUUID);
                String resetMsg = plugin.getConfigManager().getMessage("admin-reset")
                        .replace("%player%", target.getName());
                sender.sendMessage(plugin.getConfigManager().getPrefix() + " " + resetMsg);
                break;
                
            case "info":
                int level = plugin.getLevelManager().getPlayerLevel(targetUUID);
                int xp = plugin.getLevelManager().getPlayerXP(targetUUID);
                sender.sendMessage("§7--- §c" + target.getName() + "'s Level Info §7---");
                sender.sendMessage("§7Level: §a" + level);
                sender.sendMessage("§7XP: §b" + xp);
                break;
                
            default:
                sendHelp(sender);
                break;
        }
        
        return true;
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§8§m                                            ");
        sender.sendMessage("§c§lLEVEL ADMIN COMMANDS");
        sender.sendMessage("");
        sender.sendMessage("  §7/leveladmin give <player> <amount> §8- §fGive XP");
        sender.sendMessage("  §7/leveladmin take <player> <amount> §8- §fTake XP");
        sender.sendMessage("  §7/leveladmin set <player> <level> §8- §fSet level");
        sender.sendMessage("  §7/leveladmin reset <player> §8- §fReset progress");
        sender.sendMessage("  §7/leveladmin info <player> §8- §fView info");
        sender.sendMessage("§8§m                                            ");
    }
}
