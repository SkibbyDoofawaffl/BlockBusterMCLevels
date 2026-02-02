package com.yourserver.levels.data;

import java.util.UUID;

public class PlayerData {
    
    private final UUID uuid;
    private int level;
    private int xp;
    private long lastPlaytimeCheck;
    
    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.level = 0;
        this.xp = 0;
        this.lastPlaytimeCheck = System.currentTimeMillis();
    }
    
    public PlayerData(UUID uuid, int level, int xp, long lastPlaytimeCheck) {
        this.uuid = uuid;
        this.level = level;
        this.xp = xp;
        this.lastPlaytimeCheck = lastPlaytimeCheck;
    }
    
    public UUID getUuid() {
        return uuid;
    }
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public int getXp() {
        return xp;
    }
    
    public void setXp(int xp) {
        this.xp = xp;
    }
    
    public void addXp(int amount) {
        this.xp += amount;
    }
    
    public void removeXp(int amount) {
        this.xp = Math.max(0, this.xp - amount);
    }
    
    public long getLastPlaytimeCheck() {
        return lastPlaytimeCheck;
    }
    
    public void setLastPlaytimeCheck(long lastPlaytimeCheck) {
        this.lastPlaytimeCheck = lastPlaytimeCheck;
    }
}
