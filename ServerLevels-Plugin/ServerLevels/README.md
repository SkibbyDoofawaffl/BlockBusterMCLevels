# ServerLevels Plugin

A custom Minecraft server leveling system with progressive XP requirements and configurable rewards.

## Features

- **Exponential XP Curve** - Levels get progressively harder to obtain
- **Multiple XP Sources**:
  - Playtime (default: 1 XP per 5 minutes)
  - Voting (default: 10 XP per vote)
  - Daily Quests (default: 5 XP per quest)
- **Configurable Rewards** - Execute any commands when players level up
- **24 Levels** - Max level unlocks /fly permission
- **Admin Commands** - Manage player levels and XP
- **LuckPerms Integration** - Stores level data in player metadata

## XP Requirements (Nerfed Version)

This plugin uses an exponential formula to make leveling much harder than the original system:

| Level | XP Required | Total XP | Approx. Playtime |
|-------|-------------|----------|------------------|
| 1     | 50 XP       | 50 XP    | 4.2 hours        |
| 2     | 68 XP       | 118 XP   | 9.8 hours        |
| 5     | 167 XP      | 507 XP   | 42.3 hours       |
| 10    | 751 XP      | 2,935 XP | 244.6 hours      |
| 15    | 3,368 XP    | 16,302 XP| 1,358.5 hours    |
| 20    | 15,104 XP   | 74,093 XP| 6,174.4 hours    |
| 24    | 50,168 XP   | 241,760 XP| 20,146.7 hours  |

**Note**: These times assume ONLY playtime. With voting and quests, progress is faster.

## Dependencies

**Required:**
- Spigot/Paper 1.20+
- Vault
- LuckPerms

**Optional:**
- Votifier (for voting XP)
- Your daily quest plugin (configure QuestListener.java)

## Installation

1. **Build the plugin** (requires Maven):
   ```bash
   mvn clean package
   ```
   The JAR file will be in `target/ServerLevels-1.0.0.jar`

2. **Or use the pre-built JAR** if provided

3. **Install dependencies**:
   - Install Vault and LuckPerms on your server

4. **Upload the plugin**:
   - Place `ServerLevels-1.0.0.jar` in your server's `plugins/` folder

5. **Start your server**:
   - The plugin will generate a `config.yml` file

6. **Configure** (see below)

7. **Restart the server**

## Configuration

### config.yml

The main configuration file is located at `plugins/ServerLevels/config.yml`

**Key Settings:**

```yaml
# XP Sources - Enable/disable and configure XP gains
xp-sources:
  playtime:
    enabled: true
    minutes: 5          # Minutes between XP rewards
    xp-reward: 1        # XP given per interval
  voting:
    enabled: true
    xp-reward: 10       # XP given per vote
  daily-quests:
    enabled: true
    xp-reward: 5        # XP given per quest

# Level Settings
level-settings:
  max-level: 24
  base-xp: 50           # Starting XP requirement
  multiplier: 1.35      # Exponential growth rate
```

**Adjusting Difficulty:**

To make leveling even harder or easier, change the `multiplier`:
- `1.35` = Default (hard)
- `1.5` = Very hard
- `1.2` = Medium
- `1.1` = Easy

### Rewards Configuration

Each level can execute commands when reached:

```yaml
level-rewards:
  24:  # Max level
    commands:
      - "lp user %player% permission set essentials.fly true"
      - "eco give %player% 250000"
    broadcast: "&8[&6&l!&8] &6%player% &7has reached &6&lMAX LEVEL &7and unlocked &b/fly&7!"
```

**Available Placeholders:**
- `%player%` - Player name
- `%level%` - Level number

## Commands

### Player Commands

- `/levels` - View your level progress
  - Aliases: `/level`, `/lvl`
  - Shows current level, XP, and progress to next level

### Admin Commands

- `/leveladmin give <player> <amount>` - Give XP to a player
- `/leveladmin take <player> <amount>` - Remove XP from a player
- `/leveladmin set <player> <level>` - Set a player's level
- `/leveladmin reset <player>` - Reset a player's progress
- `/leveladmin info <player>` - View a player's stats

**Permission:** `serverlevels.admin`

## Permissions

- `serverlevels.admin` - Access to admin commands (default: op)

## Daily Quest Integration

The plugin includes a basic quest listener in `QuestListener.java`. You'll need to customize this based on your quest plugin:

**For BattlePass:**
```java
@EventHandler
public void onQuestComplete(BattlePassCompleteEvent event) {
    Player player = event.getPlayer();
    plugin.getLevelManager().addXP(player, 
        plugin.getConfigManager().getDailyQuestXP(), "Daily Quest");
}
```

**For BetonQuest:**
```java
@EventHandler
public void onQuestComplete(QuestCompleteEvent event) {
    Player player = Bukkit.getPlayer(event.getPlayerID());
    if (player != null) {
        plugin.getLevelManager().addXP(player, 
            plugin.getConfigManager().getDailyQuestXP(), "Daily Quest");
    }
}
```

## Data Storage

Player data is stored in `plugins/ServerLevels/playerdata/` as YAML files:
- One file per player (UUID.yml)
- Automatically saved on quit and plugin disable
- Can be manually backed up

## Customization Tips

### Change XP Curve

Edit `config.yml`:
```yaml
level-settings:
  base-xp: 100       # Double the starting requirement
  multiplier: 1.5    # Make it grow even faster
```

### Add More Levels

1. Increase `max-level` in config
2. Add rewards for new levels:
```yaml
level-rewards:
  25:
    commands:
      - "lp user %player% permission set custom.perk true"
    broadcast: "&a%player% reached level 25!"
```

### Change Playtime Frequency

Give XP more/less often:
```yaml
xp-sources:
  playtime:
    minutes: 10      # Give 1 XP every 10 minutes instead
    xp-reward: 2     # But give 2 XP each time
```

## Comparison to Original System

**Original System:**
- Level 1: 10 XP
- Level 24: 10,000 XP
- Total: ~37,000 XP

**New System:**
- Level 1: 50 XP (5x harder)
- Level 24: 50,168 XP (5x harder)
- Total: ~241,760 XP (6.5x harder)

The new exponential curve means later levels are MUCH harder to obtain, requiring serious dedication.

## Support

For issues or questions, contact your server administrator.

## License

Custom plugin for your server. All rights reserved.
