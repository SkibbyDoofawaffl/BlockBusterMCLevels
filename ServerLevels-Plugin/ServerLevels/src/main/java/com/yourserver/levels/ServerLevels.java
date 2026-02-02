package com.yourserver.levels;

import com.yourserver.levels.commands.LevelCommand;
import com.yourserver.levels.commands.LevelAdminCommand;
import com.yourserver.levels.config.ConfigManager;
import com.yourserver.levels.listeners.PlaytimeListener;
import com.yourserver.levels.listeners.VoteListener;
import com.yourserver.levels.listeners.QuestListener;
import com.yourserver.levels.managers.LevelManager;
import com.yourserver.levels.storage.DataStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerLevels extends JavaPlugin {
    
    private static ServerLevels instance;
    private ConfigManager configManager;
    private LevelManager levelManager;
    private DataStorage dataStorage;
    private Economy economy;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Setup Vault Economy
        if (!setupEconomy()) {
            getLogger().severe("Vault not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // Initialize managers
        configManager = new ConfigManager(this);
        dataStorage = new DataStorage(this);
        levelManager = new LevelManager(this);
        
        // Register commands
        getCommand("levels").setExecutor(new LevelCommand(this));
        getCommand("leveladmin").setExecutor(new LevelAdminCommand(this));
        
        // Register listeners
        getServer().getPluginManager().registerEvents(new PlaytimeListener(this), this);
        getServer().getPluginManager().registerEvents(new VoteListener(this), this);
        getServer().getPluginManager().registerEvents(new QuestListener(this), this);
        
        // Load data
        dataStorage.loadAllPlayers();
        
        getLogger().info("ServerLevels has been enabled!");
    }
    
    @Override
    public void onDisable() {
        // Save all data
        if (dataStorage != null) {
            dataStorage.saveAllPlayers();
        }
        getLogger().info("ServerLevels has been disabled!");
    }
    
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }
    
    public static ServerLevels getInstance() {
        return instance;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public LevelManager getLevelManager() {
        return levelManager;
    }
    
    public DataStorage getDataStorage() {
        return dataStorage;
    }
    
    public Economy getEconomy() {
        return economy;
    }
}
