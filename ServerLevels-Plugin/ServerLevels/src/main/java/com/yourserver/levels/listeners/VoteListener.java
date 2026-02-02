package com.yourserver.levels.listeners;

import com.vexsoftware.votifier.model.VotifierEvent;
import com.yourserver.levels.ServerLevels;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VoteListener implements Listener {
    
    private final ServerLevels plugin;
    
    public VoteListener(ServerLevels plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onVote(VotifierEvent event) {
        if (!plugin.getConfigManager().isVotingEnabled()) {
            return;
        }
        
        String playerName = event.getVote().getUsername();
        Player player = Bukkit.getPlayer(playerName);
        
        if (player != null && player.isOnline()) {
            int xpReward = plugin.getConfigManager().getVotingXP();
            plugin.getLevelManager().addXP(player, xpReward, "Voting");
        }
    }
}
